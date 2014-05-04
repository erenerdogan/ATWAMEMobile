package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.atwame.model.Attachment;
import com.atwame.model.Content;
import com.atwame.model.Location;
import com.atwame.model.User;
import com.atwame.utils.ServerCom;

public class ReportParser extends BaseParser {

	public Content content;
	
	public ReportParser(Context context, ServerCom.ErrorHandler errorHandler) {
		super(context, errorHandler);
	}

	public ReportParser(Context context) {
		super(context, null);
	}

	public void parseResponse(String json) {
		
	}
}
