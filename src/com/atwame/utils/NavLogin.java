package com.atwame.utils;

import com.atwame.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class NavLogin {
	public static interface iLoginNavResponder{
		void gotologin();
		void gotoRegister();
	}
	public static void spawn(Activity activity, final iLoginNavResponder listener){
		
	    LayoutInflater inflater = activity.getLayoutInflater();
	    inflater.inflate(R.layout.navlogin, (ViewGroup) activity.findViewById(R.id.footer));
	    
	    Button myLoginButton = (Button) activity.findViewById(R.id.navLoginBtn);
	    Button myRegisterButton = (Button) activity.findViewById(R.id.navRegisterBtn);
	    
	    if(activity.getTitle().toString().equals(activity.getResources().getString(R.string.app_name))){
	    	myLoginButton.setBackgroundColor(Color.GRAY);
	    }else if(activity.getTitle().toString().equals(activity.getResources().getString(R.string.title_activity_register))){
	    	myRegisterButton.setBackgroundColor(Color.GRAY);
	    }
	    
	    
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
