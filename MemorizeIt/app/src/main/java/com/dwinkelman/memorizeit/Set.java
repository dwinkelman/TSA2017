package com.dwinkelman.memorizeit;

/**
 * Created by Daniel on 1/20/2017.
 */

import android.content.Context;

import java.io.Serializable;
import java.util.Date;

/**
 * An interface with the SQLite database on the device to make information synchronization and
 * access easier.
 */
public class Set implements Serializable {
    // default values
    private String DEFAULT_TITLE = "My Set";
    private String DEFAULT_DESCRIPTION = "My new set that I just created.";
    private Card[] DEFAULT_VALUES = {
            new Card(-1, new String[] {"pelota", "ball"}),
            new Card(-1, new String[] {"amenazar", "to threaten"}),
            new Card(-1, new String[] {"destacarse", "to stand out"}),
            new Card(-1, new String[] {"bañarse", "ducharse", "to shower"}),
            new Card(-1, new String[] {"autobús", "bus"}),
            new Card(-1, new String[] {"chocar", "to crash"})
    };
    private Card[] EMPTY_VALUES = {};

    // data storage
    // id is the only value statically stored
    private long id;
    private String title;
    private String description;
    private Date created;
    private Card[] values;

    // database interfacing
    private Context context;
    private SetsDatabase db;

    /**
     * Constructor that sets all class variables to their default values.
     */
    private void defaultConstructor(){
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        values = EMPTY_VALUES;
        created = new Date();
        id = -1;
    }

    /**
     * Constructor that uses dynamic accessors to set class variables.
     */
    private void accessorConstructor(){

    }

    // constructors
    public Set(){
        defaultConstructor();
    }

    public Set(Context context){
        this.context = context;
        this.db = new SetsDatabase(context);
    }

    // dynamic accessors
    public long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Date getCreated(){
        return created;
    }
    public Card[] getCards(){
        return values;
    }
    public Card getCard(int index){
        return values[index];
    }
    public String getValue(int cardIndex, int valueIndex){
        return values[valueIndex].getValue(valueIndex);
    }
    public int nTerms(){
        return values.length;
    }

    // dynamic assigners
    public void setTitle(String title){

    }
    public void setDescription(String description){

    }
    public void setCreated(Date createdDate){

    }
    public void setValues(String[][] values){

    }
    public void setCard(String[] values){

    }
    public void setValue(String value) {

    }
}
