package com.atwame;

import java.util.HashMap;

import org.json.JSONArray;

import com.atwame.parser.UserParser;
import com.atwame.service.WebService;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements NavLogin.iLoginNavResponder{
	public static CustomApplication application;
	private EditText emailTxt, passwordTxt;
	private Button loginBtn;
	private LayoutInflater inflater;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        application = (CustomApplication)getApplication();

        
        inflater = this.getLayoutInflater();
	    inflater.inflate(R.layout.navlogin, (ViewGroup) findViewById(R.id.login));
        
        
        emailTxt = (EditText) findViewById(R.id.loginemailTxt);
        passwordTxt = (EditText) findViewById(R.id.loginpasswordTxt);
        
        loginBtn = (Button) findViewById(R.id.loginbtnSignin);
        loginBtn.setOnClickListener(new loginListener());
        
        SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
        long userID = sp.getLong("currentLoginUserID", -1);
        

        if(userID != -1){
        	Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        	startActivity(intent);
        	
        }
        
        NavLogin.spawn(this, this);
        
    }
    
    private class loginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			HashMap<String, String> data = new HashMap<String, String>();
	        data.put("email", emailTxt.getText().toString());
	        data.put("password", passwordTxt.getText().toString());
	        WebService webService = new WebService(MainActivity.this, "http://atwame.herokuapp.com/login",data);
	        webService.exec("POST").makeStandardProgressBox();
	        String result = webService.callServerRequest();
	        UserParser u = new UserParser();
	        u.jsonParser(result);
	        if(u.getUserID() != -1) application.setCurrentUser(u.getUserID());
		}
    	
    }

	@Override
	public void gotologin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotoRegister() {
		// TODO Auto-generated method stub
		
	}


}
