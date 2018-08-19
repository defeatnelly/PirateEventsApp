package edu.ecu.cs.eventapp.controller;
import edu.ecu.cs.eventapp.model.User;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.PiratesEvent;

/**
 * Created by Jakayla on 9/21/2017.
 */

public class CreateEventFragment extends Fragment {

    private static final String ARG_EVENT_ID = "event_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int SELECT_PICTURE = 1;
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_TIME = 2;
    private Button mTimeButton;
    private PiratesEvent mEvent;
    private EditText mEventNameField;
    private EditText mEventLocationField;
    private Button mDateButton;
    private EditText mOrganize;
    private EditText mDescription;
    private Button mImageButton;
    private Button mCreateButton;
    private ImageView imageView;


    public static CreateEventFragment newInstance(UUID eventId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);

        CreateEventFragment fragment = new CreateEventFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID eventId = (UUID) getArguments().getSerializable(ARG_EVENT_ID);
        mEvent = EventBase.get(getActivity()).getPirateEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);

        mEventNameField = (EditText) v.findViewById(R.id.event_name);
        mEventNameField.setText(mEvent.getEventName());
        mEventNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setEventName(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mEventLocationField = (EditText) v.findViewById(R.id.event_location);
        mEventLocationField.setText(mEvent.getLocation());
        mEventLocationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setLocation(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mOrganize=(EditText) v.findViewById(R.id.event_organizer);
        mOrganize.setText(mEvent.getOrganizerName());
        mOrganize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setOrganizerName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDescription=(EditText) v.findViewById(R.id.event_description);
        mDescription.setText(mEvent.getEventdiscription());
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEvent.setEventdiscription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mDateButton = (Button) v.findViewById(R.id.event_date_button);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mEvent.getEventDate());
                dialog.setTargetFragment(CreateEventFragment.this,REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);

            }
        });

        mTimeButton = (Button) v.findViewById(R.id.event_time_button);


        mTimeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance(mEvent.getEventDate());
                dialog.setTargetFragment(CreateEventFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });
        mCreateButton=(Button) v.findViewById(R.id.create_event);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        imageView=(ImageView) v.findViewById(R.id.image_create);


        mImageButton=(Button) v.findViewById(R.id.image_chooser);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            SELECT_PICTURE);

            }
        });
        updateDate();
        updatePhoto();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if(requestCode == SELECT_PICTURE){
                Uri uri = data.getData();
                mEvent.setThumbnail(uri.toString());
                updateEvents();
                updatePhoto();


            }
            else if (requestCode == REQUEST_DATE) {
                Date date = (Date) data
                        .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mEvent.setEventDate(date);
                updateEvents();
                updateDate();
            }
            else if (requestCode == REQUEST_TIME) {
                Date date = (Date) data
                        .getSerializableExtra(TimePickerFragment.EXTRA_DATE);
                mEvent.setEventDate(date);
                updateEvents();
                updateDate();

            }
        }

    }
    private void updatePhoto(){
        if(mEvent.getThumbnail()!=null) {
            Glide.with(imageView.getContext()).load(Uri.parse(mEvent.getThumbnail()))
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
        else {
            Glide.with(imageView.getContext()).load(R.drawable.ic_user_profile)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }

    }
    private void updateEvents(){
        EventBase.get(getActivity()).updateEvents(mEvent);
    }
    private void updateDate() {

        String EventDate = DateFormat.getDateFormat(getActivity()).format(mEvent.getEventDate());
        String EventTime = DateFormat.getTimeFormat(getActivity()).format(mEvent.getEventDate());
        mDateButton.setText(EventDate);
        mTimeButton.setText(EventTime);
        }


    @Override
    public void onPause() {
        super.onPause();
        EventBase.get(getActivity()).updateEvents(mEvent);

    }
}
