package com.atwame.model;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.SqlUtils;
import de.greenrobot.dao.Query;
import de.greenrobot.dao.QueryBuilder;

import com.atwame.model.Comment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table COMMENT.
*/
public class CommentDao extends AbstractDao<Comment, Long> {

    public static final String TABLENAME = "COMMENT";

    /**
     * Properties of entity Comment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property Description = new Property(1, String.class, "description", false, "DESCRIPTION");
        public final static Property User_id = new Property(2, Long.class, "user_id", false, "USER_ID");
        public final static Property Content_id = new Property(3, Long.class, "content_id", false, "CONTENT_ID");
        public final static Property Created_at = new Property(4, java.util.Date.class, "created_at", false, "CREATED_AT");
        public final static Property Updated_at = new Property(5, java.util.Date.class, "updated_at", false, "UPDATED_AT");
    };

    private DaoSession daoSession;

    private Query<Comment> user_CommentsQuery;
    private Query<Comment> content_CommentsQuery;

    public CommentDao(DaoConfig config) {
        super(config);
    }
    
    public CommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'COMMENT' (" + //
                "'ID' INTEGER PRIMARY KEY ," + // 0: id
                "'DESCRIPTION' TEXT," + // 1: description
                "'USER_ID' INTEGER," + // 2: user_id
                "'CONTENT_ID' INTEGER," + // 3: content_id
                "'CREATED_AT' INTEGER," + // 4: created_at
                "'UPDATED_AT' INTEGER);"); // 5: updated_at
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'COMMENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Comment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(2, description);
        }
 
        Long user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindLong(3, user_id);
        }
 
        Long content_id = entity.getContent_id();
        if (content_id != null) {
            stmt.bindLong(4, content_id);
        }
 
        java.util.Date created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindLong(5, created_at.getTime());
        }
 
        java.util.Date updated_at = entity.getUpdated_at();
        if (updated_at != null) {
            stmt.bindLong(6, updated_at.getTime());
        }
    }

    @Override
    protected void attachEntity(Comment entity) {
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
    public Comment readEntity(Cursor cursor, int offset) {
        Comment entity = new Comment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // description
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // user_id
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // content_id
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // created_at
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)) // updated_at
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Comment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDescription(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUser_id(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setContent_id(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setCreated_at(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setUpdated_at(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Comment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Comment entity) {
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
    
    /** Internal query to resolve the "comments" to-many relationship of User. */
    public synchronized List<Comment> _queryUser_Comments(Long user_id) {
        if (user_CommentsQuery == null) {
            QueryBuilder<Comment> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.User_id.eq(user_id));
            user_CommentsQuery = queryBuilder.build();
        } else {
            user_CommentsQuery.setParameter(0, user_id);
        }
        return user_CommentsQuery.list();
    }

    /** Internal query to resolve the "comments" to-many relationship of Content. */
    public synchronized List<Comment> _queryContent_Comments(Long content_id) {
        if (content_CommentsQuery == null) {
            QueryBuilder<Comment> queryBuilder = queryBuilder();
            queryBuilder.where(Properties.Content_id.eq(content_id));
            content_CommentsQuery = queryBuilder.build();
        } else {
            content_CommentsQuery.setParameter(0, content_id);
        }
        return content_CommentsQuery.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getContentDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM COMMENT T");
            builder.append(" LEFT JOIN CONTENT T0 ON T.'CONTENT_ID'=T0.'ID'");
            builder.append(" LEFT JOIN USER T1 ON T.'USER_ID'=T1.'ID'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Comment loadCurrentDeep(Cursor cursor, boolean lock) {
        Comment entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Content content = loadCurrentOther(daoSession.getContentDao(), cursor, offset);
        entity.setContent(content);
        offset += daoSession.getContentDao().getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setUser(user);

        return entity;    
    }

    public Comment loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Comment> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Comment> list = new ArrayList<Comment>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Comment> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Comment> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}