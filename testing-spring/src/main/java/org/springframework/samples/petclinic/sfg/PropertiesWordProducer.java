package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesWordProducer implements WordProducer {
    private final String word;

    public  PropertiesWordProducer(@Value("${say.word}") String word) {
        this.word = word;
    }

    @Override
    public String getWord() {
        return word;
    }
}
