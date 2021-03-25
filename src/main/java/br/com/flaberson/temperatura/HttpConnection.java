package br.com.flaberson.temperatura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class HttpConnection {
	
	public HttpConnection() {}
	
	public JSONObject requestServico(String Url) throws IOException { 
		URL url = new URL(Url);
		Logger logger = Logger.getLogger(HttpConnection.class.getName());
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		if (conn.getResponseCode() != 200) {
			logger.log(Level.WARNING, "HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	
		String output,json="";
		while ((output = br.readLine()) != null) {
			json+= output;
		}
		
		JSONObject jsonObject = new JSONObject(json);
		conn.disconnect();
		
		return jsonObject;
	}
}
