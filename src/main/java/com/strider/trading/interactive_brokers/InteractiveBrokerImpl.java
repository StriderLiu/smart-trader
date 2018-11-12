package com.strider.trading.interactive_brokers;

import com.ib.client.*;
import com.strider.trading.SmartTraderDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InteractiveBrokerImpl implements EWrapper {

    private EJavaSignal m_signal;
    private EClientSocket m_client;
    private EReader m_reader;
    private static final Logger LOG = LoggerFactory.getLogger(SmartTraderDemo.class);
    private static final int NUMBER_OF_WORKER_THREADS = 1;
    private static InteractiveBrokerImpl s_instance;

    private final Lock m_lock = new ReentrantLock();
    private final Condition m_contractDataNotReady = m_lock.newCondition();
    private Map<Integer, ContractDetails> m_contractDetailsMap;
    private ExecutorService executor;

    private InteractiveBrokerImpl() {
        m_signal = new EJavaSignal();
        m_client = new EClientSocket( this, m_signal);
        m_contractDetailsMap = new HashMap<>();
        executor = Executors.newFixedThreadPool(NUMBER_OF_WORKER_THREADS);
    }

    // Singleton can only be accessed via this method
    public static InteractiveBrokerImpl getInctance() {
        if (s_instance == null) {
            s_instance = new InteractiveBrokerImpl();
        }
        return s_instance;
    }

    public boolean connect(String ipAddress, int port, int clientId) {
        if (m_client.isConnected()) {
            return true;
        }

        m_client.optionalCapabilities("");
        m_client.eConnect(ipAddress, port, clientId);

        LOG.debug("Has the application connected to TWS? " + (m_client.isConnected() ? "YES" : "NO"));

        m_reader = new EReader(m_client, m_signal);
        m_reader.start();

        // somehow the connection is lost after we initialize the EReader object
        LOG.debug("Has the application connected to TWS? " + (m_client.isConnected() ? "YES" : "NO"));

        executor.submit(() -> {
            processMessages();
        });

        return m_client.isConnected();
    }

    public void disconnect() {
        m_client.eDisconnect();

        // shutdown executor service
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    private void processMessages() {
        while (m_client.isConnected()) {
            m_signal.waitForSignal();
            try {
                m_reader.processMsgs();
            } catch (Exception e) {
                error(e);
            }
        }
    }

    @Override
    public void contractDetails(int reqId, ContractDetails contractDetails) {
        m_lock.lock();
        try {
            m_contractDetailsMap.put(reqId, contractDetails);
            m_contractDataNotReady.signal();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            m_lock.unlock();
        }
    }

    public ContractDetails getContractDetails(int reqId, Contract contract) {
        m_client.reqContractDetails(reqId, contract);
        ContractDetails cd = null;
        m_lock.lock();
        try {
            while (!m_contractDetailsMap.containsKey(reqId)) {
                m_contractDataNotReady.await();
            }
            cd = m_contractDetailsMap.get(reqId);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            ex.printStackTrace();
        } finally {
            m_lock.unlock();
        }
        return cd;
    }

    @Override
    public void tickPrice(int i, int i1, double v, TickAttr tickAttr) {

    }

    @Override
    public void tickSize(int i, int i1, int i2) {

    }

    @Override
    public void tickOptionComputation(int i, int i1, double v, double v1, double v2, double v3, double v4, double v5, double v6, double v7) {

    }

    @Override
    public void tickGeneric(int i, int i1, double v) {

    }

    @Override
    public void tickString(int i, int i1, String s) {

    }

    @Override
    public void tickEFP(int i, int i1, double v, String s, double v1, int i2, String s1, double v2, double v3) {

    }

    @Override
    public void orderStatus(int i, String s, double v, double v1, double v2, int i1, int i2, double v3, int i3, String s1, double v4) {

    }

    @Override
    public void openOrder(int i, Contract contract, Order order, OrderState orderState) {

    }

    @Override
    public void openOrderEnd() {

    }

    @Override
    public void updateAccountValue(String s, String s1, String s2, String s3) {

    }

    @Override
    public void updatePortfolio(Contract contract, double v, double v1, double v2, double v3, double v4, double v5, String s) {

    }

    @Override
    public void updateAccountTime(String s) {

    }

    @Override
    public void accountDownloadEnd(String s) {

    }

    @Override
    public void nextValidId(int i) {

    }

    @Override
    public void bondContractDetails(int i, ContractDetails contractDetails) {

    }

    @Override
    public void contractDetailsEnd(int i) {

    }

    @Override
    public void execDetails(int i, Contract contract, Execution execution) {

    }

    @Override
    public void execDetailsEnd(int i) {

    }

    @Override
    public void updateMktDepth(int i, int i1, int i2, int i3, double v, int i4) {

    }

    @Override
    public void updateMktDepthL2(int i, int i1, String s, int i2, int i3, double v, int i4) {

    }

    @Override
    public void updateNewsBulletin(int i, int i1, String s, String s1) {

    }

    @Override
    public void managedAccounts(String s) {

    }

    @Override
    public void receiveFA(int i, String s) {

    }

    @Override
    public void historicalData(int i, Bar bar) {

    }

    @Override
    public void scannerParameters(String s) {

    }

    @Override
    public void scannerData(int i, int i1, ContractDetails contractDetails, String s, String s1, String s2, String s3) {

    }

    @Override
    public void scannerDataEnd(int i) {

    }

    @Override
    public void realtimeBar(int i, long l, double v, double v1, double v2, double v3, long l1, double v4, int i1) {

    }

    @Override
    public void currentTime(long l) {

    }

    @Override
    public void fundamentalData(int i, String s) {

    }

    @Override
    public void deltaNeutralValidation(int i, DeltaNeutralContract deltaNeutralContract) {

    }

    @Override
    public void tickSnapshotEnd(int i) {

    }

    @Override
    public void marketDataType(int i, int i1) {

    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {

    }

    @Override
    public void position(String s, Contract contract, double v, double v1) {

    }

    @Override
    public void positionEnd() {

    }

    @Override
    public void accountSummary(int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void accountSummaryEnd(int i) {

    }

    @Override
    public void verifyMessageAPI(String s) {

    }

    @Override
    public void verifyCompleted(boolean b, String s) {

    }

    @Override
    public void verifyAndAuthMessageAPI(String s, String s1) {

    }

    @Override
    public void verifyAndAuthCompleted(boolean b, String s) {

    }

    @Override
    public void displayGroupList(int i, String s) {

    }

    @Override
    public void displayGroupUpdated(int i, String s) {

    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void error(int i, int i1, String s) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectAck() {

    }

    @Override
    public void positionMulti(int i, String s, String s1, Contract contract, double v, double v1) {

    }

    @Override
    public void positionMultiEnd(int i) {

    }

    @Override
    public void accountUpdateMulti(int i, String s, String s1, String s2, String s3, String s4) {

    }

    @Override
    public void accountUpdateMultiEnd(int i) {

    }

    @Override
    public void securityDefinitionOptionalParameter(int i, String s, int i1, String s1, String s2, Set<String> set, Set<Double> set1) {

    }

    @Override
    public void securityDefinitionOptionalParameterEnd(int i) {

    }

    @Override
    public void softDollarTiers(int i, SoftDollarTier[] softDollarTiers) {

    }

    @Override
    public void familyCodes(FamilyCode[] familyCodes) {

    }

    @Override
    public void symbolSamples(int i, ContractDescription[] contractDescriptions) {

    }

    @Override
    public void historicalDataEnd(int i, String s, String s1) {

    }

    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

    }

    @Override
    public void tickNews(int i, long l, String s, String s1, String s2, String s3) {

    }

    @Override
    public void smartComponents(int i, Map<Integer, Map.Entry<String, Character>> map) {

    }

    @Override
    public void tickReqParams(int i, double v, String s, int i1) {

    }

    @Override
    public void newsProviders(NewsProvider[] newsProviders) {

    }

    @Override
    public void newsArticle(int i, int i1, String s) {

    }

    @Override
    public void historicalNews(int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void historicalNewsEnd(int i, boolean b) {

    }

    @Override
    public void headTimestamp(int i, String s) {

    }

    @Override
    public void histogramData(int i, List<HistogramEntry> list) {

    }

    @Override
    public void historicalDataUpdate(int i, Bar bar) {

    }

    @Override
    public void rerouteMktDataReq(int i, int i1, String s) {

    }

    @Override
    public void rerouteMktDepthReq(int i, int i1, String s) {

    }

    @Override
    public void marketRule(int i, PriceIncrement[] priceIncrements) {

    }

    @Override
    public void pnl(int i, double v, double v1, double v2) {

    }

    @Override
    public void pnlSingle(int i, int i1, double v, double v1, double v2, double v3) {

    }

    @Override
    public void historicalTicks(int i, List<HistoricalTick> list, boolean b) {

    }

    @Override
    public void historicalTicksBidAsk(int i, List<HistoricalTickBidAsk> list, boolean b) {

    }

    @Override
    public void historicalTicksLast(int i, List<HistoricalTickLast> list, boolean b) {

    }

    @Override
    public void tickByTickAllLast(int i, int i1, long l, double v, int i2, TickAttr tickAttr, String s, String s1) {

    }

    @Override
    public void tickByTickBidAsk(int i, long l, double v, double v1, int i1, int i2, TickAttr tickAttr) {

    }

    @Override
    public void tickByTickMidPoint(int i, long l, double v) {

    }
}
