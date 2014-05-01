package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

public class UserParser implements BaseParser {
	private String json;

	public UserParser(String json) {
		super();
		this.json = json;
	}

	@Override
	public Object jsonParser(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String s = "";
			if (obj.getBoolean("result") == true) {
				s = obj.getString("email") + " " + obj.getString("name");
			}
			return s;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
