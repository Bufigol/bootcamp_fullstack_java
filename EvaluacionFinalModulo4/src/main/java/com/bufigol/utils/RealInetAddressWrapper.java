package com.bufigol.utils;

import com.bufigol.interfaces.InetAddressWrapper;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RealInetAddressWrapper implements InetAddressWrapper {

    @Override
    public String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @Override
    public InetAddress[] getAllByName(String host) throws UnknownHostException {
        return InetAddress.getAllByName(host);
    }

    @Override
    public InetAddress getByName(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }

    @Override
    public String getLocalHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
}