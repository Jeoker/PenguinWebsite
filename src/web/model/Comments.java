package web.model;

import java.util.Date;

public class Comments {
    protected int commentId;
    protected String content;
    protected Date created;
    protected Posts post;
    protected Users user;
    protected Comments fatherComment;

    public Comments(int commentId, String content, Date created, Posts post, Users user, Comments fatherComment) {
        this.commentId = commentId;
        this.content = content;
        this.created = created;
        this.post = post;
        this.user = user;
        this.fatherComment = fatherComment;
    }

    public Comments(int commentId) {
        this.commentId = commentId;
    }

    public Comments(String content, Date created, Posts post, Users user, Comments fatherComment) {
        this.content = content;
        this.created = created;
        this.post = post;
        this.user = user;
        this.fatherComment = fatherComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Comments getFatherComment() {
        return fatherComment;
    }

    public void setFatherComment(Comments fatherComment) {
        this.fatherComment = fatherComment;
    }
}
