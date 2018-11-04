package com.strider.trading.subscriber;

import com.strider.trading.event.StockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

// Opening Range Breakout -- capture sell signal
public class ORBStrategySellSubscriber implements StrategySubscriber{
    private static final Logger LOG = LoggerFactory.getLogger(StrategySubscriber.class);
    private static final double OPENING_RANGE_LOW = 30.0;

    @Override
    public String getEventStatement() {
        return "select se.* from StockEvent(price < " + OPENING_RANGE_LOW + ") as se";
    }

    @Override
    public void update(Map<String, StockEvent> eventMap) {
        LOG.debug("Opening Range Low Breakout for stock " + eventMap.get("se").getTicker()
                + " with price: " + eventMap.get("se").getPrice());
        LOG.debug("Let's SELL some " + eventMap.get("se").getTicker());
    }
}
