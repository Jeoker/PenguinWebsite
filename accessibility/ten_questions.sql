USE PenguinWeb;

# 1. what is the posted information by the user whose username is JinyuNa?
select user.username, post.*
from user
         inner join post
                    on user.userkey = post.userkeyfk
where user.username = 'JinyuNa';


# 2. What is the number of comments for each user?
select user.userkey, user.username, count(comment.commentkey) as comment_count
from user
         left outer join comment
                         on user.userkey = comment.userkeyfk
group by user.userkey, user.username;



# 3.what is the image count of each site and Date?
SELECT Name, Date, COUNT_SITE
FROM Sites INNER JOIN
     (
         SELECT SiteId, COUNT(*) AS COUNT_SITE
         FROM Images
         GROUP BY SiteId
         ORDER BY SiteId ASC
     ) AS siteID_COUNT
     ON Sites.SiteId = SiteId
ORDER BY Name ASC;

# 4.what is the researchers' journey site and datetime?
SELECT  ParticipateId AS journey, Sites.Name AS siteName, Date, FirstName AS reseacher_FirstName, LastName AS reseacher_LastName
FROM
    (Participates inner join Sites
        ON Participates.ParticipateId = site.SiteId)
        INNER JOIN Researcher
                   ON Participates.researcherKeyFK = Researcher.ResearcherKey;

# 5.How many image is taken by camera "FC300X" ?
SELECT COUNT(*) AS COUNT_SITE
FROM Images INNER JOIN Cameras
                      ON Images.camID = Cameras.CameraId
WHERE Cameras.Name = "FC300X";

# 6. What is UAVs of each camera ?
SELECT Name, Model, Weight
FROM Cameras LEFT OUTER JOIN UAVs
                            ON Cameras.CameraId = UAVs.camID
ORDER BY Cameras.CameraId ASC;




# 7. What are the user names and disliked post titles and contents using "dislike"?
SELECT User.UserName,Title,PostContent
FROM Post INNER JOIN LikeOrDislike INNER JOIN User
                                              ON   Post.PostKey = LikeOrDislike.PostKeyFK
                                                  AND  User.UserKey = Post.UserKeyFK;


# 8. What are the top ten posts and its' content and author?
SELECT User.UserName,PostContent,COUNT(*) AS POPULARITY_POST
FROM  Share INNER JOIN Post INNER JOIN User
                                       ON    Share.PostKeyFK = Post.PostKey
                                           AND   Post.UserKeyFK = User.UserKey
GROUP BY PostKeyFK
ORDER BY POPULARITY_POST DESC
LIMIT 10;


# 9.What are the usernames with more posts than comments? What are their posts and comments?
SELECT UserName,
       IF(POST_.UserKeyFK IS NULL,0,NUM_POST) AS TOTAL_POST,
       IF(COMMENT_.UserKeyFK IS NULL,0,NUM_COMMENT) AS TOTAL_COMMENT
FROM User
         LEFT OUTER JOIN(
    SELECT UserKeyFK,COUNT(*) AS NUM_POST
    FROM Post
    GROUP BY UserKeyFK) AS POST_
                        ON User.UserKey = Post_.UserKeyFK
         LEFT OUTER JOIN(
    SELECT UserKeyFK, COUNT(*) AS NUM_COMMENT
    FROM Comment
    GROUP BY UserKeyFK) AS COMMENT_
                        ON User.UserKey = COMMENT_.UserKeyFK
HAVING TOTAL_POST > TOTAL_COMMENT;


# 10.What are the top 10 most popular posts in a week?
SELECT Title,User.UserName,COUNT(*) AS POPULARITY
FROM   Post INNER JOIN LikeOrDislike INNER JOIN User
                                                ON     Post.PostKey = LikeOrDislike.PostKeyFK
                                                    AND    User.UserKey = Post.UserKeyFK
WHERE  date_sub(curdate(),INTERVAL 7 DAY) <= DATE(LikeOrDislikeCreated)
GROUP  BY PostKeyFK
ORDER  BY POPULARITY
LIMIT  10;

/* 11. link each image to the weather record, where the weather station is closest and Time matches best */
select WeatherId, Time as weather_time, Weathers.Longitude as weather_long,
       Weathers.Latitude as weather_latt, ImageId, TimeStamp as image_time,
       the_image.Longitude as image_long, the_image.Latitude as image_latt,
       abs(round(6367000*2*asin(
           sqrt(pow(sin(((Weathers.Latitude * pi())/180
                          - (the_image.Latitude*pi())/180)/2),2)
                    + cos((the_image.Latitude*pi())/180)
                          *cos((Weathers.Latitude*pi())/180)
                          *pow(sin(((Weathers.Longitude*pi())/180
                                        -(the_image.Longitude * pi())/180)/2),2)
               )))) as distance
from Weathers, (select * from Images where ImageId = 5410) as the_image
where the_image.Latitude is not null and
      the_image.Longitude is not null and
      the_image.TimeStamp is not null
order by distance, abs(the_image.TimeStamp-Weathers.Time)
limit 1;
