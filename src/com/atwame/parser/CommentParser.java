package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Comment;
import com.atwame.utils.ServerCom;

public class CommentParser extends BaseParser {

	public Comment comment;
	
	public CommentParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public CommentParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		Log.w("Comments Add Parser", json);
		try {
			JSONObject objComment = new JSONObject(json);
			comment = new Comment();
			comment.setId(objComment.getLong("id"));
			comment.setDescription(objComment.getString("description"));
			comment.setUser_id(objComment.getLong("user_id"));
			comment.setContent_id(objComment.getLong("content_id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
