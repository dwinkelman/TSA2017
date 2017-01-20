package com.dwinkelman.memorizeit;

/**
 * Created by Daniel on 1/20/2017.
 */

import java.util.Date;

/**
 * Probably will inherit directly from a SQLite row for simplicity.
 * The methods currently implemented are only placeholders. They wil be replaced with legitimate
 * methods once SQLite is implemented. For now, they are defined for compatibility with the display.
 */
public class Set {
    String DEFAULT_TITLE = "My Set";
    String DEFAULT_DESCRIPTION = "My new set that I just created.";
    String[][] DEFAULT_VALUES = {
            {"destacarse", "to stand out"},
            {"amenazar", "to threaten"},
            {"el rostro", "face"},
            {"recientemente", "recently"},
            {"la pelota", "ball"}
    };

    String title;
    String description;
    Date created;
    String[][] values;

    public Set(){
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        values = DEFAULT_VALUES;
        created = new Date();
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
    public String[][] getValues(){
        return values;
    }
    public String[] getCard(int index){
        return values[index];
    }
    public String getValue(int cardIndex, int valueIndex){
        return values[cardIndex][valueIndex];
    }
    public int nTerms(){
        return values.length;
    }
}
