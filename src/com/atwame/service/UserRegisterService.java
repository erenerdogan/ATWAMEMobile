package com.atwame.service;

import java.io.IOException;
import java.util.HashMap;

import com.atwame.model.Content;
import com.atwame.model.UserDao;
import com.atwame.parser.UserParser;
import com.atwame.service.BaseService.ParserExc;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.WebService;

import android.content.Context;
import android.util.Log;

public class UserRegisterService extends BaseService{
	
	private String email,password,name;

	public UserRegisterService(Context context,
			iAsyncTerminatorCallback loadResponder, String email, String password, String name) {
		super(context, loadResponder);
		myParser = new UserParser(context);
		this.email = email;
		this.password = password;
		this.name = name;
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("email", email);
		paramPairs.put("password", password);
		paramPairs.put("name", name);
		return ws.callServer("register", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);
		UserDao userDao = ModelFactory.getInstance().getDaoSession()
				.getUserDao();
		userDao.insertOrReplace(((UserParser) myParser).user);
		Log.w("User DB", "User DB ye Eklendi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.USER_REGISTER,
					((UserParser) myParser).user);// Main Activity'e gonderildi.
	}
	
	
}
