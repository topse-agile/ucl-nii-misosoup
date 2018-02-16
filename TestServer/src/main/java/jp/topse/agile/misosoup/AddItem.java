package jp.topse.agile.misosoup;

import java.io.BufferedReader;
import java.io.IOException;
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
