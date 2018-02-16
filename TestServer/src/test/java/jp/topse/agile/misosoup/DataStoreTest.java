package jp.topse.agile.misosoup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DataStoreTest {
    @Test
    public void testGetApplePriceReturns100()
    {
        DataStore dataStore = new DataStore();

        assertThat(dataStore.getPrice("Apple"), is(100));
    }

    @Test
    public void testGetOrangePriceReturns200()
    {
        DataStore dataStore = new DataStore();

        assertThat(dataStore.getPrice("Orange"), is(200));
    }

    @Test
    public void registerBananaWithPrice300()
    {
        DataStore dataStore = new DataStore();
        
        dataStore.updateItem("Banana", 300);

        assertThat(dataStore.getPrice("Banana"), is(300));
    }

    @Test
    public void updateBananaWithPrice500()
    {
        DataStore dataStore = new DataStore();
        
        dataStore.updateItem("Banana", 500);

        assertThat(dataStore.getPrice("Banana"), is(500));
    }

    @Test
    public void testGetItemAndPriceListOfInitialState()
    {
        DataStore dataStore = new DataStore();
        
        Map<String, Integer> itemAndPriceList = new HashMap<String, Integer>();
        itemAndPriceList.put("Apple", 100);
        itemAndPriceList.put("Orange", 200);
        
        assertThat(dataStore.getItemAndPriceList(), is(itemAndPriceList));
    }

    @Test
    public void testGetItemAndPriceListWhenBananaIsAdded()
    {
        DataStore dataStore = new DataStore();
        dataStore.updateItem("Banana", 500);
        
        Map<String, Integer> itemAndPriceList = new HashMap<String, Integer>();
        itemAndPriceList.put("Apple", 100);
        itemAndPriceList.put("Orange", 200);
        itemAndPriceList.put("Banana", 500);
        
        assertThat(dataStore.getItemAndPriceList(), is(itemAndPriceList));
    }
}
