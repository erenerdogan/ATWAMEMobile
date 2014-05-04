package com.atwame.utils;

import java.util.ArrayList;

import com.atwame.R;
import com.atwame.model.Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeListAdapter extends BaseAdapter{
	
	private ArrayList<Content> listData;
	private LayoutInflater layoutInflater;
	
	public HomeListAdapter(Context context, ArrayList<Content> arrayList) {
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
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.home_list_row, null);
			holder = new ViewHolder();
			holder.userPicture = (ImageView) convertView.findViewById(R.id.ContentUserPicture);
			holder.userName = (TextView) convertView.findViewById(R.id.ContentUserName);
			holder.contentMessage = (TextView) convertView.findViewById(R.id.ContentMessage);
			holder.contentImage = (ImageView) convertView.findViewById(R.id.ContentMessageImage);
			holder.contentLike = (Button) convertView.findViewById(R.id.ContentLikeButton);
			holder.contentReportIt = (Button) convertView.findViewById(R.id.ContentReportIt);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Content menu = listData.get(position);
		holder.userPicture.setImageResource(R.drawable.ic_launcher);
		
		holder.contentMessage.setText(menu.getDescription());
		
		holder.contentImage.setImageResource(R.drawable.ic_launcher);
		try{
            if(menu.getUser().getName()== null) throw new NullPointerException();
            holder.userName.setText(menu.getUser().getName());
        }catch(NullPointerException e){
        	holder.userName.setText("Loading...");
        }
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView userPicture;
		TextView userName;
		ImageView contentImage;
		TextView contentMessage;
		Button contentLike;
		Button contentReportIt;
		
	}

}
