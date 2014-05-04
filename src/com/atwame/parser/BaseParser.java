package com.atwame.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.atwame.service.BaseService;
import com.atwame.utils.ServerCom.ErrorHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;

public class BaseParser {
	public DialogInterface loadingAlert = null;
	protected ErrorHandler errorHandler;
	protected Context context;
	protected String errorTitle = "Error!";
	protected String errorMessage = "The server rejected the data - please contact Intellitrac";
	public static AlertDialog alertDialog = null;

	public static final boolean RESPONSE_RESULT_SUCCESS = true;
	public static final boolean RESPONSE_RESULT_FAIL = false;

	public BaseParser(Context context, ErrorHandler errorHandler) {

	}

	public BaseParser(Context context) {
		this(context, null);
	}

	public void setloadingAlert(DialogInterface alert) {
		loadingAlert = alert;
	}

	final public void parseJSON(String json) throws BaseService.ParserExc {
		Log.w("JSON", json);
		try {
			JSONObject obj = new JSONObject(json);
			boolean code = obj.getBoolean("result");
			String alertMessage = null, alertTitle = errorTitle;
			if (code == RESPONSE_RESULT_SUCCESS) {

			} else if (code == RESPONSE_RESULT_FAIL) {
				alertMessage = "Please contact Eren ERDOGAN";
			}

			if (alertMessage != null) {
				System.out.println("Error: " + alertMessage);
				spawnGenericErrorPrompt(context, alertTitle, alertMessage);
				throw new BaseService.ParserExc();
			}
			
			parseResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseService.ParserExc();

		}
		
	}
	
	public void parseResponse(String json) throws BaseService.ParserExc {
		System.out.println("Error: Abtract method parseResponse called");
	}

	public static AlertDialog spawnGenericErrorPrompt(final Context context,
			final String title, final String alertMessage) {
		new Handler(context.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				if (alertDialog != null)
					return;
				AlertDialog.Builder b = new AlertDialog.Builder(context);
				//b.setIcon(R.drawable.ic_dialog_alert_holo_light);
				b.setTitle(title).setMessage(alertMessage);
				b.setPositiveButton("OK", new alertResponder());
				alertDialog = b.create();
				alertDialog.show();
			}
		});
		return null;
	}

	private static class alertResponder implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface alertView, int which) {
			alertDialog = null;
			alertView.dismiss();

		}
	}

	public void cleanupCall() {
		if (loadingAlert != null) {
			loadingAlert.dismiss();
			loadingAlert = null;
		}
	}
}
