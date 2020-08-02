USE PenguinWeb;

LOAD DATA INFILE '/home/jeoker/IdeaProjects/PenguinWeb/data/camera.csv'
  INTO TABLE Cameras
  FIELDS TERMINATED BY ',' 
  ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 0 ROWS;

LOAD DATA INFILE '/home/jeoker/IdeaProjects/PenguinWeb/data/site.csv'
  INTO TABLE Sites
  FIELDS TERMINATED BY ',' 
  ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 0 ROWS;

LOAD DATA INFILE '/home/jeoker/IdeaProjects/PenguinWeb/data/image.csv'
  INTO TABLE Images
  FIELDS TERMINATED BY ','
  ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  (ImageId,FileName,FileType,SiteId,MediaLink,Size,@vtimestamp,@vwidth,@vheight,@vlongitude,@vlatitude,CameraId)
  SET
  TimeStamp = NULLIF(@vtimestamp,'0'),
  Width = NULLIF(@vwidth,'0'),
  Height = NULLIF(@vheight,'0'),
  Longitude = NULLIF(@vlongitude,'0'),
  Latitude = NULLIF(@vlatitude,'0');

LOAD DATA INFILE '/home/jeoker/IdeaProjects/PenguinWeb/data/weather.csv'
    INTO TABLE Weathers
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    (WeatherId,`Time`,@vtmpOut,@vtmpH,@vtmpL,@vwindSpeed,WindDir,@vwindRun,@vhiSpeed,WindChill,Bar,HeatDD,CoolDD,TmpIn,HumIn,ArcInt,Longitude,Latitude)
    SET
        TmpOut = NULLIF(@vtmpOut,'NA'),
        TmpH = NULLIF(@vtmpH,'NA'),
        TmpL = NULLIF(@vtmpL,'NA'),
        WindSpeed = NULLIF(@vwindSpeed,'NA'),
        WindRun = NULLIF(@vwindRun,'NA'),
        HiSpeed = NULLIF(@vhiSpeed,'NA');



insert into UAVs(Model,CameraId,Weight)
  values("DJI Mavic Air",1,430),("Phantom 3 Professional",3,1280),("DJI Mavic 2 Pro",4,907);

insert into Models(Name,CreateTime)
  values("withoutChick0","2020:05:28 12:00:00");

insert into Detections(ImageId,Count,PathOnCloud,ModelId)
  values(1524,55,"xxx",1);


#INSERT Users
INSERT INTO Users(UserName,Password,Status)
VALUES('JinyuNa','12345','Administrator');
INSERT INTO Users(UserName,Password,Status)
VALUES('KaiHe','12345','Administrator');
INSERT INTO Users(UserName,Password,Status)
VALUES('YangLiu','12345','Administrator');
INSERT INTO Users(UserName,Password,Status)
VALUES('XinruiWu','12345','Administrator');
INSERT INTO Users(UserName, Password, Status)
VALUES('user1','123','User');
INSERT INTO Users(UserName, Password, Status)
VALUES('user2','123','User');
INSERT INTO Users(UserName, Password, Status)
VALUES('user3','123','User');

#INSERT Researchers
INSERT INTO Researchers(UserId,FirstName,LastName,Gender,institute,AcademicPaper)
VALUES('1','Heather','Lynch','0','Stony Brook University','Low-temperature fate of the 0.7 structure in a point contact: A Kondo-like correlated state in an open system；Neutral metacommunity models predict fish diversity patterns in Mississippi–Missouri basin'),
       ('2','Tom','Hart','1','Oxford University','Mitogenomes Uncover Extinct Penguin Taxa and Reveal Island Formation as a Key Driver of Speciation；Comparative population genomics reveals key barriers to dispersal in Southern Ocean penguins'),
	   ('3','Alexander','Borowicz','1','Stony Brook University','Aerial-trained deep learning networks for surveying cetaceans from satellite imagery；Multi-modal survey of Adélie penguin mega-colonies reveals the Danger Islands as a seabird hotspot. Scientific Reports 3926'),
       ('4','A. Raya','Rey','1','National Scientific and Technical Research Council','Cryptic speciation in gentoo penguins is driven by geographic isolation and regional marine conditions: Unforeseen vulnerabilities to global change'),
       ('5','Casey','Youngflesh','1','University of California,Los Angeles','Multi-modal survey of Adélie penguin mega-colonies reveals the Danger Islands as a seabird hotspot;Circumpolar analysis of the Adélie Penguin reveals the importance of environmental variability in phenological mismatch'),
	   ('6','Jinyu','Na','0','Northeastern University',''),
       ('7','Kai','He','0','Northeastern University',''),
	   ('8','Yang','Liu','0','Northeastern University',''),
       ('9','Xinrui','Wu','1','Northeastern University','');

#INSERT Posts
INSERT INTO Posts(UserId,Title,Content,Published)
VALUES('1','Which penguin is the most beautiful',
       'Almost all the birds that has been here during the antarctic summer, now find their way north. Only on bird does the oppsite. It moves south, into the interior of this cold place. That is the Emperor Penguin, the largest, tallest and most beautiful of all the penguins.',
       '1');
INSERT INTO Posts(UserId,Title,Content,Published)
VALUES('2','26 types of penguin: The only list you will ever need',
       'Warning: High risk of controversy and/or cuteness. The world penguins are a diverse and interesting bunch. Part of that diversity is the range of species - but where the species line is drawn is difficult to determine, especially in animals that are as closely related as penguins. Taxonomists agree that there are at least 16 penguin species, but there are arguments to be made for up to 22! Whether species, subspecies or colour morphs* - here are the 26 "types" of penguins that walk the earth.',
       '1');

#INSERT Comments
INSERT INTO Comments(UserId,PostId,Content)
VALUES('3','2','Amazing, they are so beautiful!');
INSERT INTO Comments(UserId,PostId,Content)
VALUES('5','2','I like them!!!!');
INSERT INTO Comments(UserId,PostId,Content)
VALUES('3','2','Thankyou for sharing.');

#INSERT Reshares
INSERT INTO Reshares(UserId,CommentId)
VALUES('5','1');
INSERT INTO Reshares(UserId,PostId)
VALUES('5','2');

#INSERT Participates
INSERT INTO Participates(SiteId,ResearcherId)
VALUES('1','1'),
('2','1'),
('3','1'),
('4','1'),
('5','1'),
('6','1'),
('7','2'),
('8','2'),
('9','2'),
('10','2'),
('11','2'),
('12','2'),
('13','2'),
('14','2'),
('15','2'),
('16','3'),
('17','3'),
('18','3'),
('19','3'),
('20','3'),
('21','3'),
('22','3'),
('23','4'),
('24','4'),
('25','4'),
('26','4'),
('27','5'),
('28','5'),
('29','5'),
('30','5'),
('31','5');
