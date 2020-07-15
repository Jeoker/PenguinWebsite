package web.dal;

import web.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

	public class WeatherDao {
    protected ConnectionManager connectionManager;
    private static WeatherDao instance = null;

    protected WeatherDao() {
        connectionManager = new ConnectionManager();
    }

    public static WeatherDao getInstance() {
        if(instance == null) {
            instance = new WeatherDao();
        }
        return instance;
    }

    /**
     * Researchers can create a weather record
     * @param weather
     * @return weather
     * @throws SQLException
     */
    public Weather create(Weather weather)
            throws SQLException {
        String sql = "INSERT INTO Weathers(Time,TmpOut,TmpH,TmpL,WindSpeed, "+
        		"WindDir,WindRun,HiSpeed,WindChill,Bar,HeatDD,CoolDD,TmpIn,HumIn,ArcInt," +
        		"Longitude,Latitude)"+
                " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
                 ps.setTimestamp(1, new Timestamp (weather.getWeather_Time().getTime()));
            	 ps.setDouble(2, weather.getTmpOut());
            	 ps.setDouble(3, weather.getTmpH());
            	 ps.setDouble(4, weather.getTmpL());
            	 ps.setDouble(5, weather.getWindSpeed());
            	 ps.setString(6, weather.getWindDir());
            	 ps.setDouble(7, weather.getWindRun());
            	 ps.setDouble(8, weather.getHiSpeed());
            	 ps.setDouble(9, weather.getWindChill());
            	 ps.setDouble(10, weather.getBar());
            	 ps.setDouble(11, weather.getHeatDD());
            	 ps.setDouble(12, weather.getCoolDD());
            	 ps.setDouble(13,weather.getTmpIn());
            	 ps.setInt(14, weather.getHumIn());
            	 ps.setInt(15, weather.getArcInt());
            	 ps.setInt(16, weather.getLongitude());
            	 ps.setInt(17, weather.getLatitude());

            	 ps.executeUpdate();

                 // Retrieve the auto-generated key and set it, so it can be used by the caller.
                 rs = ps.getGeneratedKeys();
                 int WeatherId = -1;
                 if(rs.next()) {
                	 WeatherId = rs.getInt(1);
                 } else {
                     throw new SQLException("Unable to retrieve auto-generated key.");
                 }
                 weather.setWeatherId(WeatherId);


                 return weather;
             } catch (SQLException e) {
                 e.printStackTrace();
                 throw e;
             } finally {
                 if(connection != null) {
                     connection.close();
                 }
                 if(ps != null) {
                     ps.close();
                 }
                 if(rs != null) {
                     rs.close();
                 }
        }
    }
    /**
     * Researchers can get weather record by Time
     * @return weather
     * @throws SQLException
     */
    public Weather getWeatherByWeatherId(int WeatherId) throws SQLException {
        String sql = "SELECT * FROM Weathers WHERE WeatherId=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, WeatherId );
            rs = ps.executeQuery();
            WeatherDao weatherDao  = WeatherDao.getInstance();
            if(rs.next()) {
            	int resultWeatherId = rs.getInt("WeatherId");
            	Date Time =  new Date(rs.getTimestamp("Time").getTime());
            	double TmpOut = rs.getDouble("TmpOut");
            	double TmpH = rs.getDouble("TmpH");
            	double TmpL= rs.getDouble("TmpL");
            	double WindSpeed =  rs.getDouble("WindSpeed");
            	String WindDir = (rs).getString("WindDir");
            	double WindRun = rs.getDouble("WindRun");
            	double HiSpeed = rs.getDouble("HiSpeed");
            	double WindChill = rs.getDouble("WindChill");
            	double Bar = rs.getInt("Bar");
            	double HeatDD = rs.getDouble("HeatDD");
            	double CoolDD = rs.getDouble("CoolDD");
            	double TmpIn = rs.getDouble("TmpIn");
            	int HumIn = rs.getInt("HumIn");
            	int ArcInt = rs.getInt("ArcInt");
            	int Longitude= rs.getInt("Longitude");
            	int Latitude= rs.getInt("Latitude");
         
            	Weather weather_new = new Weather(resultWeatherId,Time,TmpOut,TmpH,TmpL,WindSpeed,WindDir,WindRun,HiSpeed,WindChill,Bar,HeatDD,CoolDD,TmpIn,HumIn,ArcInt,Longitude,Latitude);
                return weather_new;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }
   
    
    public Weather getWeatherByTime(Date time)
            throws SQLException{
        String sql = "SELECT*" +
                " FROM Weathers WHERE Time=?;";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setDate(1, time);
            rs = ps.executeQuery();
            if (rs.next()) {
            	int resultWeatherId = rs.getInt("WeatherId");
            	Date Time =  new Date(rs.getTimestamp("Time").getTime());
            	double TmpOut = rs.getDouble("TmpOut");
            	double TmpH = rs.getDouble("TmpH ");
            	double TmpL= rs.getDouble("TmpL");
            	double WindSpeed =  rs.getDouble("WindSpeed");
            	String WindDir = rs.getString("WindDir");
            	double WindRun = rs.getDouble("WindRun");
            	double HiSpeed = rs.getDouble("HiSpeed");
            	double WindChill = rs.getDouble("WindChill");
            	double Bar = rs.getInt("Bar");
            	double HeatDD = rs.getDouble("HeatDD");
            	double CoolDD = rs.getDouble("CoolDD");
            	double TmpIn = rs.getDouble("TmpIn");
            	int HumIn = rs.getInt("HumIn");
            	int ArcInt = rs.getInt("ArcInt");
            	int Longitude= rs.getInt("Longitude");
            	int Latitude= rs.getInt("Latitude");
      
            	Weather weather = new Weather(resultWeatherId,Time,TmpOut,TmpH,TmpL,WindSpeed,WindDir,WindRun,HiSpeed,WindChill,Bar,HeatDD,CoolDD,TmpIn,HumIn,ArcInt,Longitude,Latitude);
                return weather;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return null;
    }
    
    
    /**
     * Researchers can update their weather's content
     * @param weather
     * @return weather
     * @throws SQLException
     */
    
    public Weather updateTmpOut(Weather weather, double newAbout) throws SQLException{
        String sql = "UPDATE Weathers SET TmpOut = ? WHERE WeatherId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setDouble(1, newAbout);
            ps.setInt(2, weather.getWeatherId());
            ps.executeUpdate();
            weather.setTmpOut(newAbout);
           
            return weather;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
                }
            }
        }
            
    /**
     * Researchers can delete their weather's content
     * @return post
     * @throws SQLException
     */
    public Weather delete(Weather weather) throws SQLException{
        String sql = "DELETE FROM Weathers WHERE WeatherId = ?;";
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, weather.getWeatherId());
            ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(ps != null) {
                ps.close();
            }
        }
    }
}

