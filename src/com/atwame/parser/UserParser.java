package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ServerCom;

public class UserParser extends BaseParser {

	public User user;

	public UserParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public UserParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		Log.w("User Parser", json);
		try {
			JSONObject obj = new JSONObject(json);
			user = new User();
			user.setId(obj.getLong("id"));
			user.setEmail(obj.getString("email"));
			user.setName(obj.getString("name"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
