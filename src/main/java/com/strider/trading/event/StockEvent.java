/*
 * Copyright (c) 2018. StriderLiu - All Rights Reserved
 * You can not use, distribute and modify this code unless permitted by the author (StriderLiu)
 * To gain access to the usage of this project, please email to liu.wenb@huksy.neu.edu
 */

package com.strider.trading.event;

import java.util.Date;

public class StockEvent {
    private String ticker;
    private Date happenTime;

    private double price;
    private double highPrice;
    private double lowPrice;
    private double openPrice;
    private double closePrice;

    public StockEvent(String ticker, String name, String type, Date date, double price) {
        this.ticker = ticker;
        this.price = price;
    }

    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return price;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public Date getHappenTime() { return happenTime; }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public void setHappenTime(Date happenTime) { this.happenTime = happenTime; }
}
