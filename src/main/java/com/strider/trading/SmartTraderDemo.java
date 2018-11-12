/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.strider.trading.interactive_brokers.IBUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class SmartTraderDemo {

    private static final Logger LOG = LoggerFactory.getLogger(SmartTraderDemo.class);

    private static final LocalTime MARKET_OPENING_TIME = LocalTime.of(9, 30);
    private static final LocalTime MARKET_CLOSING_TIME = LocalTime.of(4, 0);
    private static final Contract CONTRACT;

    static {
        CONTRACT = new Contract();
        CONTRACT.symbol("F");
        CONTRACT.secType("STK");
        CONTRACT.currency("USD");
        CONTRACT.exchange("SMART");
        CONTRACT.primaryExch("NYSE");
        CONTRACT.lastTradeDateOrContractMonth("");
        CONTRACT.strike(0.0);
        CONTRACT.right("");
        CONTRACT.multiplier("");
        CONTRACT.localSymbol("");
        CONTRACT.tradingClass("");
        CONTRACT.secIdType("");
        CONTRACT.secId("");
    }

    /**
     * Main method - start the Demo!
     */
    public static void main(String[] args) throws Exception {

        LOG.debug("Starting...");

        if (IBUtility.connect("127.0.0.1", 7497, 1)) {
            LOG.debug("Connected to TWS successfully!");
        }

        ContractDetails stockDetail = getContractDetails(CONTRACT);
        LOG.debug("Start to fetch data for: " + stockDetail.contract().symbol()
                + "\nDescription: \n" + stockDetail.contract().description()
                + "\nExchange: \n" + stockDetail.contract().exchange()
                + "\nPrimary Exchange: \n" + stockDetail.contract().primaryExch());

        getOpenRangeOfToday(MARKET_OPENING_TIME);

//        // Start Demo
//        RandomEventGenerator generator = new RandomEventGenerator();
//        generator.startSendingStockReadings(noOfStockEvents);

//        startSendingStockReadings();
        disconnect();
    }

    private static void disconnect() {
        IBUtility.disconnect();
    }

    private static ContractDetails getContractDetails(Contract contract) {
        return IBUtility.getContractDetails(contract);
    }

    private static void getOpenRangeOfToday(LocalTime startTime) {
        IBUtility.getOpenRangeOfToday(startTime);
    }

    private static void startSendingStockReadings() {
    }

}
