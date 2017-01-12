package com.dwinkelman.languagepalace;

/**
 * Created by Daniel on 12/31/2016.
 */

/**
 * DO NOT TOUCH ANYTHING IN THIS FILE UNLESS YOU ABSOLUTELY KNOW WHAT YOU ARE DOING!!!!
 * Changing strings relating to SQLite queries has the potential to mess up databases and create
 * inconsistencies in installed applications (structure/behavior of database will change upon
 * upgrade, causing data to become mixed up).
 */

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    // database variables
    private static final String LOG = DatabaseHandler.class.getName();
    private static final String DATABASE_NAME = "LangPalCards";
    private static final int DATABASE_VERSION = 1;

    /****** Database Structure ****
     *
     * ----  Cards  ----
     * cardid: INTEGER
     * setid: INTEGER
     * values: TEXT
     * tags: TEXT
     * created: DATETIME
     *
     * ----  Sets   ----
     * setid: INTEGER
     * name: TEXT
     * description: TEXT
     * tags: TEXT
     * created: DATETIME
     * modified: DATETIME
     * lastopened: DATETIME
     *
     ******************************/

    // TODO: Make dates uniform.
    // TODO: Add methods for removing rows.

    // table names
    private static final String TABLE_CARDS = "Cards", TABLE_SETS = "Sets";

    // column names
    private static final String COL_CARD_ID = "cardid", COL_SET_ID = "setid", COL_TAGS = "tags",
            COL_CREATED = "created", COL_MODIFIED = "modified", COL_LAST_OPENED = "lastopened",
            COL_VALUES = "values", COL_NAME = "name", COL_DESCRIPTION = "description";

    // table creation queries
    private static final String SQL_CREATE_TABLE_CARDS = "CREATE TABLE " + TABLE_CARDS +
            "(" + COL_CARD_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " + COL_SET_ID +
            " INTEGER, " + COL_VALUES + " TEXT, " + COL_TAGS + " TEXT, " + COL_CREATED +
            " DATETIME);";
    private static final String SQL_CREATE_TABLE_SETS = "CREATE TABLE " + TABLE_SETS +
            "(" + COL_SET_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " + COL_NAME + " TEXT, " +
            COL_DESCRIPTION + " TEXT, " + COL_TAGS + " TEXT, " + COL_CREATED + " DATETIME, " +
            COL_MODIFIED + " DATETIME, " + COL_LAST_OPENED + " DATETIME);";

    // Overridden required members
    // constructor
    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // called on installation of app
    @Override
    public void onCreate(SQLiteDatabase db){
        // create tables
        db.execSQL(SQL_CREATE_TABLE_SETS);
        db.execSQL(SQL_CREATE_TABLE_CARDS);
    }
    // called on upgrade/reinstall of app
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // don't do anything for now
    }


    // generic SQLite methods
    private Cursor Query(String query){
        Log.e(LOG, query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }


    // insert/update objects
    // TODO: This probably works for updating, but a method for updating an individual property would be nice.
    // TODO: Add integration to Card and CardSet to automatically update database upon changes.
    /**
     * Insert a pre-established card set into the database. Does not insert internally-stored cards!
     * @param set Set object to insert into database.
     * @return Most up-to-date set object, essentially adding the official id.
     */
    public CardSet InsertOrUpdateCardSet(CardSet set){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(set.IsIdSet()){
            values.put(COL_SET_ID, set.GetId());
        }
        values.put(COL_NAME, set.GetName());
        values.put(COL_DESCRIPTION, set.GetDescription());
        values.put(COL_TAGS, set.GetTagsAsString());
        if(set.IsDateCreatedSet())
            values.put(COL_CREATED, set.GetDateCreated());
        else
            values.put(COL_CREATED, new java.util.Date().toString());
        if(set.IsDateModifiedSet())
            values.put(COL_MODIFIED, set.GetDateModified());
        else
            values.put(COL_MODIFIED, new java.util.Date().toString());
        if(set.IsDateLastOpenedSet())
            values.put(COL_LAST_OPENED, set.GetDateLastOpened());
        else
            values.put(COL_LAST_OPENED, new java.util.Date().toString());

        // using replace() method to overwrite existing entry if
        long rowId = db.replace(TABLE_SETS, null, values);

        // assign newly made auto-increment id to set and return
        if(!set.IsIdSet()) {
            Cursor c = Query("SELECT * FROM " + TABLE_SETS + " LIMIT " + rowId + " OFFSET 1");
            if (c.moveToFirst()) {
                set.SetId(c.getInt(c.getColumnIndex(COL_SET_ID)));
            }
        }
        return set;
    }
    public Card InsertOrUpdateCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(card.IsIdSet()){
            values.put(COL_SET_ID, card.GetId());
        }
        values.put(COL_SET_ID, card.GetSetId());
        values.put(COL_VALUES, card.GetCardValuesAsString());
        values.put(COL_TAGS, card.GetTagsAsString());
        if(card.IsDateCreatedSet())
            values.put(COL_CREATED, card.GetDateCreated());
        else
            values.put(COL_CREATED, new java.util.Date().toString());

        // using replace() method to overwrite existing entry if
        long rowId = db.replace(TABLE_CARDS, null, values);

        // assign newly made auto-increment id to set and return
        if(!card.IsIdSet()) {
            Cursor c = Query("SELECT * FROM " + TABLE_SETS + " LIMIT " + rowId + " OFFSET 1");
            if (c.moveToFirst()) {
                card.SetId(c.getInt(c.getColumnIndex(COL_SET_ID)));
            }
        }
        return card;
    }

    // get objects
    public CardSet GetCardSet(int id){
        Cursor c = Query("SELECT * FROM " + TABLE_SETS + " WHERE " + COL_SET_ID + "=" + id + " LIMIT 1;");
        if(c.moveToFirst()){
            return GetCardSetFromCursor(c);
        }else{
            return null;
        }
    }
    public Vector<CardSet> GetAllCardSets(){
        Vector<CardSet> output = new Vector<CardSet>();
        Cursor c = Query("SELECT * FROM " + TABLE_SETS + ";");
        if(c.moveToFirst()) {
            do {
                output.add(GetCardSetFromCursor(c));
            } while (c.moveToNext());
        }
        return output;
    }
    public CardSet GetCardSetFromCursor(Cursor c){
        CardSet set = new CardSet();

        // load basic properties from database
        set.SetId(c.getInt(c.getColumnIndex(COL_SET_ID)));
        set.SetName(c.getString(c.getColumnIndex(COL_NAME)));
        set.SetDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
        set.SetTags(c.getString(c.getColumnIndex(COL_TAGS)));
        set.SetDateCreated((new java.util.Date(c.getLong(c.getColumnIndex(COL_CREATED)))).toString());
        set.SetDateModified((new java.util.Date(c.getLong(c.getColumnIndex(COL_MODIFIED)))).toString());
        set.SetDateLastOpened((new java.util.Date(c.getLong(c.getColumnIndex(COL_LAST_OPENED)))).toString());

        // load cards from another query
        set.SetCards(GetCardsBySetId(set.GetId()));

        // associate with this database and return result
        set.SetDatabase(this);
        return set;
    }

    public Vector<Card> GetCardsBySetId(int setId){
        Vector<Card> output = new Vector<Card>();
        Cursor c = Query("SELECT * FROM " + TABLE_CARDS + " WHERE " + COL_CARD_ID + "=" + setId + ";");
        if(c.moveToFirst()) {
            do {
                output.add(GetCardFromCursor(c));
            } while (c.moveToNext());
        }
        return output;
    }
    public Card GetCard(int id){
        Cursor c = Query("SELECT * FROM " + TABLE_CARDS + " WHERE " + COL_CARD_ID + "=" + id + " LIMIT 1;");
        if(c.moveToFirst()){
            return GetCardFromCursor(c);
        }else{
            return null;
        }
    }
    public Card GetCardFromCursor(Cursor c){
        Card card = new Card(c.getString(c.getColumnIndex(COL_VALUES)));

        // load basic properties from database
        card.SetId(c.getInt(c.getColumnIndex(COL_CARD_ID)));
        card.SetSetId(c.getInt(c.getColumnIndex(COL_SET_ID)));
        card.SetTags(c.getString(c.getColumnIndex(COL_TAGS)));
        card.SetDateCreated((new java.util.Date(c.getLong(c.getColumnIndex(COL_CREATED)))).toString());

        // associate with this database and return result
        card.SetDatabase(this);
        return card;
    }
}