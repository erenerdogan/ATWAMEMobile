package com.atwame.model;

import com.atwame.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table COMMENT.
 */
public class Comment {

    private Long id;
    private String description;
    private Long user_id;
    private Long content_id;
    private java.util.Date created_at;
    private java.util.Date updated_at;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CommentDao myDao;

    private Content content;
    private Long content__resolvedKey;

    private User user;
    private Long user__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Comment() {
    }

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(Long id, String description, Long user_id, Long content_id, java.util.Date created_at, java.util.Date updated_at) {
        this.id = id;
        this.description = description;
        this.user_id = user_id;
        this.content_id = content_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCommentDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getContent_id() {
        return content_id;
    }

    public void setContent_id(Long content_id) {
        this.content_id = content_id;
    }

    public java.util.Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(java.util.Date created_at) {
        this.created_at = created_at;
    }

    public java.util.Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(java.util.Date updated_at) {
        this.updated_at = updated_at;
    }

    /** To-one relationship, resolved on first access. */
    public Content getContent() {
        if (content__resolvedKey == null || !content__resolvedKey.equals(content_id)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContentDao targetDao = daoSession.getContentDao();
            content = targetDao.load(content_id);
            content__resolvedKey = content_id;
        }
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
        content_id = content == null ? null : content.getId();
        content__resolvedKey = content_id;
    }

    /** To-one relationship, resolved on first access. */
    public User getUser() {
        if (user__resolvedKey == null || !user__resolvedKey.equals(user_id)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            user = targetDao.load(user_id);
            user__resolvedKey = user_id;
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user_id = user == null ? null : user.getId();
        user__resolvedKey = user_id;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}