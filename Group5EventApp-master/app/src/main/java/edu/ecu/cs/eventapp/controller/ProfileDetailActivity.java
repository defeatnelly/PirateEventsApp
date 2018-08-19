package edu.ecu.cs.eventapp.controller;

import android.support.v4.app.Fragment;

/**
 * Created by dev on 11/5/17.
 */

public class ProfileDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new  ProfileDetailFragment();
    }
}
