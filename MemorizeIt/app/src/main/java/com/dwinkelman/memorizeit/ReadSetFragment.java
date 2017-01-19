package com.dwinkelman.memorizeit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadSetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReadSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadSetFragment extends ListFragment {
    // parameter names
    private static final String ARG_VALUES_LIST = "values_list";

    // stored values
    private String[][] values;

    // default values
    private String[][] DEFAULT_VALUES = {
            {"destacarse", "to stand out"},
            {"amenazar", "to threaten"},
            {"el rostro", "face"},
            {"recientemente", "recently"},
            {"la pelota", "ball"}
    };

    private OnFragmentInteractionListener mListener;

    public ReadSetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param values_list 2D of key-value pairs.
     * @return A new instance of fragment ReadSetFragment.
     */
    public static ReadSetFragment newInstance(String[][] values_list) {
        ReadSetFragment fragment = new ReadSetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VALUES_LIST, values_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            values = (String[][])getArguments().getSerializable(ARG_VALUES_LIST);
        }else{
            values = DEFAULT_VALUES;
        }

        // use array adapter to instantiate items
        ListView listView = (ListView) getView().findViewById(R.id.read_set_fragment);
        final ArrayList<String[]> list = new ArrayList<String[]>();
        for (String[] val : values) {
            list.add(val);
        }
        final Adapters.ReadSetAdapter adapter = new Adapters.ReadSetAdapter(getActivity(), values);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
