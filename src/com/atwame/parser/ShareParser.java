package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Content;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.utils.ServerCom;

public class ShareParser extends BaseParser {

	public Content content;
	
	public ShareParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public ShareParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		Log.w("New Content Parser", json);
		try {

			JSONObject o = new JSONObject(json);

			JSONObject objUser = o.getJSONObject("user");
			User user = new User();
			user.setId(objUser.getLong("id"));
			user.setName(objUser.getString("name"));
			user.setPicture(objUser.getString("picture"));

			JSONObject objLocation = o.getJSONObject("location");
			Location location = new Location();
			location.setId(objLocation.getLong("id"));
			location.setLatitude(objLocation.getString("latitude"));
			location.setLongitude(objLocation.getString("longitude"));
			/*
			JSONObject objAttachment = o.getJSONObject("attachment");
			Attachment attachment = new Attachment();
			attachment.setId(objAttachment.getLong("id"));
			attachment.setUrl(objAttachment.getString("url"));
			*/
			JSONObject objContent = o.getJSONObject("content");
			content = new Content();
			content.setId(objContent.getLong("id"));
			content.setDescription(objContent.getString("description"));
			content.setCategory_id(objContent.getLong("category_id"));
			//content.setAttachment(attachment);
			content.setUser(user);
			content.setLocation(location);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
