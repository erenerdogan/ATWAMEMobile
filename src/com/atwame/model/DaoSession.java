package com.atwame.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import com.atwame.model.Content;
import com.atwame.model.User;
import com.atwame.model.Comment;
import com.atwame.model.Category;
import com.atwame.model.Attachment;
import com.atwame.model.Location;

import com.atwame.model.ContentDao;
import com.atwame.model.UserDao;
import com.atwame.model.CommentDao;
import com.atwame.model.CategoryDao;
import com.atwame.model.AttachmentDao;
import com.atwame.model.LocationDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig contentDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig commentDaoConfig;
    private final DaoConfig categoryDaoConfig;
    private final DaoConfig attachmentDaoConfig;
    private final DaoConfig locationDaoConfig;

    private final ContentDao contentDao;
    private final UserDao userDao;
    private final CommentDao commentDao;
    private final CategoryDao categoryDao;
    private final AttachmentDao attachmentDao;
    private final LocationDao locationDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        contentDaoConfig = daoConfigMap.get(ContentDao.class).clone();
        contentDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        commentDaoConfig = daoConfigMap.get(CommentDao.class).clone();
        commentDaoConfig.initIdentityScope(type);

        categoryDaoConfig = daoConfigMap.get(CategoryDao.class).clone();
        categoryDaoConfig.initIdentityScope(type);

        attachmentDaoConfig = daoConfigMap.get(AttachmentDao.class).clone();
        attachmentDaoConfig.initIdentityScope(type);

        locationDaoConfig = daoConfigMap.get(LocationDao.class).clone();
        locationDaoConfig.initIdentityScope(type);

        contentDao = new ContentDao(contentDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        commentDao = new CommentDao(commentDaoConfig, this);
        categoryDao = new CategoryDao(categoryDaoConfig, this);
        attachmentDao = new AttachmentDao(attachmentDaoConfig, this);
        locationDao = new LocationDao(locationDaoConfig, this);

        registerDao(Content.class, contentDao);
        registerDao(User.class, userDao);
        registerDao(Comment.class, commentDao);
        registerDao(Category.class, categoryDao);
        registerDao(Attachment.class, attachmentDao);
        registerDao(Location.class, locationDao);
    }
    
    public void clear() {
        contentDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        commentDaoConfig.getIdentityScope().clear();
        categoryDaoConfig.getIdentityScope().clear();
        attachmentDaoConfig.getIdentityScope().clear();
        locationDaoConfig.getIdentityScope().clear();
    }

    public ContentDao getContentDao() {
        return contentDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public AttachmentDao getAttachmentDao() {
        return attachmentDao;
    }

    public LocationDao getLocationDao() {
        return locationDao;
    }

}
