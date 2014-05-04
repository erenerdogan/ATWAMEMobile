package com.atwame.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class WebService {

	
	private static String server;
	public static void initInstance(Context context, String serv){
		
		if (((CustomApplication)context.getApplicationContext()).getWebService() == null){
			if (server == null){
				server = serv;
			}
			((CustomApplication)context.getApplicationContext()).setWebService(new WebService());
		}
	}
	
	public static WebService getInstance(Context context){
		return ((CustomApplication)context.getApplicationContext()).getWebService();
	}
	
	private HttpPost createPostRequest(String method, HashMap<String, String> paramPairs){
		// Creating HTTP Post
        HttpPost httpPost = new HttpPost(server + "/" + method);
        // Building post parameters
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(paramPairs.size());
        for (String key : paramPairs.keySet()){
        	nameValuePair.add(new BasicNameValuePair(key, paramPairs.get(key)));
        }
        
        // Url Encoding the POST parameters
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // writing error to Log
            e.printStackTrace();
        }
        return httpPost;
	}
	
	public String callServer(String method, HashMap<String, String> paramPairs) throws ClientProtocolException, IOException{
		// Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParameters = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
        HttpConnectionParams.setSoTimeout(httpParameters, 3 * 1000);
        HttpResponse httpResponse = httpClient.execute(createPostRequest(method, paramPairs));
	    HttpEntity httpEntity = httpResponse.getEntity();
	    String json = EntityUtils.toString(httpEntity);
        return json;
	}
}
