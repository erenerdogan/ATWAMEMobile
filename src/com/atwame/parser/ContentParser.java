package com.atwame.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Content;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ServerCom;

public class ContentParser extends BaseParser {

	public List<Content> contentList;
	

	public ContentParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public ContentParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		Log.w("Content Parser", json);
		try {
			contentList = new ArrayList<Content>();
			JSONObject o = new JSONObject(json);
			JSONArray objArray = o.getJSONArray("contents");
					
			for (int i = 0; i < objArray.length(); i++) {
				JSONObject obj = objArray.getJSONObject(i);
				
				JSONObject objUser = obj.getJSONObject("user");
				User user = new User();
				user.setId(objUser.getLong("id"));
				user.setName(objUser.getString("name"));
				user.setPicture(objUser.getString("picture"));
				
				
				JSONObject objLocation = obj.getJSONObject("location");
				Location location = new Location();
				location.setId(objLocation.getLong("id"));
				location.setLatitude(objLocation.getString("latitude"));
				location.setLongitude(objLocation.getString("longitude"));
				
				/*
				JSONObject objAttachment = obj.getJSONObject("attachment");
				Attachment attachment = new Attachment();
				attachment.setId(objAttachment.getLong("id"));
				attachment.setUrl(objAttachment.getString("url"));
				Log.w("objAttachment",""+attachment.getUrl());
				*/
				JSONObject objContent = obj.getJSONObject("content");
				Content content = new Content();
				content.setId(objContent.getLong("id"));
				content.setDescription(objContent.getString("description"));
				content.setCategory_id(objContent.getLong("category_id"));
				//content.setAttachment(attachment);
				content.setUser(user);
				content.setLocation(location);
				content.setLike(false);
				
				
				contentList.add(content);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
