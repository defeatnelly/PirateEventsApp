package edu.ecu.cs.eventapp.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import edu.ecu.cs.eventapp.model.User;

/**
 * Created by dev on 11/24/17.
 */

public class EventsListActivity extends SingleFragmentActivity{
    private static final String EXTRA_USER="google.user";
    @Override
    protected Fragment createFragment() {
        return EventListFragment.newInstance((User) getIntent().getSerializableExtra(EXTRA_USER));
    }

    public static Intent newIntent(Context context, User user) {
        Intent intent =new Intent(context,EventsListActivity.class);
        intent.putExtra(EXTRA_USER,user);
        return intent;
    }
}
