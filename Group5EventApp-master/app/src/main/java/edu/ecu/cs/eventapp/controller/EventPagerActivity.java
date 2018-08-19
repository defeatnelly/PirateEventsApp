package edu.ecu.cs.eventapp.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.PiratesEvent;
import edu.ecu.cs.eventapp.model.User;

/**
 * Created by Jakayla on 10/24/2017.
 */

public class EventPagerActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_ID = "edu.ecu.cs.eventapp.event_id";
    private static final String EXTRA_USER="google.user";
    private ViewPager mViewPager;
    private List<PiratesEvent> mEvents;
    private User user;
    public static Intent newIntent(Context packageContext, UUID eventId,User user) {
        Intent intent = new Intent(packageContext, EventPagerActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
        user=(User) getIntent().getSerializableExtra(EXTRA_USER);

        mViewPager = (ViewPager) findViewById(R.id.event_view_pager);

        mEvents = EventBase.get(this).getPirateEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                PiratesEvent event = mEvents.get(position);
                return JoinEventFragment.newInstance(event.getUid(),user);
            }

            @Override
            public int getCount() {
                return mEvents.size();
            }
        });

        for(int i = 0; i < mEvents.size(); i++){
            if(mEvents.get(i).getUid().equals(eventId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
