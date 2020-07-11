package web.model;

import java.sql.Timestamp;

public class Models {
    protected int modelId;
    protected String name;
    protected Timestamp createTime;

    public Models(int modelId, String name, Timestamp createTime) {
        this.modelId = modelId;
        this.name = name;
        this.createTime = createTime;
    }

    public Models(String name, Timestamp createTime) {
        this.name = name;
        this.createTime = createTime;
    }

    public Models(int modelId) {
        this.modelId = modelId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
