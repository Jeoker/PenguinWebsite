package web.model;
import java.sql.Timestamp;
import java.sql.Date;

public class Weather{
    public Weather(int weatherId) {
		super();
		WeatherId = weatherId;
	}

	public Date getWeather_Time() {
		return Weather_Time;
	}

	
	protected int WeatherId;
    protected Date Weather_Time;
    protected double TmpOut;
    protected double TmpH;
    protected double TmpL;
    protected double WindSpeed;
    protected String WindDir;
    protected double WindRun;
    protected double HiSpeed;
    protected double WindChill;
    protected double Bar;
    protected double HeatDD;
    protected double CoolDD;
    protected double TmpIn;
    protected int HumIn;
    protected int ArcInt;
    protected int Longitude;
    protected int Latitude;

 

    public Weather(Date Weather_Time,double TmpOut,double TmpH,double TmpL,double WindSpeed,
    		String WindDir,double WindRun,double HiSpeed,double WindChill,double Bar,double HeatDD,
    		double CoolDD,double TmpIn,int HumIn,int ArcInt,int Longitude,int Latitude) {
        this.Weather_Time = Weather_Time;
        this.TmpOut = TmpOut;
        this.TmpH= TmpH;
        this.TmpL = TmpL;
        this.WindSpeed = WindSpeed;
        this.WindDir = WindDir;
        this.WindRun = WindRun;
        this.HiSpeed = HiSpeed;
        this.WindChill = WindChill;
        this.Bar = Bar ;
        this.HeatDD= HeatDD;
        this.CoolDD= CoolDD;
        this.TmpIn= TmpIn;
        this.HumIn= HumIn;
        this.ArcInt= ArcInt;
        this.Longitude= Longitude;
        this.Latitude= Latitude;
        
    }

    public Weather(int WeatherId,Date Weather_Time,double TmpOut,double TmpH,double TmpL,double WindSpeed,
    		String WindDir,double WindRun,double HiSpeed,double WindChill,double Bar,double HeatDD,
    		double CoolDD,double TmpIn,int HumIn,int ArcInt,int Longitude,int Latitude) {
    	 this.WeatherId = WeatherId;
         this.Weather_Time = Weather_Time;
         this.TmpOut = TmpOut;
         this.TmpH= TmpH;
         this.TmpL = TmpL;
         this.WindSpeed = WindSpeed;
         this.WindDir = WindDir;
         this.WindRun = WindRun;
         this.HiSpeed = HiSpeed;
         this.WindChill = WindChill;
         this.Bar = Bar ;
         this.HeatDD= HeatDD;
         this.CoolDD= CoolDD;
         this.TmpIn= TmpIn;
         this.HumIn= HumIn;
         this.ArcInt= ArcInt;
         this.Longitude= Longitude;
         this.Latitude= Latitude;
    }


	public void setWeather_Time(Date Weather_Time) {
		this.Weather_Time = Weather_Time;
	}

	public double getTmpOut() {
		return TmpOut;
	}

	public void setTmpOut(double tmpOut) {
		TmpOut = tmpOut;
	}

	public double getTmpH() {
		return TmpH;
	}

	public void setTmpH(double tmpH) {
		TmpH = tmpH;
	}

	public double getTmpL() {
		return TmpL;
	}

	public void setTmpL(double tmpL) {
		TmpL = tmpL;
	}

	public double getWindSpeed() {
		return WindSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}

	public String getWindDir() {
		return WindDir;
	}

	public void setWindDir(String windDir) {
		WindDir = windDir;
	}

	public double getWindRun() {
		return WindRun;
	}

	public void setWindRun(double windRun) {
		WindRun = windRun;
	}

	public double getHiSpeed() {
		return HiSpeed;
	}

	public void setHiSpeed(double hiSpeed) {
		HiSpeed = hiSpeed;
	}

	public double getWindChill() {
		return WindChill;
	}

	public void setWindChill(double windChill) {
		WindChill = windChill;
	}

	public double getBar() {
		return Bar;
	}

	public void setBar(double bar) {
		Bar = bar;
	}

	public double getHeatDD() {
		return HeatDD;
	}

	public void setHeatDD(double heatDD) {
		HeatDD = heatDD;
	}

	public double getCoolDD() {
		return CoolDD;
	}

	public void setCoolDD(double coolDD) {
		CoolDD = coolDD;
	}

	public double getTmpIn() {
		return TmpIn;
	}

	public void setTmpIn(double tmpIn) {
		TmpIn = tmpIn;
	}

	public int getHumIn() {
		return HumIn;
	}

	public void setHumIn(int humIn) {
		HumIn = humIn;
	}

	public int getArcInt() {
		return ArcInt;
	}

	public void setArcInt(int arcInt) {
		ArcInt = arcInt;
	}

	public int getLongitude() {
		return Longitude;
	}

	public void setLongitude(int longitude) {
		Longitude = longitude;
	}

	public int getLatitude() {
		return Latitude;
	}

	public void setLatitude(int latitude) {
		Latitude = latitude;
	}
	
	public int getWeatherId() {
		return WeatherId;
	}
	public void setWeatherId(int weatherId) {
		WeatherId = weatherId;
	}


}

  