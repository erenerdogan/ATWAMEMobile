package com.atwame.service;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.parser.ContentParser;
import com.atwame.parser.ShareParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class ShareService extends BaseService {

	private String message;
	private Long userID;
	private int categoryID;

	public ShareService(Context context,
			iAsyncTerminatorCallback loadResponder, String message,
			Long userID, int categoryID) {
		super(context, loadResponder);
		this.message = message;
		this.userID = userID;
		this.categoryID = categoryID;
		myParser = new ShareParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("description", message);
		paramPairs.put("user_id", userID.toString());
		paramPairs.put("category_id", ""+categoryID);
		return ws.callServer("new", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);
		ContentDao contentDao = ModelFactory.getInstance().getDaoSession()
				.getContentDao();
		contentDao.insertOrReplace(((ShareParser) myParser).content);
		Content c = ((ShareParser) myParser).content;
		User user = c.getUser();
		UserDao userDao = ModelFactory.getInstance().getDaoSession()
				.getUserDao();
		User u = userDao.load(user.getId());
		if (u == null) {
			userDao.insert(user);
		}
		//Attachment attachment = c.getAttachment();
		//ModelFactory.getInstance().getDaoSession().getAttachmentDao().insertOrReplace(attachment);
		Location location = c.getLocation();
		ModelFactory.getInstance().getDaoSession().getLocationDao()
				.insertOrReplace(location);

		Log.w("Share Contents DB", "Share Contents DB ye Eklendi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.CONTENT_HOME, null);// Home
																		// Activity'e
																		// gonderildi.
	}

}
