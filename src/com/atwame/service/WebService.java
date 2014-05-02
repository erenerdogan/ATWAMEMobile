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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class WebService {
	protected String url;
	protected HashMap<String, String> paramsPair;
	protected Context context;
	protected ProgressDialog loadingAlert = null;
	protected BaseParser parser = null;
	protected boolean status;
	private String json;
	
	public WebService(String url, HashMap<String, String> params) {
		super();
		this.url = url;
		this.paramsPair = params;
	}

	public WebService(Context context, String url, HashMap<String, String> paramsPair) {
		super();
		this.url = url;
		this.paramsPair = paramsPair;
		this.context = context;
	}

	final public WebService exec(String method) {
		try {
			json = new FetchJSONModel(method).execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	public void setProgressDialog(ProgressDialog dialog) {
		loadingAlert = dialog;
	}

	public void makeStandardProgressBox() {
		makeStandardProgressBox("Loading...");
	}

	public void makeStandardProgressBox(String message) {
		makeStandardProgressBox("ATWAME", message);
	}

	public void makeStandardProgressBox(String title, String message) {
		loadingAlert = new ProgressDialog(context);
		loadingAlert.setTitle(title);
		loadingAlert.setMessage(message);
		loadingAlert.setIndeterminate(true);
		loadingAlert.show();
	}

	private String createGetRequest() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(this.url);
		HttpResponse response;
		try {
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						instream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return sb.toString();
			}
		} catch (Exception e) {
		}
		return "";
	}

	public String callServerRequest() {
		return json;
	}

	private String createPostRequest(HashMap<String, String> params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				StringBuilder sb = new StringBuilder();
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						instream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return sb.toString();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return "";

	}

	private class FetchJSONModel extends AsyncTask<String, Void, String> {

		private String method;

		public FetchJSONModel(String method) {
			this.method = method;

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String json = null;
			try {
				String data = "";

				if (method.equals("POST"))
					data = createPostRequest(paramsPair);
				else
					data = createGetRequest();
				json = data.trim();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.w("onPostExecute", "Alert kapaldı");
			loadingAlert.dismiss();
		}

	}
}
