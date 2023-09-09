package com.mlorenzo.tddbyexpample;

public interface Expression {
    Money reduce(Bank bank, String toCurrency);
    Expression plus(Expression addend);
    Expression times(int multiplier);
}
