package com.atwame;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.utils.HomeListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavHome;

public class SettingsActivity extends SherlockActivity implements NavHome.iHomeNavResponder{
	
	private ImageView imageUser;
	private TextView nameTxt, emailTxt;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		NavHome.spawn(this, this);
		
		imageUser = (ImageView) findViewById(R.id.settingsUserPicture);
		nameTxt = (TextView) findViewById(R.id.settingsUserName);
		emailTxt = (TextView) findViewById(R.id.settingsEmailTxt);
		displayContent();
	}
	
	private void displayContent(){
		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);
		
		UserDao userDao = ModelFactory.getInstance(SettingsActivity.this).getDaoSession().getUserDao();
		User u = userDao.load(userID);
		
		imageUser.setImageResource(R.drawable.ic_launcher);
		nameTxt.setText(u.getName());
		emailTxt.setText(u.getEmail());
		
	}

	/*
	 * 
	 * SherlockActivity BEGIN
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		onPrepareOptionsMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		SubMenu myMenuBack = menu.addSubMenu("Refresh");
		MenuItem myMenuItemBack = myMenuBack.getItem();
		//myMenuItemBack.setOnMenuItemClickListener();//new clickBackHandler());
		myMenuItemBack.setTitle("Refresh");
		myMenuItemBack.setTitleCondensed("Refresh");
		myMenuItemBack.setIcon(R.drawable.ic_menu_refresh);
		myMenuItemBack.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		SubMenu myMenu = menu.addSubMenu("Share");
		MenuItem myMenuItem = myMenu.getItem();
		//myMenuItem.setOnMenuItemClickListener(new clickRefreshHandler());
		myMenuItem.setTitle("Share");
		myMenuItem.setTitleCondensed("Share");
		myMenuItem.setIcon(R.drawable.ic_menu_edit);
		myMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void gotoHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SettingsActivity.this,
				HomeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}


	@Override
	public void gotoWallMe() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SettingsActivity.this,
				WallMeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}


	@Override
	public void gotoSettings() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * SherlockActivity END
	 */
}
