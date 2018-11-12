/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading.interactive_brokers;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.strider.trading.SmartTraderDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

// Middle layer between business layer and IB API
// Composition (loose coupling): contains a single instance of implemented EWrapper class
// Thus, we can use whatever we need from IB API and customize the method signature
public class IBUtility {

    private static final Logger LOG = LoggerFactory.getLogger(SmartTraderDemo.class);

    private static InteractiveBrokerImpl m_ibHelper;
    private static int m_reqId;

    static {
        m_ibHelper = InteractiveBrokerImpl.getInctance();
        m_reqId = 1;
    }

    public static boolean connect(String ipAddress, int port, int clientId) {
        return m_ibHelper.connect(ipAddress, port, clientId);
    }

    public static ContractDetails getContractDetails(Contract contract) {
        ContractDetails cd = null;
        return m_ibHelper.getContractDetails(m_reqId++, contract);
    }

    // get HIGH and LOW from first 30min candle
    public static void getOpenRangeOfToday(LocalTime startTime) {
        // ...
    }

    public static void disconnect() {
        m_ibHelper.disconnect();
    }
}
