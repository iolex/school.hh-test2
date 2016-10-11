package com.company;
import java.math.BigInteger;

/**
 * Created by iolex on 10.10.2016.
 */
public class NumberStatus {

    private String number;
    private Boolean status;
    private BigInteger position;
    public NumberStatus(String number, Boolean status, BigInteger position) {
        this.number = number;
        this.status = status;
        this.position = position;
    }

    public String getNumber() {
        return this.number;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public BigInteger getPosition() {
        return this.position;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPosition(BigInteger position) {
        this.position = position;
    }

}
