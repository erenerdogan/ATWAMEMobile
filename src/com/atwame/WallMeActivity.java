package com.atwame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.service.ContentService;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.HomeListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavHome;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;

public class WallMeActivity extends SherlockActivity implements NavHome.iHomeNavResponder{

	private ArrayList<Content> arrayList;
	public static CustomApplication application;
	private ListView listView;
	private int flag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wall_me);
		NavHome.spawn(this, this);
		listView = (ListView) findViewById(R.id.homeListView);
		application = (CustomApplication) getApplication();
		displayContent();
		flag =1;
	}
	
	
	
	private void displayContent(){
		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);
		
		UserDao userDao = ModelFactory.getInstance(WallMeActivity.this).getDaoSession().getUserDao();
		User u = userDao.load(userID);
		arrayList = (ArrayList<Content>) u.getContents();
		Collections.sort(arrayList, comparator);
		
		listView.setAdapter(new HomeListAdapter(this, arrayList,userID));
		listView.postInvalidate();
	}
	
	Comparator<Content> comparator = new Comparator<Content>() {

		@Override
		public int compare(Content o1, Content o2) {
			return o2.getId().compareTo(o1.getId());
		}
	};
	
	// Service Begin

		private class loadContents implements ServerCom.iAsyncTerminatorCallback {
			private boolean stopService;

			public loadContents(boolean stopService) {
				this.stopService = stopService;
			}

			@Override
			public void loadResponse(String event, Object response) {
				if (event.equals(ParserEvent.CONTENT_HOME)) {
					loadContentResponse(stopService);
					displayContent();
				}
			}
		}

		private void loadContentResponse(boolean stopService) {
			Context context = WallMeActivity.this;
			if (stopService || flag == 1) {

				flag = 0;
				new ContentService(context, new loadContents(false)).exec()
						.makeStandardProgressBox();
				Log.w("Location", "" + application.getPosLatAsString() + " "
						+ application.getPosLongAsString());
			}
		}

		// Service End
	
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
		myMenuItemBack.setOnMenuItemClickListener(new clickRefreshHandler());
		
		myMenuItemBack.setTitle("Refresh");
		myMenuItemBack.setTitleCondensed("Refresh");
		myMenuItemBack.setIcon(R.drawable.ic_menu_refresh);
		myMenuItemBack.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		SubMenu myMenu = menu.addSubMenu("Share");
		MenuItem myMenuItem = myMenu.getItem();
		// myMenuItem.setOnMenuItemClickListener(new clickRefreshHandler());
		myMenuItem.setTitle("Share");
		myMenuItem.setTitleCondensed("Share");
		myMenuItem.setIcon(R.drawable.ic_menu_edit);
		myMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onPrepareOptionsMenu(menu);
	}

	private class clickRefreshHandler implements
			com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			loadContentResponse(true);
			return false;
		}
	}

	/*
	 * SherlockActivity END
	 */

	@Override
	public void gotoHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(WallMeActivity.this,
				HomeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}


	@Override
	public void gotoWallMe() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void gotoSettings() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(WallMeActivity.this,
				SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
