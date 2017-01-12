package com.dwinkelman.languagepalace;

/**
 * Created by Daniel on 12/31/2016.
 */

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.*;

/**
 * Data structure for holding flashcard data
 */
public class Card {
    private static final String DEFAULT_TAGS = "";
    private static final int DEFAULT_ID = -1;
    private static final String DEFAULT_DATE = new Date(0).toString();
    private static final String DEFAULT_CARDS = "";

    private static final String CARD_VALUE_SEPARATOR = ";";
    private static final String CARD_KEYVAL_SEPARATOR = ",";
    private static final String TAG_SEPARATOR = ";";

    private Vector<CardValue> values;
    private Vector<String> tags;
    private String createdAt;
    private int id, setId;
    private DatabaseHandler db;

    // constructors
    public Card(){
        SetCardValues(DEFAULT_CARDS);
        SetTags(DEFAULT_TAGS);
        SetDateCreated(DEFAULT_DATE);
        SetId(DEFAULT_ID);
        SetSetId(DEFAULT_ID);
    }
    public Card(String vals){
        this();
        SetCardValues(vals);
    }
    public Card(String vals, int id){
        this(vals);
        SetId(id);
    }
    public Card(String vals, int id, DatabaseHandler dbh){
        this(vals, id);
        this.db = dbh;
    }

    // getters
    public CardValue GetCardValue(int index){
        return this.values.elementAt(index);
    }
    public Vector<CardValue> GetCardValuesAsVector(){
        return this.values;
    }
    public String GetCardValuesAsString(){
        Vector<String> elements = new Vector<String>(values.size());
        for(CardValue cv : values){
            elements.add(cv.GetValue() + CARD_KEYVAL_SEPARATOR + cv.GetLanguage());
        }
        return TextUtils.join(CARD_VALUE_SEPARATOR, elements);
    }
    public int GetId(){
        return id;
    }
    public boolean IsIdSet(){
        return id != DEFAULT_ID;
    }
    public int GetSetId(){
        return setId;
    }
    public String GetTagsAsString(){
        return TextUtils.join(TAG_SEPARATOR, tags);
    }
    public Vector<String> GetTagsAsVector(){
        return tags;
    }
    public String GetTag(int index){
        return tags.elementAt(index);
    }
    public String GetDateCreated(){
        return createdAt;
    }
    public boolean IsDateCreatedSet(){
        return !createdAt.equals(DEFAULT_DATE);
    }

    // setters
    public void AddCardValue(CardValue value){
        this.values.add(value);
    }
    public void RemoveCardValue(int index){
        this.values.remove(index);
    }
    public void SetCardValue(CardValue value, int index){
        this.values.set(index, value);
    }
    public void SetCardValues(Vector<CardValue> values){
        this.values = values;
    }
    public void SetCardValues(String vals){
        for(String kv : vals.split(CARD_VALUE_SEPARATOR)){
            String[] parts = kv.split(CARD_KEYVAL_SEPARATOR);
            // check parts
            if(parts.length < 2){
                throw new Error("Card value-language pair must have at least those elements.");
            }else if(parts[1].length() != 3){
                throw new Error("Language code must be 3 characters long.");
            }
            AddCardValue(new CardValue(parts[0], parts[1]));
        }
    }
    public void SetId(int id){
        this.id = id;
    }
    public void SetSetId(int setId){
        this.setId = setId;
    }
    public void SetTags(String tags){
        this.tags = new Vector<String>(Arrays.asList(tags.split(TAG_SEPARATOR)));
    }
    public void SetTags(Vector<String> tags){
        this.tags = tags;
    }
    public void SetTag(String tag, int index){
        this.tags.set(index, tag);
    }
    public void RemoveTag(int index){
        this.tags.remove(index);
    }
    public void SetDateCreated(String date){
        createdAt = date;
    }
    public void SetDatabase(DatabaseHandler dbh){
        this.db = dbh;
    }
}