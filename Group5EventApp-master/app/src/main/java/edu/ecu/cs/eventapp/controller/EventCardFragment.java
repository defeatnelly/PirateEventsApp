package edu.ecu.cs.eventapp.controller;


import edu.ecu.cs.eventapp.model.User;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.PiratesEvent;

/**
 * Created by Jakayla on 9/21/2017.
 */

public class EventCardFragment extends Fragment {
    private RecyclerView mEventRecyclyerView;
    private EventAdapter mAdapter;
    private static final String EXTRA_USER="google.user";
    User user = new User();
    private Context mContext;
    private MenuItem profile_pic;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=(User)getActivity().getIntent().getSerializableExtra(EXTRA_USER);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_card, container, false);
        mEventRecyclyerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mEventRecyclyerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_event_card,menu);
        profile_pic=menu.findItem(R.id.user_account);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_account:
                Intent intent = new Intent(getActivity(),ProfileDetailActivity.class );
                intent.putExtra(EXTRA_USER, user);
                startActivity(intent);
                return true;
            case R.id.user_events:
                Intent intent2= EventsListActivity.newIntent(getActivity(),user);
                startActivity(intent2);
                return true;
            case R.id.add_events:
                PiratesEvent event= new PiratesEvent();
                EventBase.get(getActivity()).addEvent(event);
                Intent intent1 = CreateEventActivity.newIntent(getActivity(), event.getUid());
                startActivity(intent1);

                default:return super.onOptionsItemSelected(item);
        }

    }

    private void updateUI()
    {
        EventBase eventBase = EventBase.get(getActivity());
        List<PiratesEvent> events = eventBase.getPirateEvents();


        if (mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mEventRecyclyerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAdapter(events);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private PiratesEvent mEvent;

        private TextView mEventNameTextView;
        private TextView mEventLocationTextView;
        private TextView mEventDateTextView;
        private ImageView thumbnail;


        public EventHolder(LayoutInflater inflater, ViewGroup parent)
        {

            super(inflater.inflate(R.layout.card_item_event, parent, false));
            mContext=getContext();
            itemView.setOnClickListener(this);
            mEventNameTextView = (TextView) itemView.findViewById(R.id.event_name);
            mEventLocationTextView = (TextView) itemView.findViewById(R.id.event_location);
            mEventDateTextView = (TextView) itemView.findViewById(R.id.event_date);
            thumbnail=(ImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final CharSequence[] items = {"delete", "edit","send email"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setTitle("Select The Action");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item==0)
                            {
                                EventBase.get(getActivity()).deleteEvent(mEvent);
                                updateUI();
                            }
                            else if(item==1){

                                Intent intent1 = CreateEventActivity.newIntent(getActivity(), mEvent.getUid());
                                startActivity(intent1);
                            }
                            else if(item==2){
                                List<String> emails= EventBase.get(getContext()).getEmailList(mEvent.getUid());
                                String[] array= emails.toArray(new String[emails.size()]);

                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL  , array);
                                i.putExtra(Intent.EXTRA_SUBJECT, "Reminder");
                                i.putExtra(Intent.EXTRA_TEXT   , getReminder());
                                i.setPackage("com.google.android.gm");
                                startActivity(i);


                            }



                        }
                    });
                    builder.show();
                    return true;
                }
            });
        }

        public void bind(PiratesEvent event)
        {
            mEvent = event;
            mEventNameTextView.setText(mEvent.getEventName());
            mEventLocationTextView.setText(mEvent.getLocation());
            String EventDate = DateFormat.getDateFormat(getActivity()).format(event.getEventDate());
            String EventTime = DateFormat.getTimeFormat(getActivity()).format(event.getEventDate());
            mEventDateTextView.setText("Event on "+ EventDate + " at " + EventTime);
            if(mEvent.getThumbnail()!=null) {
                Glide.with(thumbnail.getContext()).load(Uri.parse(mEvent.getThumbnail()))
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(thumbnail);
            }
            else {
                Glide.with(thumbnail.getContext()).load(R.drawable.ic_user_profile).into(thumbnail);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent1 =  EventPagerActivity.newIntent(getActivity(), mEvent.getUid(),user);
            startActivity(intent1);

        }
        public  String getReminder(){
            if (mEvent.getEventName()==null) {
                return " ";
            }
            else {
                String eventDate = DateFormat.getDateFormat(getActivity()).format(mEvent.getEventDate());
                String eventTime = DateFormat.getTimeFormat(getActivity()).format(mEvent.getEventDate());
                String msgbody = mEvent.getEventName() + " on " + eventDate + " at " + eventTime + " in " + mEvent.getLocation() + ".";
                String description = mEvent.getEventdiscription();
                String orgainzer= mEvent.getOrganizerName();
                String tag1= "Description:";
                String tag2="Thanks!";

                String reminder = String.format("%s\n\n%s\n%s\n\n%s\n%s", msgbody,tag1, description,tag2,orgainzer);

                return reminder;
            }
        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {
        private List<PiratesEvent> mEvents;
        private Context mContext;


        public EventAdapter( List<PiratesEvent> events) {
                       mEvents = events;
        }
        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            PiratesEvent event = mEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        public void setAdapter(List<PiratesEvent> events) {
            mEvents = events;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
