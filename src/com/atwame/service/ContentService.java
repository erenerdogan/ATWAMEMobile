package com.atwame.service;

import java.io.IOException;
import java.util.HashMap;

import com.atwame.model.Attachment;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.parser.ContentParser;
import com.atwame.parser.UserParser;
import com.atwame.service.BaseService.ParserExc;
import com.atwame.utils.CategoryEnum;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.WebService;

import android.content.Context;
import android.util.Log;

public class ContentService extends BaseService {

	public ContentService(Context context,
			iAsyncTerminatorCallback loadResponder) {
		super(context, loadResponder);
		myParser = new ContentParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		return ws.callServer("contents", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);

		for (Content c : ((ContentParser) myParser).contentList) {

			ContentDao contentDao = ModelFactory.getInstance().getDaoSession()
					.getContentDao();
			Content content = contentDao.load(c.getId());
			
			if (content == null) {
				Log.w("Content parseJSON"," Eklendi.");
				User user = c.getUser();
				UserDao userDao = ModelFactory.getInstance().getDaoSession()
						.getUserDao();
				User u = userDao.load(user.getId());
				if (u == null) {
					userDao.insert(user);
				}
				/*
				 * Attachment attachment = c.getAttachment();
				 * ModelFactory.getInstance
				 * ().getDaoSession().getAttachmentDao().
				 * insertOrReplace(attachment);
				 */
				Location location = c.getLocation();
				ModelFactory.getInstance().getDaoSession().getLocationDao()
						.insertOrReplace(location);
				
				contentDao.insert(c);
			}
		}
		Log.w("Contents DB", "Contents DB ye Eklendi.");
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
