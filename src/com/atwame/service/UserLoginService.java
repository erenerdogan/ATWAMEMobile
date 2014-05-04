package com.atwame.service;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.atwame.model.UserDao;
import com.atwame.parser.UserParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class UserLoginService extends BaseService {

	private String email, password;

	public UserLoginService(Context context,
			iAsyncTerminatorCallback loadResponder, String email,
			String password) {
		super(context, loadResponder);
		myParser = new UserParser(context);
		this.email = email;
		this.password = password;
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("email", email);
		paramPairs.put("password", password);
		return ws.callServer("login", paramPairs);
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
			loadResponder.loadResponse(ParserEvent.USER_LOGIN,
					((UserParser) myParser).user);// Main Activity'e gonderildi.
	}

}
