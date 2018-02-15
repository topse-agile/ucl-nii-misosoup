package jp.topse.misosoup;

import java.util.ArrayList;

public class Bascket {
	private DataStore dataStore;
    private ArrayList<String> items = new ArrayList<String>();
    
    public Bascket (DataStore _dataStore)
    {
    		dataStore = _dataStore;
    }
    
    public void addItem(String item)
    {
    	    items.add(item);
    }
    
    public int getTotalPrice()
    {
    	    int total = 0;
    		for (String item : items) {
    			total += dataStore.getPrice(item);
    		}
    		return total;
    }
    
    public void clear()
    {
    	    items.clear();
    }
}