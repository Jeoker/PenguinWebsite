package web.model;

public class Cameras {
    protected int cameraId;
    protected String name;

    public Cameras(String name) {
        this.name = name;
    }

    public Cameras(int cameraId) {
        this.cameraId = cameraId;
    }

    public Cameras(int cameraId, String name) {
        this.cameraId = cameraId;
        this.name = name;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
