package lab.mp.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Represents messages.
 */
@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String userId;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amountSell;
    private BigDecimal amountBuy;
    private double rate;
    private Date timePlaced;
    private String originatingCountry;

    private boolean processed;

    /**
     * Message constructor
     * @param userId user id
     * @param currencyFrom from which currency
     * @param currencyTo to which currency
     * @param amountSell amount sell
     * @param amountBuy amount buy
     * @param rate rate
     * @param timePlaced when conversion happened
     * @param originatingCountry originating country
     * @param processed is this transaction processed
     */
    public Message(
            final String userId,
            final String currencyFrom,
            final String currencyTo,
            final BigDecimal amountSell,
            final BigDecimal amountBuy,
            final double rate,
            final Date timePlaced,
            final String originatingCountry,
            boolean processed
    ) {
        this.originatingCountry = originatingCountry;
        this.userId = userId;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amountSell = amountSell;
        this.amountBuy = amountBuy;
        this.rate = rate;
        this.timePlaced = timePlaced;
        this.processed = processed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public BigDecimal getAmountSell() {
        return amountSell;
    }

    public void setAmountSell(BigDecimal amountSell) {
        this.amountSell = amountSell;
    }

    public BigDecimal getAmountBuy() {
        return amountBuy;
    }

    public void setAmountBuy(BigDecimal amountBuy) {
        this.amountBuy = amountBuy;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(Date timePlaced) {
        this.timePlaced = timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(String originatingCountry) {
        this.originatingCountry = originatingCountry;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
