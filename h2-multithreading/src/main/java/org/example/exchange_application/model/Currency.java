package org.example.exchange_application.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;

    private CurrencyEnum name;

    private BigDecimal value;

    public Currency(CurrencyEnum name,BigDecimal value) {
        this.name = name;
        this.value=value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyEnum getName() {
        return name;
    }

    public void setName(CurrencyEnum name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Currency{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }
}
