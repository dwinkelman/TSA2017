package com.dwinkelman.memorizeit;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements ListSetsFragment.ListSetsListeners {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "The app worked.");

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            // add a ReadSet Fragment
            /*ReadSetFragment readSetFragment = new ReadSetFragment();
            transaction.add(R.id.fragment_container, readSetFragment).commit();*/

            // add a ListSets Fragment
            Set[] sets = new Set[] {new Set()};
            ListSetsFragment listSetsFragment = ListSetsFragment.newInstance(sets);
            transaction.add(R.id.fragment_container, listSetsFragment);

            transaction.commit();
        }
    }

    @Override
    public void gotoReadSet(Set set) {
        // make a new fragment
        ReadSetFragment readSetFragment = ReadSetFragment.newInstance(set);

        // replace the old fragment with the new one
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, readSetFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
