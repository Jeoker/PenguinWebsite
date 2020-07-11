package web.model;

public class UAVs {
    protected int uavId;
    protected String model;
    protected Cameras camera;
    protected float weight;

    public UAVs(int uavId) {
        this.uavId = uavId;
    }

    public UAVs(String model, Cameras camera, float weight) {
        this.model = model;
        this.camera = camera;
        this.weight = weight;
    }

    public UAVs(int uavId, String model, Cameras camera, float weight) {
        this.uavId = uavId;
        this.model = model;
        this.camera = camera;
        this.weight = weight;
    }

    public int getUavId() {
        return uavId;
    }

    public void setUavId(int uavId) {
        this.uavId = uavId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Cameras getCamera() {
        return camera;
    }

    public void setCamera(Cameras camera) {
        this.camera = camera;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
