package edu.ecu.cs.eventapp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import edu.ecu.cs.eventapp.R;

/**
 * Created by Jakayla on 9/21/2017.
 */

public class EventCardActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EventCardFragment();
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.exit_message);
        builder.setPositiveButton(R.string.stay, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        builder.show();
    }

}
