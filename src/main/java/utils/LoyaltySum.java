package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class LoyaltySum {
	
	public static Object getSummed(Object data) {	
		HashMap sumMap = new HashMap();
		ArrayList sumList = new ArrayList();
		ArrayList sumList1 = new ArrayList();
		if(data instanceof HashMap ){
			
			HashMap site= (HashMap) data;
			ArrayList redemptions = (ArrayList) site.get("redemptions");
			HashMap redemption = (HashMap) redemptions.get(0);
			ArrayList regions		= (ArrayList) redemption.get("regions");

			int i = 0;
			for (; i < regions.size();) {
				boolean added=false;
				String key ="";
				HashMap region= (HashMap) regions.get(i);
	
				String lineDate =  (String) region.get("transactionDate");
				Integer grade =  (Integer) region.get("fuelType");
				String[] ary= lineDate.split("T");
				lineDate = ary[0];
				Double quantity = (Double)  region.get("volume");
				key = lineDate+"-"+grade;
				for (int j= i+1;  j < regions.size(); j++) {
					String key1 ="";
					HashMap region1= (HashMap) regions.get(j);
					String lineDate1 =  (String) region1.get("transactionDate");
					Integer grade1 =  (Integer) region1.get("fuelType");
					String[] ary1= lineDate1.split("T");
					lineDate1 = ary1[0];	
					key1 = lineDate1+"-"+grade1;
					
					if (key.equals(key1)) {
						boolean foundMatch = true;
						quantity = quantity + (Double)  region1.get("volume");
						if(sumMap.get(key)==null || foundMatch){
							if(sumMap.get(key)!=null){
								sumMap.remove(key);
							}
							Map<String, String> map = new HashMap<String, String>();
							map.put("lineDate", lineDate);
							map.put("quantity", quantity.toString());
							map.put("grade", grade.toString());
							//sumList.add(map);
							sumMap.put(key, map);
							regions.remove(j);
							i=0;	
							added=true;
						}
						if(added && regions.size()==j){
							regions.remove(i);
						}
					}
				}
				if(sumMap.get(key)==null){
					Map<String, String> map = new HashMap<String, String>();
					map.put("lineDate", lineDate);
					map.put("quantity", quantity.toString());
					map.put("grade", grade.toString());
					//sumList.add(map);
					sumMap.put(key, map);
					i=0;
					regions.remove(i);
				}
			}
		}
		if(sumMap!=null){
			for (Object key : sumMap.keySet()) { 
			sumList.add(sumMap.get(key));
		}
			sumList1.addAll(sumList);
		}
		return sumList1;
	}
}
