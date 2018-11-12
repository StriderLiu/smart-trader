/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading.subscriber;

import com.strider.trading.event.StockEvent;

import java.util.Map;

public interface StrategySubscriber {
    public String getEventStatement();

    public void update(Map<String, StockEvent> eventMap);
}
