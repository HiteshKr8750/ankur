package io.evercam.network;

import io.evercam.network.discovery.Device;
import io.evercam.network.discovery.DiscoveredCamera;
import io.evercam.network.discovery.NetworkInfo;
import io.evercam.network.discovery.ScanRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    private static final String ARG_IP = "-ip";
    private static final String ARG_SUBNET_MASK = "-m";

    /**
     * Discover all cameras in local network and print them in console
     * 
     * @param args
     *            pass parameter -v/--verbose to enable verbose logging
     */
    public static void main(String[] args) {
	String ip = "";
	String subnetMask = "";

	if (args.length > 0) {
	    List<String> argsArray = Arrays.asList(args);
	    if (argsArray.contains("-v") || argsArray.contains("--verbose")) {
		Constants.ENABLE_LOGGING = true;
	    }

	    if (argsArray.contains(ARG_IP)
		    && argsArray.contains(ARG_SUBNET_MASK)) {
		int ipIndex = argsArray.indexOf(ARG_IP) + 1;
		int subnetIndex = argsArray.indexOf(ARG_SUBNET_MASK) + 1;

		ip = argsArray.get(ipIndex);
		subnetMask = argsArray.get(subnetIndex);
	    }
	}

	if (ip.isEmpty())
	    ip = NetworkInfo.getLinuxRouterIp();
	if (subnetMask.isEmpty())
	    subnetMask = NetworkInfo.getLinuxSubnetMask();

	// String deviceIp = "";
	// String subnetMask = "";
	// try
	// {
	// NetworkInterface networkInterface =
	// NetworkInfo.getNetworkInterfaceByName("wlan0");
	// deviceIp = NetworkInfo.getIpFromInterface(networkInterface);
	// subnetMask =
	// IpTranslator.cidrToMask(NetworkInfo.getCidrFromInterface(networkInterface));
	// }
	// catch (Exception e)
	// {
	// // TODO: handle exception
	// }
	EvercamDiscover.printLogMessage("Router IP address: " + ip
		+ " subnet mask: " + subnetMask);
	EvercamDiscover.printLogMessage("Scanning...");

	try {
	    ScanRange scanRange = new ScanRange(ip, subnetMask);

	    DiscoveryResult discoveryResult = new EvercamDiscover()
		    .withDefaults(true).discoverAllLinux(scanRange);

	    ArrayList<DiscoveredCamera> cameraList = discoveryResult
		    .getCameras();
	    ArrayList<Device> nonCameraList = discoveryResult.getOtherDevices();

	    EvercamDiscover.printLogMessage("Scanning finished, found "
		    + cameraList.size() + " cameras and "
		    + nonCameraList.size() + " other devices.");

	    printAsJson(cameraList, nonCameraList);

	    EvercamDiscover.printLogMessage("On normal completion: 0");
	    System.exit(0);
	} catch (Exception e) {
	    if (Constants.ENABLE_LOGGING) {
		e.printStackTrace();
	    }
	    EvercamDiscover.printLogMessage("On error: 1");
	    System.exit(1);
	}
    }

    public static void printAsJson(ArrayList<DiscoveredCamera> cameraList,
	    ArrayList<Device> nonCameraList) {
	if (cameraList != null) {
	    JSONArray cameraJsonArray = new JSONArray();
	    JSONArray nonCameraJsonArray = new JSONArray();

	    for (DiscoveredCamera camera : cameraList) {
		cameraJsonArray.put(camera.toJsonObject());
	    }

	    for (Device device : nonCameraList) {
		nonCameraJsonArray.put(device.toJsonObject());
	    }

	    JSONObject arrayJsonObject = new JSONObject();
	    arrayJsonObject.put("cameras", cameraJsonArray);
	    arrayJsonObject.put("other_devices", nonCameraJsonArray);

	    System.out.println(arrayJsonObject.toString(4));
	}
    }
}
