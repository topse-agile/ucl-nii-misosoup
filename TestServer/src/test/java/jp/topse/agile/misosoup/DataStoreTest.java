package jp.topse.agile.misosoup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class DataStoreTest {
    @Test
    public void testGetApplePriceReturns100()
    {
        DataStore dataStore = new DataStore();

        assertThat(dataStore.getPrice("Apple"), is(100));
    }

    @Test
    public void testGetOrangePriceReturns100()
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
}
