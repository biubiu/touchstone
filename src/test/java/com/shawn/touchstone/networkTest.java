package com.shawn.touchstone;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertFalse;

public class networkTest {

    @Test
    public void ip() throws UnknownHostException {
        String v6 = "2001:DB8::21f:5bff:febf:ce22:8a2e";
        String v4 = "122.202.10.250";
        InetAddress addr4 = InetAddress.getByName(v4);
        InetAddress addr6 = InetAddress.getByName(v6);
        assertFalse(addr4.isAnyLocalAddress());
        assertFalse(addr6.isAnyLocalAddress());
        assertFalse(addr6.isSiteLocalAddress());
        assertFalse(addr6.isLinkLocalAddress());
        assertFalse(addr6.isLoopbackAddress());
        assertFalse(addr6.isMulticastAddress());
    }
}
