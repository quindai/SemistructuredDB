package topiscosdb.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

//https://stackoverflow.com/questions/12732422/adding-header-for-httpurlconnection
//https://www.crwflags.com/fotw/flags/cou_reg.html#eafr
/**
 * 
 * @author quindai
 * @see https://www.baeldung.com/java-org-json
 */
public class RestDB {

	private static URL url;
	private static HttpURLConnection conn;
	public RestDB() {
		getAllCountries();
	}
	
	public void getAllCountries() {
			try {
				BufferedReader br;
				url = new URL("https://topicosbd-af88.restdb.io/rest/sama");
				conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("x-apikey", "6d5012888f773bba7404e7f541feacd7ee6da");
				conn.setRequestProperty("cache-control", "no-cache");
				conn.setUseCaches(false);
				System.out.println(conn.getResponseCode());
				
				if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
				    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
				    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				String l;
				while((l=br.readLine()) != null) {
					System.out.println(l);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void getCountries(String region) {
		try {
			BufferedReader br;
			System.out.println("Resgatando dados...");
			url = new URL("https://topicosbd-af88.restdb.io/rest/"+ region);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("x-apikey", "6d5012888f773bba7404e7f541feacd7ee6da");
			conn.setRequestProperty("cache-control", "no-cache");
			conn.setUseCaches(false);
			System.out.println("Estado da consulta:: "+ conn.getResponseCode());
			
			if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
			    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
			    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			String l = br.lines().collect(Collectors.joining(System.lineSeparator()));
			JSONArray j;
			
			//System.out.println(br.lines().collect(Collectors.joining(System.lineSeparator())).trim());
			j = new JSONArray(l);
			
			System.out.println(j);
			/*
			while((l=br.readLine()) != null) {
				System.out.println(l);
			}
			*/
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new RestDB();
	}
}
