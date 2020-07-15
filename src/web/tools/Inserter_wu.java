package web.tools;

import web.dal.*;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import web.model.Weather;

public class Inserter_wu {
	
	 private static void weatherPrinter(Weather weather) {
	 	if(weather != null){
			System.out.println("  |__ "+weather.getWeatherId()+", "+weather.getWeather_Time()+", "+weather.getTmpOut()+", "+weather.getTmpH()+", "+weather.getTmpL()+", "+weather.getWindSpeed()+", "+weather.getWindDir()+", "+weather.getWindRun()+", "+weather.getHiSpeed()+", "+weather.getWindChill()+", "+weather.getBar()+", "+weather.getHeatDD()+", "+weather.getCoolDD()+", "+weather.getTmpIn()+", "+weather.getHumIn()+", "+weather.getArcInt()+", "+weather.getLongitude()+", "+weather.getLatitude());
		}else {
			System.out.println("null");
		}
	 }
	
	 public static void main(String[] args) throws SQLException{
	
		 // DAO instances.
		    WeatherDao weatherDao = WeatherDao.getInstance();

	        System.out.println("-------------------------------");
	        System.out.println("Test Weather");
	        System.out.println("-------------------------------");

	        Weather weather = new Weather(Date.valueOf("2015-01-01"),-1.8,-1.8,-1.8,10.4,"WSW",0.17,12.2,-7.3,726.4,0.014,0,22.8,15,1,-54,-63);
	        weather = weatherDao.create(weather);
	        //Weather weather0 = weatherDao.create(new Weather(Date.valueOf("2015-01-01"),-1.8,-1.8,-1.8,10.4,"WSW",0.17,12.2,-7.3,726.4,0.014,0,22.8,15,1,-54,-63));
	        System.out.println("Create test Weather Successfully");
	        
	        Weather weather1 = weatherDao.getWeatherByWeatherId(weather .getWeatherId());
	        System.out.println("Get Weather By WeatherId");
	        
	        weatherPrinter(weather1);
	        System.out.println("Get Image By Closest Time 2015-12-23 10:10:10:");
	        weatherPrinter(weatherDao.getWeatherByTime(Date.valueOf("2015-12-23")));

	        System.out.println("Update  Weather TmpOut");
	        weatherPrinter(weatherDao.updateTmpOut(weather, -2.1));

	  
	        weatherDao.delete(weather);
	        System.out.println("Delete Test Weather Successfully");

   }
}
	 
