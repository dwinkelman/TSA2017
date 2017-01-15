package com.dwinkelman.memorizeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Daniel on 1/14/2017.
 */

public class Adapters {
    public class ReadSetAdapter extends ArrayAdapter<String[]> {

        private final Context context;
        private final String[][] values;

        public ReadSetAdapter(Context context, String[][] values){
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }
    }
}
