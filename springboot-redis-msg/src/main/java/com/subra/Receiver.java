package com.subra;


import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Receiver {
    //private static final Logger log = LoggerFactory.getLogger(Receiver.class);
	private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
    	//log.debug("test-----------1111111111111111");
        this.latch = latch;
    }

    public void receiveMessage(String message) {
    	System.out.println("Recived: " + message);
    	//log.debug("test-----------2222222222222");
        //log.info("Received <" + message + ">");
        latch.countDown();
    }
}