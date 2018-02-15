package jp.topse.agile.misosoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class InputCash
 */
@WebServlet("/inputcash")
public class InputCash extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		JsonObject jObj = parseRequestJson(request);
		String type = jObj.get("Type").getAsString();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(type.equals("inputCash")){
			String inputValue = jObj.get("Amount").getAsString();
			// change inputValue to int
			// int change = inputValue - backetTotal 
			int changeAmount = 300;
			Map<String, Integer> responseMap = new LinkedHashMap<>();
			responseMap.put("Change", changeAmount);
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
