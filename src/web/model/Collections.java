package web.model;

public class Collections {
    protected int CollectionsId;
    protected Users user;
    protected Posts post;
    protected Comments comment;

    public Collections(int collectionsId, Users user, Posts post, Comments comment) {
        CollectionsId = collectionsId;
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public Collections(int collectionsId) {
        CollectionsId = collectionsId;
    }

    public Collections(Users user, Posts post, Comments comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public int getCollectionsId() {
        return CollectionsId;
    }

    public void setCollectionsId(int collectionsId) {
        CollectionsId = collectionsId;
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
