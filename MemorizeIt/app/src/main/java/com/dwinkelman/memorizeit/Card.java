package com.dwinkelman.memorizeit;

/**
 * Created by Daniel on 1/29/2017.
 */

public class Card {
    /**
     * Negative ids indicate terms not in the database
     */
    private long id;
    private String[] values;

    public Card(long id, String[] values){

    }

    // dynamic accessors
    public long getId(){
        return this.id;
    }
    public String[] getValues(){
        return this.values;
    }
    public String getValue(int index){
        return this.values[index];
    }
    public int nValues(){
        return this.values.length;
    }

    // dynamic assigners
    public void setValues(String[] values){

    }
    public void setValue(int index, String value){

    }
}
