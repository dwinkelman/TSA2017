package com.dwinkelman.languagepalace;

/**
 * Created by Daniel on 12/31/2016.
 */

import android.text.TextUtils;
import java.util.*;

public class CardSet {
    private static final Vector<Card> DEFAULT_CARDS = new Vector<Card>();
    private static final String DEFAULT_NAME = "New Set";
    private static final String DEFAULT_DESCRIPTION = "";
    private static final String DEFAULT_TAGS = "";
    private static final int DEFAULT_ID = -1;
    private static final String DEFAULT_DATE = new Date(0).toString();

    private static final String TAG_SEPARATOR = ";";

    private Vector<Card> cards;
    private Vector<String> tags;
    private String name, description;
    private String createdAt, modifiedAt, lastOpenedAt;
    private int id;
    private DatabaseHandler db;

    // constructors
    public CardSet(){
        SetCards(DEFAULT_CARDS);
        SetTags(DEFAULT_TAGS);
        SetName(DEFAULT_NAME);
        SetDescription(DEFAULT_DESCRIPTION);
        SetId(DEFAULT_ID);
        SetDateCreated(DEFAULT_DATE);
        SetDateModified(DEFAULT_DATE);
        SetDateLastOpened(DEFAULT_DATE);
    }
    public CardSet(Vector<Card> cards){
        this();
        SetCards(cards);
    }
    public CardSet(Vector<Card> cards, int id){
        this(cards);
        SetId(id);
    }
    public CardSet(Vector<Card> cards, int id, DatabaseHandler dbh){
        this(cards, id);
        this.db = dbh;
    }

    // getters
    public Card GetCard(int index){
        return cards.elementAt(index);
    }
    public Vector<Card> GetCards(){
        return cards;
    }
    public int GetId(){
        return id;
    }
    public boolean IsIdSet(){
        return id != DEFAULT_ID;
    }
    public String GetName(){
        return name;
    }
    public String GetDescription(){
        return description;
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
    public String GetDateModified(){
        return modifiedAt;
    }
    public boolean IsDateModifiedSet(){
        return !modifiedAt.equals(DEFAULT_DATE);
    }
    public String GetDateCreated(){
        return createdAt;
    }
    public boolean IsDateCreatedSet(){
        return !createdAt.equals(DEFAULT_DATE);
    }
    public String GetDateLastOpened(){
        return lastOpenedAt;
    }
    public boolean IsDateLastOpenedSet(){
        return !lastOpenedAt.equals(DEFAULT_DATE);
    }

    // setters
    public void SetCards(Vector<Card> cards){
        this.cards = cards;
    }
    public void SetCard(Card card, int index){
        this.cards.set(index, card);
    }
    public void RemoveCard(int index){
        this.cards.remove(index);
    }
    public void SetId(int id){
        this.id = id;
    }
    public void SetName(String name){
        this.name = name;
    }
    public void SetDescription(String description){
        this.description = description;
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
    public void SetDateModified(String date){
        modifiedAt = date;
    }
    public void SetDateCreated(String date){
        createdAt = date;
    }
    public void SetDateLastOpened(String date){
        lastOpenedAt = date;
    }
    public void SetDatabase(DatabaseHandler dbh){
        this.db = dbh;
    }
}
