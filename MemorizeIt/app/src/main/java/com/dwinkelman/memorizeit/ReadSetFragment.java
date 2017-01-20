package com.dwinkelman.memorizeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link ReadSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadSetFragment extends ListFragment {
    // parameter names
    private static final String ARG_SET_OBJECT = "set";

    // stored values
    Set set;

    public ReadSetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param set The set to create the fragment from.
     * @return A new instance of fragment ReadSetFragment.
     */
    public static ReadSetFragment newInstance(Set set) {
        ReadSetFragment fragment = new ReadSetFragment();
        Bundle args = new Bundle();
        // adding set as an array to avoid having to use parceable
        args.putSerializable(ARG_SET_OBJECT, new Set[] {set});
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set flashcard values
        if (getArguments() != null) {
            Set[] data = (Set[])(getArguments().getSerializable(ARG_SET_OBJECT));
            if(data != null) {
                if (data.length > 0) {
                    set = data[0];
                } else {
                    set = new Set();
                }
            }else{
                set = new Set();
            }
        } else {
            set = new Set();
        }

        // set up the array adapter
        attachArrayAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_read_set, container, false);

        // adjust text
        setPropertyText(inflatedView);

        return inflatedView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Set up the array adapter so that it sends values into the list designated as android.R.id.list.
     */
    private void attachArrayAdapter(){
        final ArrayAdapters.ReadSetAdapter adapter = new ArrayAdapters.ReadSetAdapter(getActivity(), set.getValues());
        setListAdapter(adapter);
    }

    /**
     * Set text in the fragment like title, description, details, etc.
     */
    private void setPropertyText(View view){
        // get text elements from the layout
        TextView title = (TextView)view.findViewById(R.id.read_set_title);
        TextView description = (TextView)view.findViewById(R.id.read_set_description);
        TextView details = (TextView)view.findViewById(R.id.read_set_details);

        // set text attributes to elements
        title.setText(set.getTitle());
        description.setText(set.getDescription());
        // assemble string for details
        String d = getString(R.string.read_set_created) + " " + Util.formatDate(set.getCreated()) + "  |  " +
                set.nTerms() + " " + getString(R.string.read_set_nterms);
        details.setText(d);
    }
}
