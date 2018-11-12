/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading.subscriber;

import com.strider.trading.event.StockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

// Opening Range Breakout -- capture buy signal
public class ORBStrategyBuySubscriber implements StrategySubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(StrategySubscriber.class);
    private static final double OPENING_RANGE_HIGH = 40.0;

    @Override
    public String getEventStatement() {
        return "select se.* from StockEvent(price > " + OPENING_RANGE_HIGH + ") as se";
    }

    @Override
    public void update(Map<String, StockEvent> eventMap) {
        LOG.debug("Opening Range High Breakout for stock " + eventMap.get("se").getTicker()
                + " with price: " + eventMap.get("se").getPrice());
        LOG.debug("Let's BUY some " + eventMap.get("se").getTicker());
    }
}
