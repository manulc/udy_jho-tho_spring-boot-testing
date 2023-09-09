package com.mlorenzo.tddbyexpample;

import java.util.Objects;

public class RatePair {
    private final String from;
    private final String to;

    public RatePair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        if (this == o)
            return true;
        RatePair ratePair = (RatePair) o;
        return Objects.equals(from, ratePair.from) && Objects.equals(to, ratePair.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
