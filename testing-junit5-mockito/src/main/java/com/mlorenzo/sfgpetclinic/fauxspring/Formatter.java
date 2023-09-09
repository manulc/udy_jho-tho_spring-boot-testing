package com.mlorenzo.sfgpetclinic.fauxspring;

import com.mlorenzo.sfgpetclinic.model.PetType;

import java.text.ParseException;
import java.util.Locale;

public interface Formatter {
    String print(PetType petType, Locale locale);
    PetType parse(String text, Locale locale) throws ParseException;
}
