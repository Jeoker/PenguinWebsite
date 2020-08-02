package web.tools;

import web.dal.*;
import web.model.*;

import javax.naming.ldap.LdapContext;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * main() runner, used for the app demo.
 */
public class Inserter {
    public static void main(String[] args) throws SQLException{
        // DAO instances.
        UsersDao usersDao = UsersDao.getInstance();
        ResearchersDao researchersDao = ResearchersDao.getInstance();
        PostsDao postsDao = PostsDao.getInstance();
        CommentsDao commentsDao = CommentsDao.getInstance();
        ResharesDao resharesDao = ResharesDao.getInstance();
        CollectionsDao collectionsDao = CollectionsDao.getInstance();
        LikesDao likesDao = LikesDao.getInstance();

        // INSERT objects from our model.
        // test user
        System.out.println("-------------------------------");
        System.out.println("Test Users");
        System.out.println("-------------------------------");

        // create users
        Users user1 = new Users("mike", "123", Users.Status.valueOf("User"));
        user1 = usersDao.create(user1);
        Users user2 = new Users("kobe", "123", Users.Status.valueOf("User"));
        usersDao.create(user2);
        Users user3 = new Users("john", "123", Users.Status.valueOf("Administrator"));
        usersDao.create(user3);
        Users user4 = new Users("bob", "123", Users.Status.valueOf("Administrator"));
        usersDao.create(user4);
        System.out.println("Create users successful");

        // updatePassword
        user1 = usersDao.updatePassword(user1, "123456");
        System.out.printf("updatePassword:\n userId:%d userName:%s password:%s status:%s\n", user1.getUserId(), user1.getUserName(),
                user1.getPassword(), user1.getStatus());

        // updateUserName
        user1 = usersDao.updateUserName(user1, "mike1");
        System.out.printf("updateUserName:\n userId:%d userName:%s password:%s status:%s\n", user1.getUserId(), user1.getUserName(),
                user1.getPassword(), user1.getStatus());

        // getUserFromUserId
        Users user = usersDao.getUserFromUserId(3);
        System.out.printf("getUserFromUserId:\n userId:%d userName:%s password:%s status:%s\n", user.getUserId(), user.getUserName(),
                user.getPassword(), user.getStatus());

        // getAllUser
        System.out.println("getAllUser: ");
        List<Users> usersList = usersDao.getAllUser();
        for(Users users1: usersList){
            System.out.printf("userId:%d userName:%s password:%s status:%s\n", users1.getUserId(), users1.getUserName(),
                    users1.getPassword(), users1.getStatus());
        }

        // delete user
        if(usersDao.delete(user4) == null){
            System.out.println("Delete user successful");
        }else {
            System.out.println("Delete user failed");
        }

        // test researchers
        System.out.println("-------------------------------");
        System.out.println("Test Researchers");
        System.out.println("-------------------------------");
        Researchers researcher1 = new Researchers("jordan","123",Users.Status.valueOf("Researcher"),
                "smith","jordan",1,"paper1","institute1");
        researchersDao.create(researcher1);
        System.out.println("Create researchers successful");


        // test posts
        System.out.println("-------------------------------");
        System.out.println("Test Posts");
        System.out.println("-------------------------------");

        // create posts
        Date date = new Date();
        Posts post1 = new Posts("title1",null,"content1",true,date,user1);
        postsDao.create(post1);
        Posts post2 = new Posts("title2",null,"content2",true,date,user1);
        postsDao.create(post2);
        Posts post3 = new Posts("title3",null,"content3",true,date,user2);
        postsDao.create(post3);
        Posts post4 = new Posts("title4",null,"content4",true,date,user3);
        postsDao.create(post4);
        System.out.println("Create posts successful");

        // updateContent
        post1 = postsDao.updateContent(post1, "new content1");
        System.out.printf("updateContent:\n postId:%d title:%s picture:%s content:%s published:%s created:%s userId:%d\n",
                post1.getPostId(), post1.getTitle(),post1.getPicture(),post1.getContent(),post1.isPublished(),
                post1.getCreated(),post1.getUser().getUserId());

        // updatePicture
        post1 = postsDao.updatePicture(post1, "image/1.jpg");
        System.out.printf("updatePicture:\n postId:%d title:%s picture:%s content:%s published:%s created:%s userId:%d\n",
                post1.getPostId(), post1.getTitle(),post1.getPicture(),post1.getContent(),post1.isPublished(),
                post1.getCreated(),post1.getUser().getUserId());

        // getPostByPostId
        Posts post = postsDao.getPostByPostId(3);
        System.out.printf("getPostByPostId:\n postId:%d title:%s picture:%s content:%s published:%s created:%s userId:%d\n",
                post.getPostId(), post.getTitle(),post.getPicture(),post.getContent(),post.isPublished(),
                post.getCreated(),post.getUser().getUserId());

        // getPostsByUserId
        System.out.println("getPostsByUserId: ");
        List<Posts> postsList = postsDao.getPostsByUserId(user1);
        for(Posts posts:postsList){
            System.out.printf("postId:%d title:%s picture:%s content:%s published:%s created:%s userId:%d\n",
                    posts.getPostId(), posts.getTitle(),posts.getPicture(),posts.getContent(),posts.isPublished(),
                    posts.getCreated(),posts.getUser().getUserId());
        }

        // getAllPost
        System.out.println("getAllPost: ");
        List<Posts> postsList1 = postsDao.getAllPost();
        for(Posts posts:postsList1){
            System.out.printf("postId:%d title:%s picture:%s content:%s published:%s created:%s userId:%d\n",
                    posts.getPostId(), posts.getTitle(),posts.getPicture(),posts.getContent(),posts.isPublished(),
                    posts.getCreated(),posts.getUser().getUserId());
        }

        // delete post
        if(postsDao.delete(post4) == null){
            System.out.println("Delete post successful");
        }else {
            System.out.println("Delete post failed");
        }

        // test comments
        System.out.println("-------------------------------");
        System.out.println("Test Comments");
        System.out.println("-------------------------------");

        Comments comment1 = new Comments("comment1", date, post1, user1,null);
        commentsDao.create(comment1);
        Comments comment2 = new Comments("comment2", date, post1, user2,null);
        commentsDao.create(comment2);
        Comments comment3 = new Comments("comment3", date, post1, user3,comment2);
        commentsDao.create(comment3);
        Comments comment4 = new Comments("comment4", date, post2, user2,comment1);
        commentsDao.create(comment4);
        System.out.println("Create comments successful");

        // updateContent
        comment1 = commentsDao.updateContent(comment1, "new comment1");
        System.out.printf("updateContent:\n commentId:%d cotent:%s created:%s postId:%d userId:%d\n",
                comment1.getCommentId(),comment1.getContent(),comment1.getCreated(),
                comment1.getPost().getPostId(), comment1.getUser().getUserId());

        // getCommentById
        comment3 = commentsDao.getCommentById(3);
        System.out.printf("getCommentById:\n commentId:%d cotent:%s created:%s postId:%d userId:%d\n",
                comment3.getCommentId(),comment3.getContent(),comment3.getCreated(),
                comment3.getPost().getPostId(), comment3.getUser().getUserId());

        // getCommentsByUserId
        System.out.println("getCommentsByUserId: ");
        List<Comments> commentsList = commentsDao.getCommentsByUserId(user2);
        for (Comments comments:commentsList){
            System.out.printf("commentId:%d cotent:%s created:%s postId:%d userId:%d\n",
                    comments.getCommentId(),comments.getContent(),comments.getCreated(),
                    comments.getPost().getPostId(), comments.getUser().getUserId());
        }

        // getCommentsByPostId
        System.out.println("getCommentsByPostId: ");
        List<Comments> commentsList1 = commentsDao.getCommentsByPostId(post1);
        for (Comments comments:commentsList1){
            System.out.printf("commentId:%d cotent:%s created:%s postId:%d userId:%d\n",
                    comments.getCommentId(),comments.getContent(),comments.getCreated(),
                    comments.getPost().getPostId(), comments.getUser().getUserId());
        }

        // getAllComment
        System.out.println("getAllComment: ");
        List<Comments> commentsList2 = commentsDao.getAllComment();
        for (Comments comments:commentsList2){
            System.out.printf("commentId:%d cotent:%s created:%s postId:%d userId:%d\n",
                    comments.getCommentId(),comments.getContent(),comments.getCreated(),
                    comments.getPost().getPostId(), comments.getUser().getUserId());
        }

        // delete comment
        if(commentsDao.delete(comment4) == null){
            System.out.println("Delete comment successful");
        }else {
            System.out.println("Delete comment failed");
        }

        // test reshares
        System.out.println("-------------------------------");
        System.out.println("Test Reshares");
        System.out.println("-------------------------------");

        // Create reshares
        Reshares reshare1 = new Reshares(user1,post1,null);
        resharesDao.create(reshare1);
        Reshares reshare2 = new Reshares(user1,null,comment1);
        resharesDao.create(reshare2);
        Reshares reshare3 = new Reshares(user2,post1,null);
        resharesDao.create(reshare3);
        Reshares reshare4 = new Reshares(user3,null,comment2);
        resharesDao.create(reshare4);
        System.out.println("Create reshares successful");

        // getReshareById
        reshare2 = resharesDao.getReshareById(2);
        System.out.printf("getReshareById:\n reviewId:%d userId:%d postId:%d commentId:%d\n",
                reshare2.getReshareId(),
                reshare2.getUser().getUserId(),
                reshare2.getPost() == null ? null:reshare2.getPost().getPostId(),
                reshare2.getComment() == null ? null:reshare2.getComment().getCommentId());

        // getResharesByUserId
        System.out.println("getResharesByUserId: ");
        List<Reshares> resharesList = resharesDao.getResharesByUserId(user1);
        for(Reshares reshares:resharesList){
            System.out.printf("reviewId:%d userId:%d postId:%d commentId:%d\n",
                    reshares.getReshareId(),
                    reshares.getUser().getUserId(),
                    reshares.getPost() == null ? null:reshares.getPost().getPostId(),
                    reshares.getComment() == null ? null:reshares.getComment().getCommentId());
        }

        // getAllReshares
        System.out.println("getAllReshares: ");
        List<Reshares> resharesList1 = resharesDao.getAllReshares();
        for(Reshares reshares:resharesList1){
            System.out.printf("reviewId:%d userId:%d postId:%d commentId:%d\n",
                    reshares.getReshareId(),
                    reshares.getUser().getUserId(),
                    reshares.getPost() == null ? null:reshares.getPost().getPostId(),
                    reshares.getComment() == null ? null:reshares.getComment().getCommentId());
        }

        // delete reshare
        if(resharesDao.delete(reshare4) == null){
            System.out.println("Delete reshare successful");
        }else {
            System.out.println("Delete reshare failed");
        }

        // test collections
        System.out.println("-------------------------------");
        System.out.println("Test Collections");
        System.out.println("-------------------------------");

        // Create collections
        Collections collection1 = new Collections(user1,post1,null);
        collectionsDao.create(collection1);
        Collections collection2 = new Collections(user1,null,comment1);
        collectionsDao.create(collection2);
        Collections collection3 = new Collections(user2,post1,null);
        collectionsDao.create(collection3);
        Collections collection4 = new Collections(user3,null,comment2);
        collectionsDao.create(collection4);
        System.out.println("Create collections successful");

        // getCollectionsById
        collection2 = collectionsDao.getCollectionsById(2);
        System.out.printf("getCollectionsById:\n collectionId:%d userId:%d postId:%d commentId:%d\n",
                collection2.getCollectionsId(),
                collection2.getUser().getUserId(),
                collection2.getPost() == null ? null:collection2.getPost().getPostId(),
                collection2.getComment() == null ? null:collection2.getComment().getCommentId());

        // getCollectionsByUserId
        System.out.println("getCollectionsByUserId: ");
        List<Collections> collectionsList = collectionsDao.getCollectionsByUserId(user1);
        for(Collections collections:collectionsList){
            System.out.printf("collectionId:%d userId:%d postId:%d commentId:%d\n",
                    collections.getCollectionsId(),
                    collections.getUser().getUserId(),
                    collections.getPost() == null ? null:collections.getPost().getPostId(),
                    collections.getComment() == null ? null:collections.getComment().getCommentId());
        }

        // getAllCollections
        System.out.println("getAllCollections: ");
        List<Collections> collectionsList1 = collectionsDao.getAllCollections();
        for(Collections collections:collectionsList1){
            System.out.printf("collectionId:%d userId:%d postId:%d commentId:%d\n",
                    collections.getCollectionsId(),
                    collections.getUser().getUserId(),
                    collections.getPost() == null ? null:collections.getPost().getPostId(),
                    collections.getComment() == null ? null:collections.getComment().getCommentId());
        }

        // delete collection
        if(collectionsDao.delete(collection4) == null){
            System.out.println("Delete collection successful");
        }else {
            System.out.println("Delete collection failed");
        }

        // test likes
        System.out.println("-------------------------------");
        System.out.println("Test Likes");
        System.out.println("-------------------------------");

        // Create collections
        Likes like1 = new Likes(user1,post1,null);
        likesDao.create(like1);
        Likes like2 = new Likes(user1,null,comment1);
        likesDao.create(like2);
        Likes like3 = new Likes(user2,post1,null);
        likesDao.create(like3);
        Likes like4 = new Likes(user3,null,comment2);
        likesDao.create(like4);
        System.out.println("Create likes successful");

        // getLikeById
        like2 = likesDao.getLikeById(2);
        System.out.printf("getLikeById:\n likeId:%d userId:%d postId:%d commentId:%d\n",
                like2.getLikeId(),
                like2.getUser().getUserId(),
                like2.getPost() == null ? null:like2.getPost().getPostId(),
                like2.getComment() == null ? null:like2.getComment().getCommentId());

        // getLikesByUserId
        System.out.println("getLikesByUserId: ");
        List<Likes> likesList = likesDao.getLikesByUserId(user1);
        for(Likes likes:likesList){
            System.out.printf("likeId:%d userId:%d postId:%d commentId:%d\n",
                    likes.getLikeId(),
                    likes.getUser().getUserId(),
                    likes.getPost() == null ? null:likes.getPost().getPostId(),
                    likes.getComment() == null ? null:likes.getComment().getCommentId());
        }

        // getAllLikes
        System.out.println("getAllLikes: ");
        List<Likes> likesList1 = likesDao.getAllLikes();
        for(Likes likes:likesList1){
            System.out.printf("likeId:%d userId:%d postId:%d commentId:%d\n",
                    likes.getLikeId(),
                    likes.getUser().getUserId(),
                    likes.getPost() == null ? null:likes.getPost().getPostId(),
                    likes.getComment() == null ? null:likes.getComment().getCommentId());
        }

        // delete collection
        if(likesDao.delete(like4) == null){
            System.out.println("Delete like successful");
        }else {
            System.out.println("Delete like failed");
        }
    }
}
