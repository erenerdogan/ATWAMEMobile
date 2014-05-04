package com.atwame;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.atwame.model.Comment;
import com.atwame.model.CommentDao;
import com.atwame.model.Content;
import com.atwame.service.DetailService;
import com.atwame.utils.DetailListAdapter;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom;

public class PostDetailActivity extends SherlockActivity {
	
	private EditText commentET;
	private ImageView userImage, contentImage, commentSendImage;
	private TextView userNameTxt, contentMessageTxt;
	private ListView listview;
	private long contentID;
	private ArrayList<Comment> arrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		contentID = getIntent().getLongExtra("contentID", -1);
		if(contentID == -1){
			super.onBackPressed();
		}
		
		Content c = ModelFactory.getInstance(PostDetailActivity.this).getDaoSession().getContentDao().load(contentID);

		userImage = (ImageView) findViewById(R.id.DetailUserPicture);
		userImage.setImageResource(R.drawable.ic_launcher);
		userNameTxt = (TextView) findViewById(R.id.DetailUserName);
		userNameTxt.setText(c.getUser().getName());
		
		contentMessageTxt = (TextView) findViewById(R.id.DetailMessage);
		contentMessageTxt.setText(c.getDescription());
		contentImage = (ImageView) findViewById(R.id.DetailMessageImage);
		contentImage.setImageResource(R.drawable.ic_launcher);
		
		listview = (ListView) findViewById(R.id.commentlist);
		displayContent();
		loadCommentsResponse(true);
	}
	
	private void displayContent() {
		CommentDao commentDao = ModelFactory.getInstance(PostDetailActivity.this)
				.getDaoSession().getCommentDao();
		arrayList = (ArrayList<Comment>) commentDao._queryContent_Comments(contentID);
		
		
		SharedPreferences sp = getSharedPreferences("atwameprefs", 0);
		long userID = sp.getLong("currentLoginUserID", -1);
		listview.setAdapter(new DetailListAdapter(this, arrayList));
		
	}
	
	// Service Begin

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

				new DetailService(context, new loadComments(false),contentID).exec()
						.makeStandardProgressBox();
				
			}
		}
		
		// Service End
}
