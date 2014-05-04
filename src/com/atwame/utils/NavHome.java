package com.atwame.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.atwame.R;

public class NavHome {
	public static interface iHomeNavResponder{
		void gotoHome();
		void gotoWallMe();
		void gotoSettings();
	}
	public static void spawn(Activity activity, final iHomeNavResponder listener){
		
	    LayoutInflater inflater = activity.getLayoutInflater();
	    inflater.inflate(R.layout.navhome, (ViewGroup) activity.findViewById(R.id.footer));
	    
	    Button myHomeButton = (Button) activity.findViewById(R.id.navHomeBtn);
	    Button myWallMeButton = (Button) activity.findViewById(R.id.navWallMeBtn);
	    Button mySettingsButton = (Button) activity.findViewById(R.id.navSettingsBtn);
	    
	    if(activity.getTitle().toString().equals(activity.getResources().getString(R.string.title_activity_home))){
	    	myHomeButton.setBackgroundColor(Color.GRAY);
	    }else if(activity.getTitle().toString().equals(activity.getResources().getString(R.string.title_activity_wall_me))){
	    	myWallMeButton.setBackgroundColor(Color.GRAY);
	    }else if(activity.getTitle().toString().equals(activity.getResources().getString(R.string.title_activity_settings))){
	    	mySettingsButton.setBackgroundColor(Color.GRAY);
	    }
	    
	    
	    myHomeButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.gotoHome();
				
			}
		});
	    myWallMeButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.gotoWallMe();
			}
		});
	    mySettingsButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				listener.gotoSettings();
			}
		});
	}
}
