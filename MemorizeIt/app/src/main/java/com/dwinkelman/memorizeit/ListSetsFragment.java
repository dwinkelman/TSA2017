package com.dwinkelman.memorizeit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListSetsFragment extends ListFragment {
    // parameter names
    private static final String ARG_SET_ARRAY = "sets";

    // stored values
    private Set[] sets;

    // callback object
    Callbacks callbackObj;

    public ListSetsFragment() {
        // Required empty public constructor
    }

    public static ListSetsFragment newInstance(Set[] sets) {
        ListSetsFragment fragment = new ListSetsFragment();
        Bundle args = new Bundle();
        // adding set as an array to avoid having to use parceable
        args.putSerializable(ARG_SET_ARRAY, sets);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set flashcard values
        if (getArguments() != null) {
            Set[] data = (Set[])(getArguments().getSerializable(ARG_SET_ARRAY));
            if(data != null) {
                sets = data;
            }else{
                sets = new Set[] {new Set()};
            }
        } else {
            sets = new Set[] {new Set()};
        }

        // set up the array adapter
        attachArrayAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_sets, container, false);

        // add event listener to listview
        ListView listView = (ListView)view.findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Set set = sets[position];
                callbackObj.onListSetsItemClick(set);
                System.exit(1);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbackObj = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ListSetsFragments.Callbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Set up the array adapter so that it sends values into the list designated as android.R.id.list.
     */
    private void attachArrayAdapter(){
        final ArrayAdapters.ListSetsAdapter adapter = new ArrayAdapters.ListSetsAdapter(getActivity(), sets);
        setListAdapter(adapter);
    }

    public interface Callbacks {
        void onListSetsItemClick(Set set);
    }
}
