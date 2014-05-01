package com.atwame;

import java.util.HashMap;

import org.json.JSONArray;

import com.atwame.parser.UserParser;
import com.atwame.service.WebService;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("email", "erenerdogan87@gmail.com");
        data.put("password", "147852");
        WebService webService = new WebService("http://atwame.herokuapp.com/login",data);
        String result = webService.callServerPostRequest();
        UserParser u = new UserParser(result);
        Log.w("Result", "" + u.toString());
        
        
    }


}
