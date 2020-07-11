package web.model;

import java.sql.Timestamp;

public class Images {
    protected int imageId;
    protected String fileName;
    protected String fileType;
    protected int size;
    protected Sites site;
    protected String mediaLink;
    protected Timestamp timestamp;
    protected int width;
    protected int height;
    protected double longitude;
    protected double latitude;
    protected Cameras camera;

    public Images(int imageId, String fileName, String fileType, int size,
                  Sites site, String mediaLink, Timestamp timestamp, int width,
                  int height, double longitude, double latitude, Cameras camera) {
        this.imageId = imageId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.site = site;
        this.mediaLink = mediaLink;
        this.timestamp = timestamp;
        this.width = width;
        this.height = height;
        this.longitude = longitude;
        this.latitude = latitude;
        this.camera = camera;
    }

    public Images(String fileName, String fileType, int size, Sites site,
                  String mediaLink, Timestamp timestamp, int width, int height,
                  double longitude, double latitude, Cameras camera) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.site = site;
        this.mediaLink = mediaLink;
        this.timestamp = timestamp;
        this.width = width;
        this.height = height;
        this.longitude = longitude;
        this.latitude = latitude;
        this.camera = camera;
    }

    public Images(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Cameras getCamera() {
        return camera;
    }

    public void setCamera(Cameras camera) {
        this.camera = camera;
    }
}
