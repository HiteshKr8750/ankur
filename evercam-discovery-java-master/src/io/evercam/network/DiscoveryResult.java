package io.evercam.network;

import io.evercam.network.discovery.Device;
import io.evercam.network.discovery.DiscoveredCamera;

import java.util.ArrayList;

public class DiscoveryResult {
    private ArrayList<DiscoveredCamera> cameras;
    private ArrayList<Device> nonCameraDevices;

    public DiscoveryResult(ArrayList<DiscoveredCamera> cameras,
	    ArrayList<Device> nonCameraDevices) {
	this.cameras = cameras;
	this.nonCameraDevices = nonCameraDevices;
    }

    public ArrayList<DiscoveredCamera> getCameras() {
	return cameras;
    }

    public ArrayList<Device> getOtherDevices() {
	return nonCameraDevices;
    }
}
