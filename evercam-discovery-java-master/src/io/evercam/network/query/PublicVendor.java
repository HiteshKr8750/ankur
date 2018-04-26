package io.evercam.network.query;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.evercam.network.Constants;

public class PublicVendor {
    private JSONObject jsonObject;
    private final static String URL = Constants.URL_API_PUBLIC_MAC_VENDOR;
    private final String KEY_COMPANY = "company";
    private final static int CODE_OK = 200;
    private final static int CODE_NO_CONTENT = 204;

    private PublicVendor(JSONObject jsonObject) {
	this.jsonObject = jsonObject;
    }

    public static PublicVendor getByMac(String macAddress) {
	try {
	    HttpResponse<JsonNode> response = Unirest.get(URL + macAddress)
		    .asJson();
	    if (response.getStatus() == CODE_OK) {
		JSONObject vendorJsonObject = response.getBody().getArray()
			.getJSONObject(0);
		return new PublicVendor(vendorJsonObject);
	    }
	} catch (UnirestException e) {
	    if (Constants.ENABLE_LOGGING) {
		e.printStackTrace();
	    }
	} catch (JSONException e) {
	    if (Constants.ENABLE_LOGGING) {
		e.printStackTrace();
	    }
	}
	return null;
    }

    public String getCompany() {
	if (jsonObject != null) {
	    try {
		return jsonObject.getString(KEY_COMPANY);
	    } catch (JSONException e) {
		if (Constants.ENABLE_LOGGING) {
		    e.printStackTrace();
		}
	    }
	}
	return "";
    }
}
