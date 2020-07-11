package web.model;

public class Detections {
    protected int detectionId;
    protected Images image;
    protected int count;
    protected String pathOnCloud;
    protected Models model;

    public Detections(int detectionId) {
        this.detectionId = detectionId;
    }

    public Detections(Images image, int count, String pathOnCloud, Models model) {
        this.image = image;
        this.count = count;
        this.pathOnCloud = pathOnCloud;
        this.model = model;
    }

    public Detections(int detectionId, Images image, int count,
                      String pathOnCloud, Models model) {
        this.detectionId = detectionId;
        this.image = image;
        this.count = count;
        this.pathOnCloud = pathOnCloud;
        this.model = model;
    }

    public int getDetectionId() {
        return detectionId;
    }

    public void setDetectionId(int detectionId) {
        this.detectionId = detectionId;
    }

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPathOnCloud() {
        return pathOnCloud;
    }

    public void setPathOnCloud(String pathOnCloud) {
        this.pathOnCloud = pathOnCloud;
    }

    public Models getModel() {
        return model;
    }

    public void setModel(Models model) {
        this.model = model;
    }
}
