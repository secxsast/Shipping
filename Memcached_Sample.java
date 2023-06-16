package com.amazon.elasticache;

import java.io.IOException;
import java.net.InetSocketAddress;

// Import the &AWS;-provided library with Auto Discovery support 
import net.spy.memcached.MemcachedClient;  

public class AutoDiscoveryDemo {

    @Value("${app.awsServices.MemCache1}")
    private String MemCache1;

    @Value("${app.awsServices.MemCache2}")
    private String MemCache2;

    public static void main(String[] args) throws IOException { 
        MemcachedClient client1 = new MemcachedClient(new InetSocketAddress(MemCache1, 123)); 

	InetSocketAddress address = new InetSocketAddress(MemCache2, 123);
        MemcachedClient client2 = new MemcachedClient(address);     
		
    }
}