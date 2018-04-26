package io.evercam.network.discovery;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.json.JSONObject;

public class Device implements Serializable, DeviceInterface {
    private static final long serialVersionUID = 1L;
    protected String ip = "";
    protected String mac = "";
    protected String externalIp = "";
    protected String publicVendor = "";

    public Device(String ip) {
	this.ip = ip;
    }

    public String getIP() {
	return ip;
    }

    public void setIP(String ip) {
	this.ip = ip;
    }

    public String getMAC() {
	return mac;
    }

    public void setMAC(String mac) {
	this.mac = mac;
    }

    public String getExternalIp() {
	return externalIp;
    }

    public void setExternalIp(String externalIp) {
	this.externalIp = externalIp;
    }

    public void setPublicVendor(String publicVendor) {
	this.publicVendor = publicVendor;
    }

    public String getPublicVendor() {
	return publicVendor;
    }

    @Override
    public String toString() {
	return "Device [ip=" + ip + ", mac=" + mac + ", externalIp="
		+ externalIp + ", publicVendor=" + publicVendor + "]";
    }

    public JSONObject toJsonObject() {
	LinkedHashMap<String, Object> jsonOrderedMap = new LinkedHashMap<String, Object>();

	jsonOrderedMap.put("lan_ip", getIP());
	jsonOrderedMap.put("mac_address", getMAC());
	jsonOrderedMap.put("wan_ip", getExternalIp());
	jsonOrderedMap.put("public_vendor", getPublicVendor());

	return new JSONObject(jsonOrderedMap);
    }
}
