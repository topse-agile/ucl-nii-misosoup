package jp.topse.misosoup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

public class DataStoreTest {
    @Test
    public void testGetApplePriceReturns100()
    {
        DataStore dataStore = new DataStore();

        assertThat(stockManager.getPrice("Apple"), is(100));
    }

    @Test
    public void testGetOrangePriceReturns100()
    {
        DataStore dataStore = new DataStore();

        assertThat(stockManager.getPrice("Orange"), is(200));
    }
}
