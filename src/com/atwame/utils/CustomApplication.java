package com.atwame.utils;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.atwame.model.User;

public class CustomApplication extends Application{
	public final static String BY = "4";
	public final static int CURRENT_VERSION = 9;
	public static int LATEST_VERSION = CURRENT_VERSION;
	private User currentUser;
	private String registrationID;
	private double posLong = 145.072842;
	private double posLat = -37.684998; 
	private LocationManager locationManager;
	private Boolean gpsDisabled = false;
	private WebService WebserviceInstance;
	private File APP_FILE_PATH;
	public static CustomApplication appRef;
	private final String SHARED_PREFS = "atwameprefs";
	@Override
	public void onCreate(){
		super.onCreate();
		updateFilePath();
		initSingletons();
		appRef = this;
	}
	public void updateFilePath(){
		APP_FILE_PATH = getDir(this);
	}
	public static File getDir(Context context) {
		File cacheDir;
        if (isMediaMounted()){
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "/.Atwame");
            cacheDir.mkdir();
            File nomedia = new File(android.os.Environment.getExternalStorageDirectory(), "/.Atwame/.nomedia");
            try {
				nomedia.createNewFile();
			} catch (IOException e) {}
        }else cacheDir = context.getCacheDir();
        return cacheDir;
    }
	public static boolean isOnline(Context context) {
	    ConnectivityManager cm =
	        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}
	public static boolean isMediaMounted(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	private void initSingletons(){
		ModelFactory.initInstance(this.getApplicationContext());
		WebService.initInstance(this, "http://atwame.herokuapp.com");
		locationManager = (LocationManager)  getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener();  
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);
	}
	
	public File getAppFilePath(){
		return APP_FILE_PATH;
	}
	public void setAppFilePath(File path){
		this.APP_FILE_PATH = path;
	}
	
	
	
	public WebService getWebService(){
		return WebserviceInstance;
	}
	
	public void setWebService(WebService WebserviceInstance){
		this.WebserviceInstance = WebserviceInstance;
	}

	public void setCurrentUser(long currentUserID) {
		SharedPreferences ps = getSharedPreferences(SHARED_PREFS, 0);
		SharedPreferences.Editor pe = ps.edit();
		pe.putLong("currentLoginUserID", currentUserID);
		pe.commit();
		this.currentUser = ModelFactory.getInstance().getUserByID(currentUserID);
	}

	
	public double getPosLong(){
		if(!(locationManager != null && locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null)) return posLong;
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
	}
	public double getPosLati(){
		if(!(locationManager != null && locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null)) return posLat;
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
	}
	public String getPosLongAsString(){
		if(gpsDisabled) return "";
		return Double.toString(getPosLong());
	}
	public String getPosLatAsString(){
		if(gpsDisabled) return "";
		return Double.toString(getPosLati());
	}
    public class MyLocationListener implements LocationListener{
            @Override
            public void onLocationChanged(Location loc) {
	           	 loc.getLatitude();
	           	 loc.getLongitude();
            }
            @Override
            public void onProviderDisabled(String provider) {
           		Toast.makeText( getApplicationContext(), "Gps is Disabled", Toast.LENGTH_SHORT ).show();
           		gpsDisabled = true;
            }

            @Override
            public void onProviderEnabled(String provider) {
            	gpsDisabled = false;
           		//Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            	if(status == LocationProvider.OUT_OF_SERVICE) Toast.makeText(CustomApplication.this, "GPS service currently unavailable.", Toast.LENGTH_SHORT).show();
            	else gpsDisabled = false;
            }
      }
}
