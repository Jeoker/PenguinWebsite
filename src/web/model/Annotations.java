package web.model;

public class Annotations {
    protected int annotationId;
    protected String name;
    protected String fileType;
    protected Sites site;
    protected String pathOnCloud;
    protected String Criteria;

    public Annotations(int annotationId) {
        this.annotationId = annotationId;
    }

    public Annotations(String name, String fileType, Sites site, String pathOnCloud,
                       String criteria) {
        this.name = name;
        this.fileType = fileType;
        this.site = site;
        this.pathOnCloud = pathOnCloud;
        Criteria = criteria;
    }

    public Annotations(int annotationId, String name, String fileType, Sites site,
                       String pathOnCloud, String criteria) {
        this.annotationId = annotationId;
        this.name = name;
        this.fileType = fileType;
        this.site = site;
        this.pathOnCloud = pathOnCloud;
        Criteria = criteria;
    }

    public int getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(int annotationId) {
        this.annotationId = annotationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }

    public String getPathOnCloud() {
        return pathOnCloud;
    }

    public void setPathOnCloud(String pathOnCloud) {
        this.pathOnCloud = pathOnCloud;
    }

    public String getCriteria() {
        return Criteria;
    }

    public void setCriteria(String criteria) {
        Criteria = criteria;
    }
}
