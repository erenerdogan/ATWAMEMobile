package com.atwame.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.atwame.model.Location;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table LOCATION.
*/
public class LocationDao extends AbstractDao<Location, Long> {

    public static final String TABLENAME = "LOCATION";

    /**
     * Properties of entity Location.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Latitude = new Property(1, String.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(2, String.class, "longitude", false, "LONGITUDE");
        public final static Property Created_at = new Property(3, java.util.Date.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(4, java.util.Date.class, "updated_at", false, "UPDATED_AT");
    };

    private DaoSession daoSession;


    public LocationDao(DaoConfig config) {
        super(config);
    }
    
    public LocationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'LOCATION' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: id
                "'LATITUDE' TEXT," + // 1: latitude
                "'LONGITUDE' TEXT," + // 2: longitude
                "'CREATED_AT' INTEGER," + // 3: created_at
                "'UPDATED_AT' INTEGER);"); // 4: updated_at
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'LOCATION'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Location entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(2, latitude);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(3, longitude);
        }
 
        java.util.Date created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindLong(4, created_at.getTime());
        }
 
        java.util.Date updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindLong(5, updated_at.getTime());
        }
    }

    @Override
    protected void attachEntity(Location entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Location readEntity(Cursor cursor, int offset) {
        Location entity = new Location( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // latitude
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // longitude
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // created_at
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // updated_at
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Location entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLatitude(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLongitude(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreated_at(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setUpdated_at(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Location entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Location entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
