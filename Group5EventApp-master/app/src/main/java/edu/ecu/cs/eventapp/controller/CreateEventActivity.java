package edu.ecu.cs.eventapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


import java.util.UUID;

/**
 * Created by dev on 11/8/17.
 */

public class CreateEventActivity extends SingleFragmentActivity {
    UUID eventid;
    private static final String EXTRA_EVENT_ID = "edu.ecu.cs.eventapp.event_id";

    public static Intent newIntent(Context context, UUID eventId){

            Intent intent = new Intent(context, CreateEventActivity.class);
            intent.putExtra(EXTRA_EVENT_ID, eventId);
            return intent;
        }


    @Override
    protected Fragment createFragment() {
        eventid=(UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        return  CreateEventFragment.newInstance(eventid);

    }

}
