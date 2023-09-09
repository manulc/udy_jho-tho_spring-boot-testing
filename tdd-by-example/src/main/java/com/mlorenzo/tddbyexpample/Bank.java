package com.mlorenzo.tddbyexpample;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<RatePair, Integer> rateMap = new HashMap<>();

    public Money reduce(Expression source, String toCurrency) {
        return source.reduce(this, toCurrency);
    }

    public int getRate(String from, String to) {
        if(from.equals(to))
            return 1;
        return rateMap.get(new RatePair(from, to));
    }

    public void addRate(String from, String to, int rate) {
        rateMap.put(new RatePair(from, to), rate);
    }
}
