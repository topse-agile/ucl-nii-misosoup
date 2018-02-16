package jp.topse.agile.misosoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servlet implementation class AddItem
 */
@WebServlet("/additem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Bascket bascket;
	private DataStore dataStore;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		dataStore = new DataStore();
		bascket = new Bascket(dataStore);
	}
	
	
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		JsonObject jObj = parseRequestJson(request);
		String type = jObj.get("Type").getAsString();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(type.equals("addItem")){
			String item = jObj.get("Item").getAsString();
			bascket.addItem(item);
			Map<String, Integer> responseMap = new LinkedHashMap<>();
			responseMap.put("totalValue", bascket.getTotalPrice());
			String json = new Gson().toJson(responseMap);
			response.getWriter().write(json);
		} else if(type.equals("inputCash")){
			String inputValue = jObj.get("Amount").getAsString();
			int inputVal = Integer.parseInt(inputValue);
			int changeAmount = inputVal - bascket.getTotalPrice();
			Map<String, Integer> responseMap = new LinkedHashMap<>();
			responseMap.put("Change", changeAmount);
			String json = new Gson().toJson(responseMap);
			response.getWriter().write(json);
			bascket.clear();
		} else if(type.equals("resetBascket")) {
			bascket.clear();
			Map<String, Integer> responseMap = new LinkedHashMap<>();
			responseMap.put("totalValue", bascket.getTotalPrice());
			String json = new Gson().toJson(responseMap);
			response.getWriter().write(json);
		} else if(type.equals("getItems")) {
			Map<String, Integer> responseMap = dataStore.getItems();
			String json = new Gson().toJson(responseMap);
			response.getWriter().write(json);
		} else if(type.equals("say")) {
			say("Welcome customer");
			Map<String, Integer> responseMap = new LinkedHashMap<>();
			String json = new Gson().toJson(responseMap);
			response.getWriter().write(json);
		}
	}

	private void say(String message) {
		HttpURLConnection con = null;
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL("http://192.168.128.102:8091/google-home-notifier");
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			//con.setRequestProperty("Accept-Language", "jp");
			//con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
			//con.setRequestProperty("Content-Length", String.valueOf(JSON.length()));
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			out.write("text=" + message);
			out.flush();
			con.connect();
			final int status = con.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				System.out.println("Message sent to Google Home");
			} else {
				System.out.println("Message sent with failure");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
	
	private JsonObject parseRequestJson(HttpServletRequest request) throws IOException{
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		JsonParser parser = new JsonParser();
		return parser.parse(sb.toString()).getAsJsonObject();
	}

}
