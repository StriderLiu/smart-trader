package com.strider.trading.subscriber;

import com.strider.trading.event.StockEvent;

import java.util.Map;

public interface StrategySubscriber {
    public String getEventStatement();
    public void update(Map<String, StockEvent> eventMap);
}
