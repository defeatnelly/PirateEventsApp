package edu.ecu.cs.eventapp.controller;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.UUID;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.PiratesEvent;
import edu.ecu.cs.eventapp.model.User;

/**
 * Created by dev on 11/8/17.
 */

public class JoinEventFragment extends Fragment {
    private TextView eventName;
    private TextView eventLocation;
    private TextView eventDescription;
    private TextView  eventDate;
    private ImageView imageView;
    private TextView eventTime;
    private PiratesEvent event;
    private static final String ARG_EVENT_ID = "event_id";
    private Button JoinButton;
    private User user;
    private static final String EXTRA_USER="google.user";
    private Button CancelButton;

    public static JoinEventFragment newInstance(UUID eventId,User user){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);
        args.putSerializable(EXTRA_USER,user);
        JoinEventFragment fragment = new JoinEventFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID) getArguments().getSerializable(ARG_EVENT_ID);
        user=(User) getArguments().getSerializable(EXTRA_USER);
        event = EventBase.get(getActivity()).getPirateEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_fragment_join,container,false);
        eventDate=(TextView)view.findViewById(R.id.event_date_join);
        eventTime=(TextView) view.findViewById(R.id.event_time_join);
        eventLocation=(TextView)view.findViewById(R.id.event_location_join);
        eventDescription=(TextView)view.findViewById(R.id.event_description_join);
        eventDescription.setText(event.getEventdiscription());
        eventName=(TextView)view.findViewById(R.id.event_name_join);
        eventName.setText(event.getEventName());
        eventLocation.setText(event.getLocation());
        updateDate();
        JoinButton=(Button) view.findViewById(R.id.join_event);

        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBase.get(getActivity()).updateEvents(event,user);
                Toast.makeText(getContext(),R.string.join_sucessfull,Toast.LENGTH_LONG).show();
                JoinButton.setEnabled(false);
                getActivity().finish();
            }
        });
        if(event.getUserID()!= null){
            JoinButton.setEnabled(false);
        }

        CancelButton= (Button) view.findViewById(R.id.cancel_event);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setmUserID(null);
                user.setmEmail(null);
                EventBase.get(getActivity()).updateEvents(event,user);
                JoinButton.setEnabled(true);
            }
        });
        imageView=(ImageView) view.findViewById(R.id.image_event);
        if(event.getThumbnail()!=null) {
            Glide.with(getActivity()).load(Uri.parse(event.getThumbnail()))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

        return view;
    }

    private void updateDate() {

        String EventDate = DateFormat.getDateFormat(getActivity()).format(event.getEventDate());
        String EventTime = DateFormat.getTimeFormat(getActivity()).format(event.getEventDate());
        eventDate.setText(EventDate);
        eventTime.setText(EventTime);
    }
}
