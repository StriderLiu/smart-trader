package com.strider.trading.interactive_brokers;

import com.strider.trading.SmartTraderDemo;
import com.strider.trading.security.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Middle layer between business layer and IB API
// Composition (loose coupling): contains a single instance of implemented EWrapper class
// Thus, we can use whatever we need from IB API and customize the method signature
public class IBUtility {

    private static final Logger LOG = LoggerFactory.getLogger(SmartTraderDemo.class);

    private static InteractiveBrokerImpl ibHelper;

    static {
        ibHelper = InteractiveBrokerImpl.getInctance();

    }

    public static boolean connect(String ipAddress, int port, int clientId) {
        return ibHelper.connect(ipAddress, port, clientId);
    }

    public static Stock requestContract(String stockName) {

    }

    public static void getOpenRangeOfToday() {
    }
}
