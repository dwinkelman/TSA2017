package com.dwinkelman.memorizeit;

import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ListSetsFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public void onListSetsItemClick(Set set){

    }
}
