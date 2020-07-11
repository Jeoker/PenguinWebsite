package web.model;

import java.util.Date;

public class Sites {
    protected int siteId;
    protected String name;
    protected Date date;

    public Sites(int siteId, String name, Date date) {
        this.siteId = siteId;
        this.name = name;
        this.date = date;
    }

    public Sites(int siteId) {
        this.siteId = siteId;
    }

    public Sites(String name, Date date) {
        this.name = name;
        this.date = date;
    }

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
    
    
}
