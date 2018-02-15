package jp.topse.agile.misosoup;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AddItemTest {

	private DataStore dataStore;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private AddItem servlet;
	
	@Before
	public void setUp() {
		dataStore = new DataStore();
		request = Mockito.mock(HttpServletRequest.class);
		response = Mockito.mock(HttpServletResponse.class);
		servlet = new AddItem();
		try {
			servlet.init(servlet.getServletConfig());
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	private StringWriter post(String inputJsonString) throws IOException, ServletException {
		Reader reader = new StringReader(inputJsonString);
		BufferedReader buffReader = new BufferedReader(reader);
		Mockito.when(request.getReader()).thenReturn(buffReader);
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		Mockito.when(response.getWriter()).thenReturn(writer);
		servlet.doPost(request, response);
		return stringWriter;
	}
			
	
	@Test
	public void testAddSingleItem() {
		try {
			StringWriter stringWriter = post("{Type:addItem, Item:Orange}");
			assertTrue(stringWriter.toString().contains("{\"totalValue\":" +  
					dataStore.getPrice("Orange") +"}"));
		} catch (IOException | ServletException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testAddMultipleItems() {
		try {
			// Add orange and check total cost
			int totalCost = dataStore.getPrice("Orange");
			StringWriter stringWriter = post("{Type:addItem, Item:Orange}");
			assertTrue(stringWriter.toString().contains("{\"totalValue\":" +  
					totalCost +"}"));
			
			// Add apple and check total cost
			request = Mockito.mock(HttpServletRequest.class);
			response = Mockito.mock(HttpServletResponse.class);
			stringWriter = post("{Type:addItem, Item:Apple}");
			totalCost += dataStore.getPrice("Apple");
			assertTrue(stringWriter.toString().contains("{\"totalValue\":" +  
					totalCost +"}"));
		} catch (IOException | ServletException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testMakePayment() {
		try {
			// Add orange
			int totalCost = dataStore.getPrice("Orange");
			post("{Type:addItem, Item:Orange}");
			
			// Make payment of 300
			int payment = 300;
			StringWriter stringWriter = 
					post("{Type:inputCash, Amount:" + payment + "}");
			
			int change = payment - totalCost;
			
			assertTrue(stringWriter.toString().contains("{\"Change\":" + change +"}"));
		} catch (IOException | ServletException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testNegativeChange() {
		try {
			// Add orange
			int totalCost = dataStore.getPrice("Orange");
			post("{Type:addItem, Item:Orange}");
			
			// Make payment of 50
			int payment = 50;
			StringWriter stringWriter = 
					post("{Type:inputCash, Amount:" + payment + "}");
			
			int change = payment - totalCost;
			
			assertTrue(stringWriter.toString().contains("{\"Change\":" + change +"}"));
		} catch (IOException | ServletException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testResetValues() {
		try {
			StringWriter stringWriter = post("{Type:resetBascket}");
			assertTrue(stringWriter.toString().contains("{\"totalValue\":0"));
		} catch (IOException | ServletException e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetStockValues() {
		
	}

}
