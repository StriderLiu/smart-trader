package com.strider.trading.handler;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.strider.trading.event.StockEvent;
import com.strider.trading.subscriber.ORBStrategyBuySubscriber;
import com.strider.trading.subscriber.ORBStrategySellSubscriber;
import com.strider.trading.subscriber.StrategySubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ORBEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ORBEventHandler.class);

    private static EPServiceProvider epService;
    private List<StrategySubscriber> subscribers;

    public ORBEventHandler() {
        LOG.debug("Initializing Servcie ..");
        setupEPServiceProvider();
        registerEventSubscribers();
    }

    private void setupEPServiceProvider() {
        if (epService == null) {
            Configuration config = new Configuration();
            config.addEventTypeAutoName("com.strider.trading.event");
            epService = EPServiceProviderManager.getDefaultProvider(config);
        }
    }

    private void registerEventSubscribers() {
        subscribers = new ArrayList<>();
        subscribers.add(new ORBStrategyBuySubscriber());
        subscribers.add(new ORBStrategySellSubscriber());
        for (StrategySubscriber ss : subscribers) {
            LOG.debug("registering event subscriber: " + ss);
            EPStatement eventStatement = epService.getEPAdministrator().createEPL(ss.getEventStatement());
            eventStatement.setSubscriber(ss);
        }
    }

    public void handle(StockEvent event) {
        LOG.debug(event.toString());
        epService.getEPRuntime().sendEvent(event);
    }
}
