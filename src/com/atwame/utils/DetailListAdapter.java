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
import com.atwame.model.Comment;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.service.ContentService;
import com.atwame.service.ReportService;

public class DetailListAdapter extends BaseAdapter {

	private ArrayList<Comment> listData;
	private LayoutInflater layoutInflater;
	private Context context;
	

	public DetailListAdapter(Context context, ArrayList<Comment> arrayList) {
		this.context = context;
		this.listData = arrayList;
		this.layoutInflater = LayoutInflater.from(context);
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
			convertView = layoutInflater.inflate(R.layout.detail_list_row, null);
			holder = new ViewHolder();
			holder.userPicture = (ImageView) convertView.findViewById(R.id.commentUserPicture);
			holder.userName = (TextView) convertView.findViewById(R.id.commentUserName);
			holder.commentMessage = (TextView) convertView.findViewById(R.id.commentMessage);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Comment menu = listData.get(position);
		
		
		holder.userPicture.setImageResource(R.drawable.ic_launcher);
		holder.commentMessage.setText(menu.getDescription());


		try {
			if (menu.getUser().getName() == null)
				throw new NullPointerException();
			holder.userName.setText(menu.getUser().getName());
		} catch (NullPointerException e) {
			holder.userName.setText("Loading...");
		}


		return convertView;
	}

	static class ViewHolder {
		ImageView userPicture;
		TextView userName;
		TextView commentMessage;

	}
}
