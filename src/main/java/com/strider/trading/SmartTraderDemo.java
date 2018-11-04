package com.strider.trading;

import com.strider.trading.interactive_brokers.IBUtility;
import com.strider.trading.security.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class SmartTraderDemo {

    private static final Logger LOG = LoggerFactory.getLogger(SmartTraderDemo.class);

    private static final LocalTime MARKET_OPENING_TIME = LocalTime.of(9, 30);
    private static final LocalTime MARKET_CLOSING_TIME = LocalTime.of(4, 0);
    private static final String FAVORITE_STOCK_TICKER = "F";

    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");

        IBUtility.connect("127.0.0.1", 7497, 1);

        Stock stock = getStockInfo(FAVORITE_STOCK_TICKER);
        getOpenRangeOfToday();

//        // Start Demo
//        RandomEventGenerator generator = new RandomEventGenerator();
//        generator.startSendingStockReadings(noOfStockEvents);
        startSendingStockReadings();
    }

    private static Stock getStockInfo(String stockName) {
        return IBUtility.requestContract(stockName);
    }

    private static void getOpenRangeOfToday() {
        IBUtility.getOpenRangeOfToday();
    }

    private static void startSendingStockReadings() {
    }


}
