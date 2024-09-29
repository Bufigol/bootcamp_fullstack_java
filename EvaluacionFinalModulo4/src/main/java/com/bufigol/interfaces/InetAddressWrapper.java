package com.bufigol.interfaces;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface InetAddressWrapper {
    /**
     * Returns the IP address string in textual presentation.
     *
     * @return the raw IP address in a string format.
     * @throws UnknownHostException if the IP address of the host could not be determined.
     */
    String getHostAddress() throws UnknownHostException;

    /**
     * Given the name of a host, returns an array of its IP addresses, based on the configured name service on the system.
     *
     * @param host the name of the host.
     * @return an array of all IP addresses for the given host name.
     * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address.
     */
    InetAddress[] getAllByName(String host) throws UnknownHostException;

    /**
     * Determines the IP address of a host, given the host's name.
     *
     * @param host the name of the host.
     * @return an InetAddress object containing the IP address of the named host.
     * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address.
     */
    InetAddress getByName(String host) throws UnknownHostException;

    /**
     * Returns the local host name.
     *
     * @return a string containing the host name of the local host.
     * @throws UnknownHostException if the local host name could not be resolved into an address.
     */
    String getLocalHostName() throws UnknownHostException;

}
