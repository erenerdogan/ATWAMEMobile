package com.atwame.service;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Comment;
import com.atwame.model.CommentDao;
import com.atwame.model.Content;
import com.atwame.model.ContentDao;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.model.UserDao;
import com.atwame.parser.ContentParser;
import com.atwame.parser.DetailParser;
import com.atwame.parser.ShareParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class DetailService extends BaseService {

	private long contentID;

	public DetailService(Context context,
			iAsyncTerminatorCallback loadResponder, long contentID) {
		super(context, loadResponder);
		
		this.contentID = contentID;
		myParser = new DetailParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("content_id", ""+contentID);
		return ws.callServer("comments", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);
		for (Comment c : ((DetailParser) myParser).commentList) {

			CommentDao commentDao = ModelFactory.getInstance().getDaoSession()
					.getCommentDao();
			Comment comment = commentDao.load(c.getId());
			
			if (comment == null) {
				
				User user = c.getUser();
				UserDao userDao = ModelFactory.getInstance().getDaoSession()
						.getUserDao();
				User u = userDao.load(user.getId());
				if (u == null) {
					userDao.insert(user);
				}
				
				commentDao.insert(c);
			}
		}
		Log.w("Comments DB", "Comments DB ye Eklendi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.CONTENT_COMMENTS, null);
	}

}
