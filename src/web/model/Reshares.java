package web.model;

public class Reshares {
    protected int reshareId;
    protected Users user;
    protected Posts post;
    protected Comments comment;

    public Reshares(int reshareId, Users user, Posts post, Comments comment) {
        this.reshareId = reshareId;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public Reshares(int reshareId) {
        this.reshareId = reshareId;
    }

    public Reshares(Users user, Posts post, Comments comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public int getReshareId() {
        return reshareId;
    }

    public void setReshareId(int reshareId) {
        this.reshareId = reshareId;
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
