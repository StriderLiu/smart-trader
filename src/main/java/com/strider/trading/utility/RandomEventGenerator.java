/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading.utility;

import com.strider.trading.event.StockEvent;
import com.strider.trading.handler.ORBEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomEventGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(RandomEventGenerator.class);

    private ORBEventHandler eventHandler;

    public RandomEventGenerator() {
        eventHandler = new ORBEventHandler();
    }

    /**
     * Create simple random stock events and let the implementation class handle them.
     */
    public void startSendingStockReadings(final long noOfStockEvents) {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {
                LOG.debug(getStartingMessage());
                int count = 0;
                while (count < noOfStockEvents) {
                    StockEvent se = new StockEvent("AMZN", "Amazon", "stock",
                            new Date(), new Random().nextInt(30) + 20);
                    eventHandler.handle(se);
                    count++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        LOG.error("Thread Interrupted", e);
                    }
                }
            }
        });

        xrayExecutor.shutdown();
    }

    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* ------ STARTING APPLICATION ------ *");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}
