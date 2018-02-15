package jp.topse.misosoup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class BascketTest {

	@Test	
	public void testGetTotalPriceReturnsZeroWhenThereIsNoItems ()
	{
		DataStore dataStore = new DataStore();
		Bascket bascket = new Bascket(dataStore);
		
		assertThat(bascket.getTotalPrice(), is(0));
	}

	@Test
	public void testAddItemAcceptsAppleItemNameAndGetsThePriceFromDataStore() {
		DataStore dataStore = new DataStore();
		Bascket bascket = new Bascket(dataStore);
		
		bascket.addItem("Apple");
		
		assertThat(bascket.getTotalPrice(), is(100));
	}

	@Test
	public void testAddItemAcceptsAppleAndOrangeItemsAndGetsThePriceFromDataStore() {
		DataStore dataStore = new DataStore();
		Bascket bascket = new Bascket(dataStore);

		bascket.addItem("Apple");
		bascket.addItem("Orange");
		
		assertThat(bascket.getTotalPrice(), is(300));
	}

	@Test
	public void testAddItemAcceptsMultipleSameItemNamesAndGetsThePriceFromDataStore() {
		DataStore dataStore = new DataStore();
		Bascket bascket = new Bascket(dataStore);

		bascket.addItem("Apple");
		bascket.addItem("Apple");
		
		assertThat(bascket.getTotalPrice(), is(200));
	}

	@Test
	public void testClearClearsBascketAndWillReturnsTotalPriceZero ()
	{
		DataStore dataStore = new DataStore();
		Bascket bascket = new Bascket(dataStore);

		bascket.addItem("Apple");
		bascket.addItem("Orange");
		
		bascket.clear();
		
		assertThat(bascket.getTotalPrice(), is(0));
	}
}
