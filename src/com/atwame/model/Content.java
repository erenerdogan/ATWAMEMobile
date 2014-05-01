package com.atwame.model;

import java.util.List;
import com.atwame.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table CONTENT.
 */
public class Content {

    private Long id;
    private String title;
    private String description;
    private Long user_id;
    private Long category_id;
    private Long attachment_id;
    private Long location_id;
    private java.util.Date created_at;
    private java.util.Date upload_at;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ContentDao myDao;

    private Location location;
    private Long location__resolvedKey;

    private Attachment attachment;
    private Long attachment__resolvedKey;

    private Category category;
    private Long category__resolvedKey;

    private User user;
    private Long user__resolvedKey;

    private List<Comment> comments;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Content() {
    }

    public Content(Long id) {
        this.id = id;
    }

    public Content(Long id, String title, String description, Long user_id, Long category_id, Long attachment_id, Long location_id, java.util.Date created_at, java.util.Date upload_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.category_id = category_id;
        this.attachment_id = attachment_id;
        this.location_id = location_id;
        this.created_at = created_at;
        this.upload_at = upload_at;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContentDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }

    public Long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Long location_id) {
        this.location_id = location_id;
    }

    public java.util.Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(java.util.Date created_at) {
        this.created_at = created_at;
    }

    public java.util.Date getUpload_at() {
        return upload_at;
    }

    public void setUpload_at(java.util.Date upload_at) {
        this.upload_at = upload_at;
    }

    /** To-one relationship, resolved on first access. */
    public Location getLocation() {
        if (location__resolvedKey == null || !location__resolvedKey.equals(location_id)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            location = targetDao.load(location_id);
            location__resolvedKey = location_id;
        }
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        location_id = location == null ? null : location.getId();
        location__resolvedKey = location_id;
    }

    /** To-one relationship, resolved on first access. */
    public Attachment getAttachment() {
        if (attachment__resolvedKey == null || !attachment__resolvedKey.equals(attachment_id)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AttachmentDao targetDao = daoSession.getAttachmentDao();
            attachment = targetDao.load(attachment_id);
            attachment__resolvedKey = attachment_id;
        }
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
        attachment_id = attachment == null ? null : attachment.getId();
        attachment__resolvedKey = attachment_id;
    }

    /** To-one relationship, resolved on first access. */
    public Category getCategory() {
        if (category__resolvedKey == null || !category__resolvedKey.equals(category_id)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            category = targetDao.load(category_id);
            category__resolvedKey = category_id;
        }
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        category_id = category == null ? null : category.getId();
        category__resolvedKey = category_id;
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

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public synchronized List<Comment> getComments() {
        if (comments == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CommentDao targetDao = daoSession.getCommentDao();
            comments = targetDao._queryContent_Comments(id);
        }
        return comments;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetComments() {
        comments = null;
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