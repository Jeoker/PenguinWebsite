package web.model;

import java.util.Date;

public class Posts {
    protected int postId;
    protected String title;
    protected String picture;
    protected String content;
    protected boolean published;
    protected Date created;
    protected Users User;

    public Posts(int postId, String title, String picture, String content, boolean published, Date created, Users user) {
        this.postId = postId;
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.published = published;
        this.created = created;
        User = user;
    }

    public Posts(int postId) {
        this.postId = postId;
    }

    public Posts(String title, String picture, String content, boolean published, Date created, Users user) {
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.published = published;
        this.created = created;
        User = user;
    }

    public Posts() {

    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }
}
