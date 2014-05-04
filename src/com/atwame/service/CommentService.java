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
import com.atwame.parser.CommentParser;
import com.atwame.parser.ContentParser;
import com.atwame.parser.DetailParser;
import com.atwame.parser.ShareParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class CommentService extends BaseService {

	private long contentID;
	private long userID;
	private String message;

	public CommentService(Context context,
			iAsyncTerminatorCallback loadResponder, String message,
			long userID, long contentID) {
		super(context, loadResponder);

		this.contentID = contentID;
		this.userID = userID;
		this.message = message;
		myParser = new CommentParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("content_id", "" + contentID);
		paramPairs.put("user_id", "" + userID);
		paramPairs.put("message", message);
		return ws.callServer("commentadd", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);
		Comment c = ((CommentParser) myParser).comment;
		CommentDao commentDao = ModelFactory.getInstance().getDaoSession()
				.getCommentDao();
		Comment comment = commentDao.load(c.getId());
		if (comment == null) {
			commentDao.insert(c);
		}
		Log.w("Comments Add DB", "Comments Add DB ye Eklendi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.CONTENT_COMMENTS_ADD, null);
	}

}
