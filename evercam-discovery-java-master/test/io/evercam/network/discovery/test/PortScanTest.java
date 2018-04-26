package io.evercam.network.discovery.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.evercam.network.discovery.Port;
import io.evercam.network.discovery.PortScan;

import java.util.ArrayList;

import org.junit.Test;
import org.simpleframework.xml.Text;

public class PortScanTest {
    @Test
    public void testScanPort() throws Exception {
	PortScan portScan = new PortScan();
	portScan.start("10.0.0.41");
	ArrayList<Port> activePortList = portScan.getActivePorts();

	for (Port port : activePortList) {
	    // System.out.println(port.getValue());
	}
	assertEquals(2, activePortList.size());
    }

    @Test
    public void testScanSinglePort() throws Exception {
	assertTrue(Port.isReachable("10.0.0.41", 80));
    }

    @Test
    public void testExternalHostPorts() throws Exception {
	assertFalse(Port.isReachable("89.101.245.147", 80));
	assertTrue(Port.isReachable("89.101.245.147", 8101));
    }
}
