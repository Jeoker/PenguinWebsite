package web.model;

public class Likes {
    protected int LikeId;
    protected Users user;
    protected Posts post;
    protected Comments comment;

    public Likes(int likeId, Users user, Posts post, Comments comment) {
        LikeId = likeId;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public Likes(int likeId) {
        LikeId = likeId;
    }

    public Likes(Users user, Posts post, Comments comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public int getLikeId() {
        return LikeId;
    }

    public void setLikeId(int likeId) {
        LikeId = likeId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public Comments getComment() {
        return comment;
    }

    public void setComment(Comments comment) {
        this.comment = comment;
    }
}
