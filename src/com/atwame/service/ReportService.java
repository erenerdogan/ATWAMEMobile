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
import com.atwame.parser.ReportParser;
import com.atwame.parser.ShareParser;
import com.atwame.utils.ModelFactory;
import com.atwame.utils.ParserEvent;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

public class ReportService extends BaseService {

	private String message;
	private Long userID;
	private Long contentID;

	public ReportService(Context context,
			iAsyncTerminatorCallback loadResponder, String message,
			Long userID, Long contentID) {
		super(context, loadResponder);
		this.message = message;
		this.userID = userID;
		this.contentID = contentID;
		myParser = new ReportParser(context);
	}

	@Override
	protected String sendRequestGetJSON(WebService ws,
			HashMap<String, String> paramPairs) throws IOException {
		paramPairs.put("message", message);
		paramPairs.put("user_id", userID.toString());
		paramPairs.put("content_id", contentID.toString());
		Log.w("Report Send Param", "" + message + " " + userID.toString() + " " + contentID.toString());
		return ws.callServer("reportit", paramPairs);
	}

	@Override
	protected void parseJSON(String json) throws ParserExc {
		myParser.parseJSON(json);
		Log.w("Report Send", "Report Gonderildi.");
	}

	@Override
	protected void postExecute() {
		if (loadingAlert != null)
			loadingAlert.dismiss();
		if (loadResponder != null)
			loadResponder.loadResponse(ParserEvent.SEND_REPORT, null);
	}

}
