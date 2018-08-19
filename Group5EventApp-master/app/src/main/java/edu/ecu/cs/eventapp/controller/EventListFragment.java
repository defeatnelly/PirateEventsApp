package edu.ecu.cs.eventapp.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import edu.ecu.cs.eventapp.R;
import edu.ecu.cs.eventapp.model.EventBase;
import edu.ecu.cs.eventapp.model.PiratesEvent;
import edu.ecu.cs.eventapp.model.User;

/**
 * Created by dev on 11/24/17.
 */

public class EventListFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private static final String EXTRA_USER="google.user";
    private User user;

    public static EventListFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER,user);
        EventListFragment fragment = new EventListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_event_list,container,false);
        user=(User) getArguments().getSerializable(EXTRA_USER);
        recyclerView=(RecyclerView) v.findViewById(R.id.user_event_reycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }
    public void updateUI()

    {
        List<PiratesEvent> piratesEvents;
        piratesEvents = EventBase.get(getActivity()).getUserEvents(user.getmUserID());
        if (mAdapter == null) {
            mAdapter = new EventAdapter(piratesEvents);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAdapter(piratesEvents);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class EventListHolder extends RecyclerView.ViewHolder{
        private TextView EventName;
        private TextView EventDateView;
        private PiratesEvent Event;
        private TextView organizer;

        public EventListHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.item_event_list,parent,false));
            EventName=(TextView) itemView.findViewById(R.id.event_name_list);
            EventDateView=(TextView) itemView.findViewById(R.id.event_date_list);
            organizer=(TextView) itemView.findViewById(R.id.event_organizer_list);
        }
        public void bind (PiratesEvent event){
            Event=event;
            EventName.setText(Event.getEventName());
            String EventDate = DateFormat.getDateFormat(getActivity()).format(Event.getEventDate());
            String EventTime = DateFormat.getTimeFormat(getActivity()).format(Event.getEventDate());
            EventDateView.setText("Event on "+ EventDate + " at " + EventTime);
            organizer.setText(Event.getOrganizerName());

        }

    }
    private class EventAdapter extends RecyclerView.Adapter<EventListHolder>{
        List<PiratesEvent> mevents;
        private EventAdapter(List<PiratesEvent> events){
            mevents=events;
        }

        @Override
        public EventListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            return new EventListHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(EventListHolder holder, int position) {

            PiratesEvent event=mevents.get(position);
            holder.bind(event);

        }

        @Override
        public int getItemCount() {
            return mevents.size();
        }
        public void setAdapter(List<PiratesEvent> events){
            mevents=events;
        }
    }
}
