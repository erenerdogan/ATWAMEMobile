package com.atwame.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

import com.atwame.model.Attachment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ATTACHMENT.
*/
public class AttachmentDao extends AbstractDao<Attachment, Long> {

    public static final String TABLENAME = "ATTACHMENT";

    /**
     * Properties of entity Attachment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Url = new Property(1, String.class, "url", false, "URL");
        public final static Property Created_at = new Property(2, java.util.Date.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(3, java.util.Date.class, "updated_at", false, "UPDATED_AT");
    };

    private DaoSession daoSession;


    public AttachmentDao(DaoConfig config) {
        super(config);
    }
    
    public AttachmentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ATTACHMENT' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: id
                "'URL' TEXT," + // 1: url
                "'CREATED_AT' INTEGER," + // 2: created_at
                "'UPDATED_AT' INTEGER);"); // 3: updated_at
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ATTACHMENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Attachment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(2, url);
        }
 
        java.util.Date created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindLong(3, created_at.getTime());
        }
 
        java.util.Date updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindLong(4, updated_at.getTime());
        }
    }

    @Override
    protected void attachEntity(Attachment entity) {
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
    public Attachment readEntity(Cursor cursor, int offset) {
        Attachment entity = new Attachment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // url
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // created_at
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)) // updated_at
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Attachment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUrl(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreated_at(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setUpdated_at(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Attachment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Attachment entity) {
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
