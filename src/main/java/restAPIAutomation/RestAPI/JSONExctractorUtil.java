package restAPIAutomation.RestAPI;

import org.json.*;

import io.restassured.response.ResponseBody;;

 
public class JSONExctractorUtil {
	
	//private static final JSONArray String = null;
	private static JSONObject jsonObj;
	static String value = "";
	
	@SuppressWarnings("rawtypes")
	public static String GetValueFromResponseWRTGTRID(ResponseBody responseBody, String serverreferenceCode_gtrid,String key) throws JSONException {
		JSONArray transactionsArr =  jsonObj.getJSONObject("transactionInfo").getJSONArray("transactions");
		String s1 = null;
		for (int i = 0; i < transactionsArr.length(); i++) {
			responseBody = (ResponseBody) transactionsArr.getJSONObject(i);
			String serverReferenceCode = ((JSONObject) responseBody).getString("serverReferenceCode");
			if(serverreferenceCode_gtrid.equalsIgnoreCase(serverReferenceCode)) {
				s1 = ((JSONObject) responseBody).getString(key);
				break;
			}
			else s1 ="Server Reference code not found";
		}
		return s1;
	}
	
	public static String GetValueOf(String jSONResponseString, String key) {
		//jsonObj.getJSONObject("transactionInfo").getJSONObject("transactions").getJSONArray("transactions");
		try {
		jsonObj = new JSONObject(jSONResponseString);
		switch(key)
		{
		//StatusInfo
		case "totalAmountCharged":	
		case "statusCode":
		case "serverReferenceCode":		
		case "endUserId":
			return value=jsonObj.getJSONObject("statusInfo").getString(key);
		//StatusInfo>errorInfo
		case "errorCode":
		case "errorDescription":
			return value=jsonObj.getJSONObject("statusInfo").getJSONObject("errorInfo").getString(key);
		//creditInfo
		case "creditType":			
		case "transactionType":
		case "available":
		case "added":
		case "used":
		case "balance":
			return value=jsonObj.getJSONObject("creditInfo").getString(key);
			
		//profileInfo
		case "msisdn":
		case "accountNumber":
		case "subscriberPayType":
		case "profileStatus ":
		case "firstName":
		case "lastName":
		case "language":
		case "networkActivationDate":
		case "profileCreatedDate":
		case "employeeFlag":
		case "dndFlag":
		case "blacklistFlag":
		case "region":
		case "cgwRoute":
		case "msgRoute":
		case "profileUpdatedDate":
			return value=jsonObj.getJSONObject("profileInfo").getString(key);
		//default
		default:
			return "No match found";
		}	
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
