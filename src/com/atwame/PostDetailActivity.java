package com.atwame;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.atwame.model.Comment;
import com.atwame.model.CommentDao;
import com.atwame.model.Content;
import com.atwame.service.CommentService;
import com.atwame.service.DetailService;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.DetailListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.NavHome;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;

public class PostDetailActivity extends SherlockActivity implements
		NavHome.iHomeNavResponder {

	private EditText commentET;
	private ImageView userImage, contentImage;
	private ImageButton commentSend;
	private TextView userNameTxt, contentMessageTxt;
	private ListView listview;
	private long contentID;
	private ArrayList<Comment> arrayList;
	public static CustomApplication application;
	public long userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		application = (CustomApplication) getApplication();
		NavHome.spawn(this, this);
		contentID = getIntent().getLongExtra("contentID", -1);
		if (contentID == -1) {
			super.onBackPressed();
		}

		Content c = ModelFactory.getInstance(PostDetailActivity.this)
				.getDaoSession().getContentDao().load(contentID);

		userImage = (ImageView) findViewById(R.id.DetailUserPicture);
		userImage.setImageResource(R.drawable.ic_launcher);
		userNameTxt = (TextView) findViewById(R.id.DetailUserName);
		userNameTxt.setText(c.getUser().getName());

		contentMessageTxt = (TextView) findViewById(R.id.DetailMessage);
		contentMessageTxt.setText(c.getDescription());
		contentImage = (ImageView) findViewById(R.id.DetailMessageImage);
		contentImage.setImageResource(R.drawable.ic_launcher);

		commentET = (EditText) findViewById(R.id.DetailcommentTxt);

		commentSend = (ImageButton) findViewById(R.id.DetailCommentImageButton);
		commentSend.setOnClickListener(new sendCommentListener());

		listview = (ListView) findViewById(R.id.commentlist);
		displayContent();
		loadCommentsResponse(true);

		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		userID = sp.getLong("currentLoginUserID", -1);
	}

	private void displayContent() {
		CommentDao commentDao = ModelFactory
				.getInstance(PostDetailActivity.this).getDaoSession()
				.getCommentDao();
		arrayList = (ArrayList<Comment>) commentDao
				._queryContent_Comments(contentID);

		listview.setAdapter(new DetailListAdapter(this, arrayList));

	}

	// Send Comment Button Begin

	private class sendCommentListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Toast.makeText(PostDetailActivity.this, "Butona Tıklandı",
					Toast.LENGTH_LONG).show();
			if (commentET.getText().toString().equalsIgnoreCase("")) {
				AlertDialog myAlert = new AlertDialog.Builder(
						PostDetailActivity.this).create();
				myAlert.setTitle("Error in input!");

				myAlert.setMessage("Comment is Empty!!!");
				myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
						new cancelListener());
				myAlert.show();

				if (myAlert.isShowing())
					return;
			}
			sendCommentResponse(true);
			
		}
	}

	private class cancelListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	// Send Comment Button End

	// Service Begin

	private class sendComment implements ServerCom.iAsyncTerminatorCallback {
		private boolean stopService;

		public sendComment(boolean stopService) {
			this.stopService = stopService;
		}

		@Override
		public void loadResponse(String event, Object response) {
			if (event.equals(ParserEvent.CONTENT_COMMENTS_ADD)) {
				sendCommentResponse(stopService);
				commentET.setText("");
				displayContent();
				
			}
		}
	}

	private void sendCommentResponse(boolean stopService) {
		Context context = PostDetailActivity.this;
		if (stopService) {

			new CommentService(context, new sendComment(false), commentET
					.getText().toString(), userID, contentID).exec()
					.makeStandardProgressBox("Send Comment...");

		}
	}

	private class loadComments implements ServerCom.iAsyncTerminatorCallback {
		private boolean stopService;

		public loadComments(boolean stopService) {
			this.stopService = stopService;
		}

		@Override
		public void loadResponse(String event, Object response) {
			if (event.equals(ParserEvent.CONTENT_COMMENTS)) {
				loadCommentsResponse(stopService);
				displayContent();
			}
		}
	}

	private void loadCommentsResponse(boolean stopService) {
		Context context = PostDetailActivity.this;
		if (stopService) {

			new DetailService(context, new loadComments(false), contentID)
					.exec().makeStandardProgressBox();

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
		SubMenu myMenuBack = menu.addSubMenu("Back");
		MenuItem myMenuItemBack = myMenuBack.getItem();
		myMenuItemBack.setOnMenuItemClickListener(new backHandler());// new clickBackHandler());
		myMenuItemBack.setTitle("Back");
		myMenuItemBack.setTitleCondensed("Back");
		myMenuItemBack.setIcon(R.drawable.ic_menu_back);
		myMenuItemBack.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		//SubMenu myMenu = menu.addSubMenu("Share");
		//MenuItem myMenuItem = myMenu.getItem();
		// myMenuItem.setOnMenuItemClickListener(new clickRefreshHandler());
		//myMenuItem.setTitle("Share");
		//myMenuItem.setTitleCondensed("Share");
		//myMenuItem.setIcon(R.drawable.ic_menu_edit);
		//myMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
		//		| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onPrepareOptionsMenu(menu);
	}

	private class backHandler implements
			com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			onBackPressed();
			return false;
		}
	}


	/*
	 * SherlockActivity END
	 */
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	@Override
	public void gotoHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PostDetailActivity.this, HomeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void gotoWallMe() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PostDetailActivity.this, WallMeActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	@Override
	public void gotoSettings() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PostDetailActivity.this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	// Service End
}
