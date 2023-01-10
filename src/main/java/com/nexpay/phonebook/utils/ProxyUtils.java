package com.nexpay.phonebook.utils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public class ProxyUtils {

    private static final String PROXY_PORT_PARAM = "http.proxyPort";
    private static final String PROXY_HOST_PARAM = "http.proxyHost";

    private ProxyUtils() {}

    public static Proxy getSystemHttpProxy(String host) {
        if (host == null || host.trim().isEmpty()) {
            host = resolveProxyHost();
        }

        if (host != null) {
            Integer port = resolveProxyPort();
            return new Proxy(Type.HTTP, new InetSocketAddress(host, port));
        }
        return null;
    }

    
    public static Integer resolveProxyPort() {
        return Integer.valueOf(System.getProperty(PROXY_PORT_PARAM, "3128"));
    }

    public static String resolveProxyHost() {
        return System.getProperty(PROXY_HOST_PARAM, null);
    }

}
