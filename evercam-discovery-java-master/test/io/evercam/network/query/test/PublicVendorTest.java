package io.evercam.network.query.test;

import static org.junit.Assert.assertEquals;
import io.evercam.network.query.PublicVendor;

import org.junit.Test;

public class PublicVendorTest {
    @Test
    public void testGetPublicVendor() {
	assertEquals("Samsung Electonics Digital Video System Division",
		PublicVendor.getByMac("00:16:6C").getCompany());
    }
}
