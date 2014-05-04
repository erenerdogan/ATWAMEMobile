package com.atwame.utils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.atwame.HomeActivity;
import com.atwame.R;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.service.ContentService;
import com.atwame.service.ReportService;

public class HomeListAdapter extends BaseAdapter {

	private ArrayList<Content> listData;
	private LayoutInflater layoutInflater;
	private Context context;
	private Long userID;
	private EditText messageTxt;
	

	public HomeListAdapter(Context context, ArrayList<Content> arrayList, Long userID) {
		this.context = context;
		this.listData = arrayList;
		this.layoutInflater = LayoutInflater.from(context);
		this.userID = userID;
	}

	@Override
	public int getCount() {
		return this.listData.size();
	}

	@Override
	public Object getItem(int position) {
		return this.listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.home_list_row, null);
			holder = new ViewHolder();
			holder.userPicture = (ImageView) convertView
					.findViewById(R.id.ContentUserPicture);
			holder.userName = (TextView) convertView
					.findViewById(R.id.ContentUserName);
			holder.contentMessage = (TextView) convertView
					.findViewById(R.id.ContentMessage);
			holder.contentImage = (ImageView) convertView
					.findViewById(R.id.ContentMessageImage);
			holder.contentLike = (ImageButton) convertView
					.findViewById(R.id.ContentLikeButton);
			holder.contentReportIt = (Button) convertView
					.findViewById(R.id.ContentReportIt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Content menu = listData.get(position);
		
		
		holder.userPicture.setImageResource(R.drawable.ic_launcher);

		holder.contentMessage.setText(menu.getDescription());

		holder.contentImage.setImageResource(R.drawable.ic_launcher);

		holder.contentReportIt.setOnClickListener(new reportListener(menu));

		try {
			if (menu.getUser().getName() == null)
				throw new NullPointerException();
			holder.userName.setText(menu.getUser().getName());
		} catch (NullPointerException e) {
			holder.userName.setText("Loading...");
		}

		try {
			if (menu.getLike() == null)
				throw new NullPointerException();
			else if (menu.getLike()) {
				holder.contentLike.setImageResource(R.drawable.love);
				holder.contentLike.setOnClickListener(new likeListener(menu));
			} else {
				holder.contentLike.setImageResource(R.drawable.like);
				holder.contentLike.setOnClickListener(new loveListener(menu));
			}
		} catch (NullPointerException e) {
			holder.contentLike.setImageResource(R.drawable.love);
			holder.contentLike.setOnClickListener(new loveListener(menu));
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView userPicture;
		TextView userName;
		ImageView contentImage;
		TextView contentMessage;
		ImageButton contentLike;
		Button contentReportIt;

	}

	private class likeListener implements View.OnClickListener {
		Content content;

		public likeListener(Content content) {
			this.content = content;
		}

		@Override
		public void onClick(View v) {
			ImageButton i = (ImageButton) v;
			i.setImageResource(R.drawable.like);
			i.setOnClickListener(new loveListener(content));

			ContentDao c = ModelFactory.getInstance().getDaoSession()
					.getContentDao();
			content.setLike(false);
			c.insertOrReplace(content);

		}

	}

	private class loveListener implements View.OnClickListener {
		Content content;

		public loveListener(Content content) {
			this.content = content;
		}

		@Override
		public void onClick(View v) {

			ImageButton i = (ImageButton) v;
			i.setImageResource(R.drawable.love);
			i.setOnClickListener(new likeListener(content));
			ContentDao c = ModelFactory.getInstance().getDaoSession()
					.getContentDao();
			content.setLike(true);
			c.insertOrReplace(content);
		}

	}

	private class reportListener implements View.OnClickListener {
		private AlertDialog myAlert;
		private Content content;
		
		 public reportListener(Content content) {
			 this.content = content;
		}
		

		@Override
		public void onClick(View v) {
			Toast.makeText(context, "Butona Tıklandı", Toast.LENGTH_LONG)
					.show();
			myAlert = new AlertDialog.Builder(context).create();
			myAlert.setTitle("Report It");
			myAlert.setIcon(R.drawable.ic_menu_edit);

			View view = layoutInflater.inflate(R.layout.dialog_report_it_alert,
					null);

			myAlert.setView(view);

			myAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Report It",
					new ReportItListener(content));
			myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
					new cancelListener());

			messageTxt = (EditText) view.findViewById(R.id.customReportEditText);
			myAlert.show();

		}

		private class ReportItListener implements
				DialogInterface.OnClickListener {
			private Content content;
			public ReportItListener(Content content) {
				this.content = content;
			}
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AlertDialog myAlert = new AlertDialog.Builder(context).create();
				myAlert.setTitle("Error in input!");
				if (messageTxt.getText().toString().equalsIgnoreCase("")) {
					myAlert.setMessage("Message is Empty!!!");
					myAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
							new cancelListener());
					myAlert.show();
				}

				if (myAlert.isShowing())
					return;

				Log.w("Report It", messageTxt.getText().toString());
				sendReportResponse(true, content);
				dialog.dismiss();
			}
		}

		private class sendReport implements ServerCom.iAsyncTerminatorCallback {
			private boolean stopService;
			private Content content;

			public sendReport(boolean stopService, Content content) {
				this.stopService = stopService;
				this.content = content;
			}

			@Override
			public void loadResponse(String event, Object response) {
				if (event.equals(ParserEvent.SEND_REPORT)) {
					sendReportResponse(stopService,content);
				}
			}
		}

		private void sendReportResponse(boolean stopService, Content content) {

			if (stopService) {
				new ReportService(context, new sendReport(false, content), messageTxt.getText().toString(), userID, content.getId()).exec()
				.makeStandardProgressBox();
				
			}
		}

		private class cancelListener implements DialogInterface.OnClickListener {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}

	}

}
