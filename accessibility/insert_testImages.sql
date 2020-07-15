use PenguinWeb;
delete from Images where ImageId<0;
insert into Images(ImageId, FileName, FileType, SiteId, Size, MediaLink,
                   TimeStamp, Width, Height, Longitude, Latitude, CameraId)
value(-1,"testImage1","jpg","-1",1,"images/testPenguin1.jpg",
     "2020-07-12 12:00:00",500,500,0,0,2),
    (-2,"testImage2","jpg","-1",1,"images/testPenguin2.jpg",
     "2020-07-12 12:00:00",500,500,0,0,2);
select * from Images where SiteId<0;
insert into Sites(SiteId, Name, Date)
value(-1,"testSite",NOW());