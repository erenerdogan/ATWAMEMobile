package com.atwame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.atwame.model.Category;
import com.atwame.model.CategoryDao;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.utils.CategoryEnum;
import com.atwame.utils.HomeListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavHome;

public class SettingsActivity extends SherlockActivity implements NavHome.iHomeNavResponder{
	
	private ImageView imageUser;
	private TextView nameTxt, emailTxt;
	private CheckBox genelCB,duyuruCB,haberCB, eglenceCB, kisiselCB, reklamCB;
	private Button saveBtn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		NavHome.spawn(this, this);
		
		settingCategory();
		
		imageUser = (ImageView) findViewById(R.id.settingsUserPicture);
		nameTxt = (TextView) findViewById(R.id.settingsUserName);
		emailTxt = (TextView) findViewById(R.id.settingsEmailTxt);
		
		genelCB = (CheckBox) findViewById(R.id.settingsCheckboGenel);
		duyuruCB = (CheckBox) findViewById(R.id.settingsCheckboxDuyuru);
		eglenceCB = (CheckBox) findViewById(R.id.settingsCheckboxEglence);
		haberCB = (CheckBox) findViewById(R.id.settingsCheckboxHaber);
		kisiselCB = (CheckBox) findViewById(R.id.settingsCheckboxKisisel);
		reklamCB = (CheckBox) findViewById(R.id.settingsCheckboxReklam);
		
		saveBtn = (Button) findViewById(R.id.settingsSaveButton);
		saveBtn.setOnClickListener(new SaveListener());
		
		displayContent();
	}
	
	private void settingCategory(){
		CategoryDao categoryDao = ModelFactory.getInstance(SettingsActivity.this).getDaoSession().getCategoryDao();
		int size = categoryDao.loadAll().size();
		Log.w("Category Size", "" + size);
		CategoryEnum[] eNum = CategoryEnum.values();
		for (int i= 1;  i < eNum.length; i++ ) {
			CategoryEnum cEnum = eNum[i];
			Category c = categoryDao.load((long) cEnum.getValue());
			if(c==null){
				c = new Category();
				c.setId((long) cEnum.getValue());
				c.setMember(true);
				c.setName(cEnum.toString());
				categoryDao.insertOrReplace(c);
				Log.w("Category Enum","" + c.getId() +" "+ c.getName()+" "+c.getMember());
			}
		}
		
	}
	
	private void displayContent(){
		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);
		
		UserDao userDao = ModelFactory.getInstance(SettingsActivity.this).getDaoSession().getUserDao();
		User u = userDao.load(userID);
		
		imageUser.setImageResource(R.drawable.ic_launcher);
		nameTxt.setText(u.getName());
		emailTxt.setText(u.getEmail());
		
		CategoryDao categoryDao = ModelFactory.getInstance(SettingsActivity.this).getDaoSession().getCategoryDao();
		List<Category> array = categoryDao.loadAll();
		for (Category category : array) {
			if(category.getId() == CategoryEnum.GENEL.getValue()) genelCB.setChecked(category.getMember());
			else if(category.getId() == CategoryEnum.DUYURU.getValue()) duyuruCB.setChecked(category.getMember());
			else if(category.getId() == CategoryEnum.EGLENCE.getValue()) eglenceCB.setChecked(category.getMember());
			else if(category.getId() == CategoryEnum.HABER.getValue()) haberCB.setChecked(category.getMember());
			else if(category.getId() == CategoryEnum.KISISEL.getValue()) kisiselCB.setChecked(category.getMember());
			else if(category.getId() == CategoryEnum.REKLAM.getValue()) reklamCB.setChecked(category.getMember());
		}
		
	}
	
	private class SaveListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CategoryDao categoryDao = ModelFactory.getInstance(SettingsActivity.this).getDaoSession().getCategoryDao();
			List<Category> array = categoryDao.loadAll();
			for (Category category : array) {
				if(category.getId() == CategoryEnum.GENEL.getValue()) category.setMember(genelCB.isChecked());
				else if(category.getId() == CategoryEnum.DUYURU.getValue()) category.setMember(duyuruCB.isChecked());
				else if(category.getId() == CategoryEnum.EGLENCE.getValue()) category.setMember(eglenceCB.isChecked());
				else if(category.getId() == CategoryEnum.HABER.getValue()) category.setMember(haberCB.isChecked());
				else if(category.getId() == CategoryEnum.KISISEL.getValue()) category.setMember(kisiselCB.isChecked());
				else if(category.getId() == CategoryEnum.REKLAM.getValue()) category.setMember(reklamCB.isChecked());
				categoryDao.insertOrReplace(category);
			}
		}
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
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	/*
	 * SherlockActivity END
	 */
}
