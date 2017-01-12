package com.dwinkelman.languagepalace;

/**
 * Created by Daniel on 12/31/2016.
 */

import java.util.*;

public class CardValue {
    // special language codes
    private static final String IMAGE = "img";
    private static final String DEFAULT = "eng";

    private String language;
    private String value;

    // constructors
    public CardValue(){}
    public CardValue(String value){
        SetValue(value);
        SetLanguage(DEFAULT);
    }
    public CardValue(String value, String language){
        SetValue(value);
        SetLanguage(language);
    }

    // getters
    public String GetLanguage(){
        return language;
    }
    public String GetValue(){
        return value;
    }
    public boolean IsImage(){
        return language == IMAGE;
    }

    // setters
    public void SetLanguage(String language){
        this.language = language;
    }
    public void SetValue(String value) {
        this.value = value;
    }
}
