package com.atwame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.service.ContentService;
import com.atwame.service.ShareService;
import com.atwame.utils.CategoryEnum;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.HomeListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavHome;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;

public class HomeActivity extends SherlockActivity implements
		NavHome.iHomeNavResponder {

	private ArrayList<Content> arrayList;
	private AlertDialog ad;
	private ListView listView;
	private int flag;
	public static CustomApplication application;
	private LayoutInflater inflater;
	private EditText messageTxt;
	private Spinner categorySpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		listView = (ListView) findViewById(R.id.homeListView);
		flag = 1;
		application = (CustomApplication) getApplication();
		loadContentResponse(false);
		NavHome.spawn(this, this);
		inflater = this.getLayoutInflater();
		displayContent();
	}
	private ArrayList<Content> calculateDistance(ArrayList<Content> arrayList){
		ArrayList<Content> array = new ArrayList<Content>();
		double lat = application.getPosLati();
		double lng = application.getPosLong();
		double distance = application.distance;
		Location locationA = new Location("myLocation");
		locationA.setLatitude(lat);
		locationA.setLongitude(lng);
		Log.w("MyLocation","" + lat + " " + lng);
		for (Content content : arrayList) {
			Location locationB = new Location("Content Location");
			com.atwame.model.Location location = content.getLocation();
			locationB.setLatitude(Double.parseDouble(location.getLatitude()));
			locationB.setLongitude(Double.parseDouble(location.getLongitude()));
			Log.w("Content Location", "" + Double.parseDouble(location.getLatitude()) +" "+ Double.parseDouble(location.getLongitude()));
			Log.w("Result Location", "" + locationA.distanceTo(locationB));
			boolean result = locationA.distanceTo(locationB) <= distance ? true:false;
			Log.w("Result", "" + result);
			if(result){
				array.add(content);
			}
		}
		return array;
	}

	private void displayContent() {
		ContentDao contentDao = ModelFactory.getInstance(HomeActivity.this)
				.getDaoSession().getContentDao();
		arrayList = (ArrayList<Content>) contentDao.loadAll();
		
		Collections.sort(arrayList, comparator);

		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);
		listView.setAdapter(new HomeListAdapter(this, calculateDistance(arrayList), userID));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Butona Tiklandi", Toast.LENGTH_LONG).show();
				Content content = (Content) listView
						.getItemAtPosition(position);

				Intent intent = new Intent(HomeActivity.this,
						PostDetailActivity.class);
				intent.putExtra("contentID", content.getId());
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
		
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
		Context context = HomeActivity.this;
		if (stopService || flag == 1) {

			flag = 0;
			new ContentService(context, new loadContents(false)).exec()
					.makeStandardProgressBox();
			Log.w("Location", "" + application.getPosLatAsString() + " "
					+ application.getPosLongAsString());
		}
	}

	private class loadShareContents implements
			ServerCom.iAsyncTerminatorCallback {
		private boolean stopService;

		public loadShareContents(boolean stopService) {
			this.stopService = stopService;
		}

		@Override
		public void loadResponse(String event, Object response) {
			if (event.equals(ParserEvent.SHARE_CONTENT)) {
				loadShareContentResponse(stopService);
				displayContent();
			}
		}
	}

	private void loadShareContentResponse(boolean stopService) {
		Context context = HomeActivity.this;
		if (stopService || flag == 1) {

			flag = 0;

			SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
			long userID = sp.getLong("currentLoginUserID", -1);
			new ShareService(context, new loadShareContents(false), messageTxt
					.getText().toString(), userID,
					((CategoryEnum) categorySpinner.getSelectedItem())
							.getValue()).exec().makeStandardProgressBox(
					"Share Message...");
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
		myMenuItem.setOnMenuItemClickListener(new ShareHandler());
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

	// Sherlock Post Add Begin

	public class ShareHandler implements
			com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener {
		private AlertDialog myAlert;
		private Button attachmentBtn;

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			myAlert = new AlertDialog.Builder(HomeActivity.this).create();
			myAlert.setTitle("Share");
			myAlert.setIcon(R.drawable.ic_menu_edit);

			View v = inflater.inflate(R.layout.dialog_custom_alert, null);

			myAlert.setView(v);

			myAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Share",
					new addPartConfirmListener());
			myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new cancelListener());

			attachmentBtn = (Button) v.findViewById(R.id.customAttachmentBtn);
			messageTxt = (EditText) v.findViewById(R.id.customMessageEditText);
			categorySpinner = (Spinner) v.findViewById(R.id.customCategory);

			CategoryEnum[] arr = { CategoryEnum.GENEL, CategoryEnum.DUYURU,
					CategoryEnum.EGLENCE, CategoryEnum.HABER,
					CategoryEnum.KISISEL, CategoryEnum.REKLAM };
			ArrayAdapter<CategoryEnum> adapter = new ArrayAdapter<CategoryEnum>(
					HomeActivity.this, R.layout.simple_spinner_item, arr);
			categorySpinner.setAdapter(adapter);

			myAlert.show();
			return false;
		}

		private class addPartConfirmListener implements
				DialogInterface.OnClickListener {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertDialog myAlert = new AlertDialog.Builder(HomeActivity.this)
						.create();
				myAlert.setTitle("Error in input!");
				if (messageTxt.getText().toString().equalsIgnoreCase("")) {
					myAlert.setMessage("Message is Empty!!!");
					myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
							new cancelListener());
					myAlert.show();
				}

				if (myAlert.isShowing())
					return;

				Log.w("SHARE",
						messageTxt.getText().toString()
								+ " "
								+ ((CategoryEnum) categorySpinner
										.getSelectedItem()).getValue());
				loadShareContentResponse(true);
				dialog.dismiss();
			}
		}

		private class cancelListener implements DialogInterface.OnClickListener {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}
	}

	// Sherlock Post Add End

	/*
	 * SherlockActivity END
	 */

	@Override
	public void onBackPressed() {
	}

	@Override
	public void gotoHome() {
		// TODO Auto-generated method stub

	}

	@Override
	public void gotoWallMe() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(HomeActivity.this, WallMeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void gotoSettings() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
}
