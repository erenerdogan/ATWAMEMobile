package com.atwame;

import java.util.HashMap;

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
import com.atwame.parser.UserParser;
import com.atwame.service.BaseService;
import com.atwame.service.UserLoginService;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.NavLogin;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;

public class MainActivity extends SherlockActivity implements
		NavLogin.iLoginNavResponder {
	public static CustomApplication application;
	private EditText emailTxt, passwordTxt;
	private Button loginBtn;
	private int flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flag = 1;
		application = (CustomApplication) getApplication();

		emailTxt = (EditText) findViewById(R.id.loginemailTxt);
		passwordTxt = (EditText) findViewById(R.id.loginpasswordTxt);

		emailTxt.setText("erenerdogan87@gmail.com");
		passwordTxt.setText("147852");

		loginBtn = (Button) findViewById(R.id.loginbtnSignin);
		loginBtn.setOnClickListener(new loginListener());

		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);

		if (userID != -1) {
			Intent intent = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(intent);

		}

		NavLogin.spawn(this, this);

	}

	private class loginListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			loadUserLoginResponse(false);

		}

	}

	// Service Begin

	private class loadUserLogin implements ServerCom.iAsyncTerminatorCallback {
		private boolean stopService;

		public loadUserLogin(boolean stopService) {
			this.stopService = stopService;
		}

		@Override
		public void loadResponse(String event, Object response) {
			if (event.equals(ParserEvent.USER_LOGIN)) {
				loadUserLoginResponse(stopService);
				User u = (User) response;

				application.setCurrentUser(u.getId());
				Intent intent = new Intent(MainActivity.this,
						HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		}
	}

	private void loadUserLoginResponse(boolean stopService) {
		Context context = MainActivity.this;
		if (stopService || flag == 1) {

			flag = 0;
			new UserLoginService(context, new loadUserLogin(false), emailTxt
					.getText().toString(), passwordTxt.getText().toString())
					.exec().makeStandardProgressBox("Login...");
			Log.w("Location", "" + application.getPosLatAsString() + " " + application.getPosLongAsString());
		}
	}

	// Service End

	@Override
	public void gotologin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotoRegister() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	@Override
	public void onBackPressed(){}

}
