package com.urqa.library.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Authentication implements JsonInterface {
    private String mKey;
    private String mAppVersion;
    private String mDeviceId; // �꾩씠��    private String mModel;   // ��紐�    private String mManufacturer; // �쒖“��    private String mCarrierName;  // �듭떊��    private String mCountryCode; // 援�� �뺣낫
    private String mAndroidVersion; // �덈뱶濡쒖씠��踰꾩쟾 
    private String mModel;
    private String mManufacturer;
    private String mCarrierName;
    private String mCountryCode;
    


    
    // TODO 援�� �뺣낫, �듭떊
    @Override
    public JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("apikey", getKey());
            object.put("appversion", getAppVersion());
            object.put("device_id", getDevieId());
            object.put("model", getModel());
            object.put("manufacturer", getManufacturer());
            object.put("carrier_name", getCarrierName());
            object.put("country_code", getCountryCode());
            object.put("android_version", getAndroidVersion());

        } catch (JSONException e) {
        }
        return object;
    }


	/**
	 * @return the key
	 */
	public String getKey() {
		return mKey;
	}


	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		mKey = key;
	}


	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return mAppVersion;
	}


	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		mAppVersion = appVersion;
	}


	/**
	 * @return the id
	 */
	public String getDevieId() {
		return mDeviceId;
	}


	/**
	 * @param id the id to set
	 */
	public void setDeviceId(String deviceId) {
		mDeviceId = deviceId;
	}


	/**
	 * @return the model
	 */
	public String getModel() {
		return mModel;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		mModel = model;
	}


	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return mManufacturer;
	}


	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		mManufacturer = manufacturer;
	}


	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return mCarrierName;
	}


	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		mCarrierName = carrierName;
	}


	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return mCountryCode;
	}


	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		mCountryCode = countryCode;
	}


	/**
	 * @return the androidVersion
	 */
	public String getAndroidVersion() {
		return mAndroidVersion;
	}


	/**
	 * @param androidVersion the androidVersion to set
	 */
	public void setAndroidVersion(String androidVersion) {
		mAndroidVersion = androidVersion;
	}


    
    
}
