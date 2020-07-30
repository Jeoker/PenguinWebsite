package web.tools;

import web.dal.*;
import web.model.*;

import java.awt.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.*;

/**
 * Unit tests for Images, Cameras
 */
public class unittest0 {

    private static void imagePrinter(Images image) {
        System.out.println("  |__ "+image.getImageId()
              +", "+image.getFileName()+", "+image.getFileType()+", "
              +image.getMediaLink()+", "+image.getTimestamp()+", "
              +image.getWidth()+", "+image.getHeight()+", "+image.getLatitude()
              +", "+image.getCamera().getCameraId());
    }

    private static void cameraPrinter(Cameras camera) {
        System.out.println("  |__ "+camera.getCameraId()+", "+camera.getName());
    }

    private static void uavPrinter(UAVs uav) {
        System.out.println("  |__ "+uav.getUavId()+", "+uav.getModel()+", "
              +uav.getCamera().getCameraId()+", "+uav.getWeight());
    }


    public static void main(String[] args) throws SQLException{

       // DAO instances.
        ImagesDao imagesDao = ImagesDao.getInstance();
        CamerasDao camerasDao = CamerasDao.getInstance();
        SitesDao sitesDao = SitesDao.getInstance();
        UAVsDao uavsDao = UAVsDao.getInstance();


        System.out.println("-------------------------------");
        System.out.println("Test Images");
        System.out.println("-------------------------------");
        Images image0 = imagesDao.create(new Images("testImage","JPG",8,new Sites(1),
              "Somewhere.html",new Timestamp(System.currentTimeMillis()), 800,600,
              56.2323224,-61.4404206,new Cameras(1)));
        System.out.println("Create test Image Successfully");
        Images image1 = imagesDao.getImageById(image0.getImageId());
        System.out.println("Get Image By Id:");
        imagePrinter(image1);

        System.out.println("Update Image Path:");
        imagePrinter(imagesDao.updatePath(image0, "anotherplace.html"));

        System.out.println("Get Image By Closest Time 2015-12-23 10:10:10:");
        imagePrinter(imagesDao.getImageByClosestTime(
                                Timestamp.valueOf("2015-12-23 10:10:10")));

        System.out.println("-----------check the weather for Image 20:");
        System.out.println(imagesDao.getWeatherForImage(340));

        List<Images> imageList = imagesDao.getImageBySite(4);
        System.out.println("Get Images by Site:");
        for (Images item:imageList)
            imagePrinter(item);
        imagesDao.delete(image0);
        System.out.println("Delete Test Image Successfully");


        System.out.println("-------------------------------");
        System.out.println("Test Cameras");
        System.out.println("-------------------------------");
        Cameras camera0 = camerasDao.create(new Cameras("Sony a7r3iii"));
        System.out.println("Create test Camera Successfully");
        System.out.println("Get Camera by Id: ");
        cameraPrinter(camerasDao.getCameraById(camera0.getCameraId()));
        camerasDao.delete(camera0);
        System.out.println("Delete test Camera Successfully");


        System.out.println("-------------------------------");
        System.out.println("Test UAVs");
        System.out.println("-------------------------------");
        UAVs uav0 = uavsDao.create(new UAVs("M300",new Cameras(2),2200));
        System.out.println("Create test UAV Successfully");
        System.out.println("Get UAV by Id: ");
        uavPrinter(uavsDao.getUavById(uav0.getUavId()));
        System.out.println("Get UAV by Camera Id: ");
        List<UAVs> uavList = uavsDao.getUavByCameraId(2);
        System.out.println(uavList.size());
        for (UAVs item:uavList)
            uavPrinter(item);
        uavsDao.delete(uav0);
        System.out.println("Delete test UAV Successfully");
    }
}
