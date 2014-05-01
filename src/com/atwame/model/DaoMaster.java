package com.atwame.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.IdentityScopeType;

import com.atwame.model.ContentDao;
import com.atwame.model.UserDao;
import com.atwame.model.CommentDao;
import com.atwame.model.CategoryDao;
import com.atwame.model.AttachmentDao;
import com.atwame.model.LocationDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        ContentDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
        CommentDao.createTable(db, ifNotExists);
        CategoryDao.createTable(db, ifNotExists);
        AttachmentDao.createTable(db, ifNotExists);
        LocationDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        ContentDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
        CommentDao.dropTable(db, ifExists);
        CategoryDao.dropTable(db, ifExists);
        AttachmentDao.dropTable(db, ifExists);
        LocationDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(ContentDao.class);
        registerDaoClass(UserDao.class);
        registerDaoClass(CommentDao.class);
        registerDaoClass(CategoryDao.class);
        registerDaoClass(AttachmentDao.class);
        registerDaoClass(LocationDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
