package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.utils.ModelFactory;

public class UserParser implements BaseParser {
	
	private Long userID;

	public UserParser() {
		super();
		userID = (long) -1;
	}

	@Override
	public void jsonParser(String json) {
		Log.w("User Parser", json);
		try {
			JSONObject obj = new JSONObject(json);
			if (obj.getBoolean("result") == true) {
				UserDao userDao = ModelFactory.getInstance().getDaoSession()
						.getUserDao();
				User user = new User();
				userID = obj.getLong("id");
				user.setId(userID);
				user.setEmail(obj.getString("email"));
				user.setName(obj.getString("name"));
				
				userDao.insertOrReplace(user);
				Log.w("User DB", "User DB ye Eklendi.");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getUserID() {
		return userID;
	}
	
}
