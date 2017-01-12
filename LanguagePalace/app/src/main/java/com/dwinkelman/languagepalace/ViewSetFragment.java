package com.dwinkelman.languagepalace;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.*;


// TODO: Populate ListView
// TODO: Finish fragment scripts
// Useful tutorial on ListViews in Fragments:
// https://developer.android.com/reference/android/app/Fragment.html#Layout
/**
 * A fragment for viewing a set.
 */
public class ViewSetFragment extends ListFragment {
    private CardSet set;
    private ViewSetEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_set,
                container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get parameters passed earlier
        Bundle bundle = getArguments();
        if (bundle != null) {
            // get card set loaded to local variables
            CardSet cs = bundle.getParcelable("cardSet");
            SetCardSet(cs);
        }

        // set element values in page
        View view = getView();
        CardSet cs = GetCardSet();
        TextView title = (TextView)view.findViewById(R.id.view_set_title);
        title.setText(cs.GetName());
        TextView number_terms = (TextView)view.findViewById(R.id.view_set_number_of_items);
        String cards = cs.GetCards().size() + " Cards";
        title.setText(cards);
        TextView created = (TextView)view.findViewById(R.id.view_set_created);
        created.setText(cs.GetDateCreated());
        TextView last_opened = (TextView)view.findViewById(R.id.view_set_last_viewed);
        last_opened.setText(cs.GetDateLastOpened());
        TextView tags = (TextView)view.findViewById(R.id.view_set_tags);
        tags.setText(TextUtils.join(" | ", cs.GetTagsAsVector()));

        // use adapter to parse information
        setListAdapter(new CardArrayAdapter(getActivity(), R.layout.layout_card_listview, GetCardSet().GetCards()));
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id){

    }

    // event listener
    // TODO: reference http://www.vogella.com/tutorials/AndroidFragments/article.html#application-communication-with-fragments
    // when implementing activity communication
    public interface ViewSetEventListener {

    }

    // getters
    public CardSet GetCardSet(){
        return this.set;
    }

    // setters
    public void SetCardSet(CardSet set){
        this.set = set;
    }

    private class CardArrayAdapter extends ArrayAdapter<Card> {
        HashMap<Card, Integer> idMap = new HashMap<Card, Integer>();

        CardArrayAdapter(Context context, int textViewResourceId, List<Card> cards){
            super(context, textViewResourceId, cards);
            for(int i = 0; i < cards.size(); ++i){
                idMap.put(cards.get(i), i);
            }
        }

        @Override
        public long getItemId(int index){
            return idMap.get(getItem(index));
        }

        @Override
        public boolean hasStableIds(){
            return true;
        }
    }
}
