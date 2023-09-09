package com.mlorenzo.tddbyexpample;

public class Sum implements Expression {
    private final Expression augend;
    private final Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    Expression getAugend() {
        return augend;
    }

    Expression getAddend() {
        return addend;
    }

    @Override
    public Money reduce(Bank bank, String toCurrency) {
        Money reducedAugend = bank.reduce(augend, toCurrency);
        Money reducedAddend = bank.reduce(addend, toCurrency);
        return new Money(reducedAugend.getAmount() + reducedAddend.getAmount(), toCurrency);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sum(augend.times(multiplier), addend.times(multiplier));
    }
}
