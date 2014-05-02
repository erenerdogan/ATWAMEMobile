package com.atwame.utils;

import com.atwame.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class NavLogin {
	public static interface iLoginNavResponder{
		void gotologin();
		void gotoRegister();
	}
	public static void spawn(Activity activity, final iLoginNavResponder listener){
	    LayoutInflater inflater = activity.getLayoutInflater();
	    inflater.inflate(R.layout.navlogin, (ViewGroup) activity.findViewById(R.id.login));
	    
	    Button myLoginButton = (Button) activity.findViewById(R.id.navLoginBtn);
	    Button myRegisterButton = (Button) activity.findViewById(R.id.navRegisterBtn);
	    
	    myLoginButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.gotologin();
			}
		});
	    myRegisterButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.gotoRegister();
			}
		});
	}
}
