package com.mlorenzo.tddbyexpample;

public class Money implements Expression {
    private final int amount;
    private final String currency;

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money)obj;
        return amount == money.amount && currency.equals(money.currency);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Money reduce(Bank bank, String toCurrency) {
        int rate = bank.getRate(currency, toCurrency);
        return new Money(amount/rate, toCurrency);
    }
}
