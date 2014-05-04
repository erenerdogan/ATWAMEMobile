package com.atwame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;
import com.atwame.model.User;
import com.atwame.service.UserLoginService;
import com.atwame.service.UserRegisterService;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.NavLogin;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;
import com.atwame.utils.NavLogin.iLoginNavResponder;

public class RegisterActivity extends SherlockActivity implements
		iLoginNavResponder {

	public static CustomApplication application;
	private EditText emailTxt, passwordTxt, nameTxt, surnameTxt;
	private Button registerBtn;
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		flag = 1;

		application = (CustomApplication) getApplication();

		emailTxt = (EditText) findViewById(R.id.registeremailTxt);
		passwordTxt = (EditText) findViewById(R.id.registerpasswordTxt);
		nameTxt = (EditText) findViewById(R.id.registernameTxt);
		surnameTxt = (EditText) findViewById(R.id.registersurnameTxt);

		emailTxt.setText("test@gmail.com");
		passwordTxt.setText("147852");
		nameTxt.setText("Test");
		surnameTxt.setText("Test");

		registerBtn = (Button) findViewById(R.id.registerbtnSignup);
		registerBtn.setOnClickListener(new registerListener());

		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);

		if (userID != -1) {
			Intent intent = new Intent(RegisterActivity.this,
					HomeActivity.class);
			startActivity(intent);

		}

		NavLogin.spawn(this, this);

	}

	private class registerListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			loadUserRegisterResponse(false);
		}

	}

	// Service Begin

	private class loadUserRegister implements
			ServerCom.iAsyncTerminatorCallback {
		private boolean stopService;

		public loadUserRegister(boolean stopService) {
			this.stopService = stopService;
		}

		@Override
		public void loadResponse(String event, Object response) {
			if (event.equals(ParserEvent.USER_REGISTER)) {
				loadUserRegisterResponse(stopService);
				User u = (User) response;

				application.setCurrentUser(u.getId());
				Intent intent = new Intent(RegisterActivity.this,
						HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		}
	}

	private void loadUserRegisterResponse(boolean stopService) {
		Context context = RegisterActivity.this;
		if (stopService || flag == 1) {

			flag = 0;
			new UserRegisterService(context, new loadUserRegister(false),
					emailTxt.getText().toString(), passwordTxt.getText()
							.toString(), nameTxt.getText().toString() + " "
							+ surnameTxt.getText().toString()).exec()
					.makeStandardProgressBox("Register...");
			Log.w("Location", "" + application.getPosLatAsString() + " "
					+ application.getPosLongAsString());
		}
	}

	// Service End

	@Override
	public void gotologin() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void gotoRegister() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onBackPressed(){}

}
