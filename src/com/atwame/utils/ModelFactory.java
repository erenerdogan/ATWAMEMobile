package com.atwame.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.atwame.model.DaoMaster;
import com.atwame.model.DaoMaster.DevOpenHelper;
import com.atwame.model.DaoSession;
import com.atwame.model.User;
import com.atwame.model.UserDao;

public class ModelFactory {

	private static ModelFactory instance;
	private DaoSession daoSession;
	public DaoSession getDaoSession(){
		return daoSession;
	}
	
	public static void initInstance(Context ct){
		if (instance == null) instance = new ModelFactory(ct);
	}
	public static ModelFactory getInstance(Context ct){
		if(instance == null) initInstance(ct);
		return instance;
	}
	public static ModelFactory getInstance(){
		return instance;
	}
	private ModelFactory(Context context){
		try{
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "atwame-db", null);
			SQLiteDatabase db = helper.getWritableDatabase();
			DaoMaster daoMaster = new DaoMaster(db);
	        daoSession = daoMaster.newSession();
		}catch(Exception Error){
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}
	
	public User getUserByID(Long userID) {
		UserDao userDao = daoSession.getUserDao();
		User user = userDao.load(userID);
		return user;
	}
}
