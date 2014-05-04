package com.atwame.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.atwame.parser.BaseParser;
import com.atwame.utils.CustomApplication;
import com.atwame.utils.ServerCom.iAsyncTerminatorCallback;
import com.atwame.utils.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class BaseService {
	protected HashMap<String, String> paramsPair;
	protected ProgressDialog loadingAlert = null;
	protected BaseParser parser = null;
	protected boolean status;
	
	protected BaseParser myParser = null;
	protected int finishMode;
	protected String errorMsg;
	protected iAsyncTerminatorCallback loadResponder;
    protected Context context;
	private static final int MAX_LOAD_RETRIES = 4;
    private static final int FINISH_MODE_FAIL = -1;
	
	public static class ParserExc extends Exception{}
	
	
	public BaseService(Context context, iAsyncTerminatorCallback loadResponder) {
		super();
		this.context = context;
		this.loadResponder = loadResponder;
	}

	final public BaseService exec() {
		new ServerCommAsync().execute();
        return this;
	}

	public void setProgressDialog(ProgressDialog dialog){
        loadingAlert = dialog;
        myParser.setloadingAlert(loadingAlert);
    }
    public void makeStandardProgressBox(){
        makeStandardProgressBox("Loading...");
    }
    public void makeStandardProgressBox(String message){
        makeStandardProgressBox("ATWAME", message);
    }
    public void makeStandardProgressBox(String title, String message){
        loadingAlert = new ProgressDialog(context);
        loadingAlert.setTitle(title);
        loadingAlert.setMessage(message);
        //loadingAlert.setButton(Dialog.BUTTON_POSITIVE, "Cancel", new cancelProgressListener());
        //boolean progBar = loadingAlert.requestWindowFeature(Window.FEATURE_PROGRESS);
        //loadingAlert.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loadingAlert.setIndeterminate(true);
        //loadingAlert.setProgress(10);
        //if(progBar)
        loadingAlert.show();
        if(myParser != null) myParser.setloadingAlert(loadingAlert);
    }
	
	protected String sendRequestGetJSON(WebService ws, HashMap<String, String> paramPairs) throws IOException{
        System.out.println("Error: abstract method <sendRequest()> called");
        return "Error: abstract method called";
    }
	protected void parseJSON(String json) throws ParserExc{}
    protected void postExecute(){}

    final public class ServerCommAsync extends AsyncTask<Void, Void, Void>{
        public ServerCommAsync(){}
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)  {
            int retryCount = 0;
            while(true){
                try {
                    WebService ws = WebService.getInstance(context);
                    HashMap<String, String> paramPairs = new HashMap<String, String>();
                    String posLati = ((CustomApplication)context.getApplicationContext()).getPosLatAsString();
                    String posLong = ((CustomApplication)context.getApplicationContext()).getPosLongAsString();
                    paramPairs.put("lat", posLati);
                    paramPairs.put("lng", posLong);

                    String json = sendRequestGetJSON(ws, paramPairs);
                    parseJSON(json);
                }catch (java.io.IOException e){
                    e.printStackTrace();
                    if(++retryCount < MAX_LOAD_RETRIES) continue;
                    errorMsg = "Couldn't connect to the server - it seems to be unresponsive.";
                    finishMode = FINISH_MODE_FAIL;
                }catch(ParserExc e){
                    finishMode = FINISH_MODE_FAIL;
                    break;
                }catch(Exception e) {
                    e.printStackTrace();
                    finishMode = FINISH_MODE_FAIL;
                }
                break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            if (finishMode != FINISH_MODE_FAIL) postExecute();
        }
    }
    
    private class cancelProgressListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface diag, int which){
            diag.dismiss();
        }
    }
}
