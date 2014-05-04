package com.atwame.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Comment;
import com.atwame.model.Content;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.utils.ServerCom;

public class DetailParser extends BaseParser {

	public List<Comment> commentList;
	
	public DetailParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public DetailParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		Log.w("Comments Parser", json);
		try {

			commentList = new ArrayList<Comment>();
			JSONObject o = new JSONObject(json);
			JSONArray objArray = o.getJSONArray("comments");
					
			for (int i = 0; i < objArray.length(); i++) {
				JSONObject objComment = objArray.getJSONObject(i);
				
				JSONObject objUser = objComment.getJSONObject("user");
				User user = new User();
				user.setId(objUser.getLong("id"));
				user.setName(objUser.getString("name"));
				user.setPicture(objUser.getString("picture"));
				
				
				Comment comment = new Comment();
				comment.setId(objComment.getLong("id"));
				comment.setDescription(objComment.getString("description"));
				
				comment.setUser(user);
				
				comment.setContent_id(objComment.getLong("content_id"));
				commentList.add(comment);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
