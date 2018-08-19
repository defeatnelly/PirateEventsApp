package edu.ecu.cs.eventapp.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.User;

/**
 * Created by dev on 11/5/17.
 */

public class ProfileDetailFragment extends Fragment{

    private TextView userName;
    private TextView email;
    private static final String EXTRA_USER="google.user";
    private ImageView profile_picuture;

    User user=new User();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      user =(User) getActivity().getIntent().getSerializableExtra(EXTRA_USER);
    }

   

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.activity_profile_detail,container,false);

        userName=(TextView) view.findViewById(R.id.user_name);

        userName.setText(user.getmUsername());

        email=(TextView)view.findViewById(R.id.email_id);
        email.setText(user.getmEmail());
        profile_picuture=(ImageView)view.findViewById(R.id.profile_image);

        Glide.with(getActivity().getApplicationContext()).load(user.getPhotoUri())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_picuture);
        return view;
    }
}
