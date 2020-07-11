package web.model;

public class Participates {
    protected int participateId;
    protected Sites site;
    protected Researchers researcher;

    public Participates(int participateId, Sites site, Researchers researcher) {
        this.participateId = participateId;
        this.site = site;
        this.researcher = researcher;
    }

    public Participates(int participateId) {
        this.participateId = participateId;
    }

    public  Participates(Sites site, Researchers researcher) {
        this.site = site;
        this.researcher = researcher;
    }

	public int getParticipateId() {
		return participateId;
	}

	public void setParticipateId(int participateId) {
		this.participateId = participateId;
	}

	public Sites getSite() {
		return site;
	}

	public void setSite(Sites site) {
		this.site = site;
	}

	public Researchers getResearcher() {
		return researcher;
	}

	public void setResearcher(Researchers researcher) {
		this.researcher = researcher;
	}
    
    
    
}
