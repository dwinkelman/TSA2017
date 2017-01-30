package com.dwinkelman.memorizeit;

/**
 * Created by Daniel on 1/29/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Class for general purpose database access.
 * DO NOT CHANGE ANY NAMES in this class (DATABASE_NAME, COL_SETID, etc.) since it will cause
 * installed databases to get out of sync with the program.
 */
public class SetsDatabase extends SQLiteOpenHelper {
    // !!!TODO: DO NOT TOUCH ANY NAMES IN THIS CLASS!!!
    private static final String DATABASE_NAME = "MemorizeItSets";
    private static final int DATABASE_VERSION = 1;

    // table and column SQLite names
    public static final String
            TABLE_SETS = "sets",
            TABLE_CARDS = "cards",
            COL_SETID = "setid",
            COL_CARDID = "cardid",
            COL_TITLE = "title",
            COL_DESCRIPTION = "description",
            COL_CREATED = "created",
            COL_OPENED = "opened",
            COL_EDITED = "edited",
            COL_VALUES = "values";

    // create table queries
    private static final String QUERY_CREATE_SET_TABLE =
            "CREATE TABLE " + TABLE_SETS + "("
            + COL_SETID + " INTEGER PRIMARY KEY,"
            + COL_TITLE + " TEXT,"
            + COL_DESCRIPTION + " TEXT,"
            + COL_CREATED + " INTEGER,"
            + COL_OPENED + " INTEGER,"
            + COL_EDITED + " INTEGER);";
    private static final String QUERY_CREATE_CARDS_TABLE =
            "CREATE TABLE " + TABLE_CARDS + "("
            + COL_CARDID + " INTEGER PRIMARY KEY,"
            + COL_SETID + " INTEGER,"
            + COL_VALUES + " TEXT);";

    public SetsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_SET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long addSetRow(String title, String description, Date created, Date opened, Date edited){
        SQLiteDatabase db = getWritableDatabase();
        /*String query =
                "INSERT INTO " + TABLE_SETS + " (" + COL_TITLE + "," + COL_DESCRIPTION + "," + COL_CREATED + "," + COL_OPENED + "," + COL_EDITED + ")"
                + " VALUES (" + title + "," + description + "," + encodeDate(created) + "," + encodeDate(opened) + "," + encodeDate(edited) + ");";*/
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_CREATED, encodeDate(created));
        values.put(COL_OPENED, encodeDate(opened));
        values.put(COL_EDITED, encodeDate(edited));
        return db.replace(TABLE_SETS, null, values);
    }
    public long addCardRow(long setid, String[] values){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL_SETID, setid);
        content.put(COL_VALUES, encodeCard(values));
        return db.replace(TABLE_CARDS, null, content);
    }
    public void removeSetRow(long id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_SETS + " WHERE " + COL_SETID + "=" + id + ";";
        db.execSQL(query);
    }
    public void removeCardRow(long id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_CARDS + " WHERE " + COL_CARDID + "=" + id + ";";
        db.execSQL(query);
    }
    public void updateValue(String table, String selectorCol, String selectorValue, String updateCol, String newValue){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + table + " SET " + updateCol + "=`" + newValue
                + "` WHERE " + selectorCol + "=`" + selectorValue + "`;";
        db.execSQL(query);
    }
    public void getValue(String table, String selectorCol, String selectorValue, String selectCol){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + selectCol + " FROM " + table
                + " WHERE " + selectorCol + "=`" + selectorValue + "`;";
        db.execSQL(query);
    }


    public static String encodeCard(String[] card){
        return TextUtils.join("|", card);
    }
    public static String[] decodeCard(String encoded){
        return TextUtils.split("|", encoded);
    }
    public static long encodeDate(Date date){
        return date.getTime() / 1000;
    }
    public static Date decodeDate(long encoded){
        return new Date(encoded * 1000);
    }
}
