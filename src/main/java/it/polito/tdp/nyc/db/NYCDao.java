package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.model.Adiacenze;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllProvider(){
		String sql = "SELECT DISTINCT Provider "
				+ "FROM nyc_wifi_hotspot_locations ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Provider"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getVertici(String p){
		String sql = "SELECT DISTINCT nyc1.city "
				+ "FROM nyc_wifi_hotspot_locations nyc1 "
				+ "WHERE  nyc1.Provider = ? "
				+ "GROUP BY nyc1.city ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("nyc1.city"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<Adiacenze> getAdiacenze(String p){
		String sql = "SELECT DISTINCT nyc1.city as c1, nyc2.city as c2, AVG(nyc1.Latitude) as lat1, "
				+ "AVG(nyc2.Latitude) as lat2, "
				+ "AVG(nyc1.Longitude) as long1, AVG(nyc2.Longitude) as long2 "
				+ "FROM nyc_wifi_hotspot_locations nyc1,  nyc_wifi_hotspot_locations nyc2 "
				+ "WHERE  nyc1.Provider = ? "
				+ "AND nyc2.Provider = nyc1.Provider "
				+ "AND nyc1.city > nyc2.city "
				+ "GROUP BY c1, c2 ";
		List<Adiacenze> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				double lat1 = res.getDouble("lat1");
				double lat2 = res.getDouble("lat2");
				double long1 = res.getDouble("long1");
				double long2 = res.getDouble("long2");
				
				LatLng lt1 = new LatLng(lat1,long1);
				LatLng lt2 = new LatLng(lat2,long2);
				
				double distance = LatLngTool.distance(lt1, lt2, LengthUnit.KILOMETER) ;
				
				result.add(new Adiacenze(res.getString("c1"),res.getString("c2"), distance));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
}
