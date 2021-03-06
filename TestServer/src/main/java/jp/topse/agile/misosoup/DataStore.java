package jp.topse.agile.misosoup;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
   private Map<String, Integer> items = new HashMap<String, Integer>();
   
   public DataStore() {
	   items.put("Apple",100);
	   items.put("Orange",200);
   }
   
   public int getPrice(String item){
	   Integer price = items.get(item);
	   return price;
   }
   
   public void updateItem(String item, Integer price){
	   items.put(item, price);
   }
   
   public Map<String, Integer> getItems() {
	   return items;
   }
}