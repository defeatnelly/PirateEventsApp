package edu.ecu.cs.eventapp.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener{


    private GoogleApiClient googleApiClient;
    private final String TAG = "LoginActivity";
    private static final int REQ_CODE = 9001;
    private SignInButton mSignInButton;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private static final String EXTRA_USER="google.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi
                (Auth.GOOGLE_SIGN_IN_API, gso).build();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, REQ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if(result.isSuccess()) {
            // signed in successfully
            GoogleSignInAccount account = result.getSignInAccount();
            String name=account.getGivenName();
            String email=account.getEmail();
           String photo= account.getPhotoUrl().toString();



            User user = new User();
            user.setmEmail(email);
            user.setmUsername(name);
            user.setPhotoUri(photo);
            user.setmUserID(account.getId());
            EventBase.get(getApplicationContext()).addUser(user);

            //Saving the user google login information to firebase database
            mDatabase= FirebaseDatabase.getInstance();
            mRef=mDatabase.getReference().child("Users").child(account.getId().toString());
            mRef.child("Email").setValue(account.getEmail().toString());
            mRef.child("Name").setValue((account.getDisplayName()));
            mRef.child("PhotoUri").setValue(account.getPhotoUrl().toString());

            Intent intent = new Intent(this,EventCardActivity.class );
            intent.putExtra(EXTRA_USER, user);

            startActivity(intent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Failed to login " + result.getStatus(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


}

