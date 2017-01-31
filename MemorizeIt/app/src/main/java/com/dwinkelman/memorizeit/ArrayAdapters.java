package com.dwinkelman.memorizeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Daniel on 1/14/2017.
 */

public class ArrayAdapters {
    public static class ReadSetAdapter extends ArrayAdapter<String[]> {

        private Context context;
        private String[][] values;

        // default layout for the TextView containing an individual value
        private LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        public ReadSetAdapter(Context context, String[][] values){
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent){
            // set up layout template
            if(rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.snippet_readset_card, parent, false);
            }
            LinearLayout left = (LinearLayout)rowView.findViewById(R.id.readset_card_col_left);
            LinearLayout right = (LinearLayout)rowView.findViewById(R.id.readset_card_col_right);

            // fetch terms to display
            String[] term = values[position];

            for(int i = 0; i < term.length; i++){
                // make a new text view
                TextView v = new TextView(context);
                v.setText(term[i]);
                v.setLayoutParams(textViewLayout);
                v.setTextSize(16.0f);

                // append view to appropriate column
                if(i % 2 == 0) left.addView(v);
                else right.addView(v);
            }
            return rowView;
        }
    }

    public static class ListSetsAdapter extends ArrayAdapter<Set> {

        private Context context;
        private Set[] sets;

        public ListSetsAdapter(Context context, Set[] sets){
            super(context, -1, sets);
            this.context = context;
            this.sets = sets;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent){
            // set up layout template
            if(rowView == null){
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.snippet_listset_item, parent, false);
            }

            // fetch values to display
            Set set = sets[position];
            TextView title = (TextView)rowView.findViewById(R.id.listset_item_title);
            TextView description = (TextView)rowView.findViewById(R.id.listset_item_description);
            TextView details = (TextView)rowView.findViewById(R.id.listset_item_details);

            // set text values
            title.setText(set.getTitle());
            description.setText(set.getDescription());
            String d = context.getString(R.string.read_set_created) + " " + Util.formatDate(set.getCreated()) + "\n" +
                    set.nTerms() + " " + context.getString(R.string.read_set_nterms);
            details.setText(d);

            return rowView;
        }
    }
}
