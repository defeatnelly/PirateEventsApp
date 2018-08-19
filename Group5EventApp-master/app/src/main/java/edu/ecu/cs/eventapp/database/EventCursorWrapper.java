package edu.ecu.cs.eventapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.ecu.cs.eventapp.database.EventDbSchema.EventTable;
import edu.ecu.cs.eventapp.model.PiratesEvent;

import edu.ecu.cs.eventapp.model.User;

/**
 * Created by dev on 11/6/17.
 */

public class EventCursorWrapper extends CursorWrapper {
    public EventCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public PiratesEvent getEvent() {
        String uuidString = getString(getColumnIndex(EventTable.ColsEvent.UUID));
        String title=getString(getColumnIndex(EventTable.ColsEvent.TITLE));
        String description = getString(getColumnIndex(EventTable.ColsEvent.DESCRIPTION));
        long date = getLong(getColumnIndex(EventTable.ColsEvent.DATE));
        String organizer = getString(getColumnIndex(EventTable.ColsEvent.ORGANIZER));
        String location = getString(getColumnIndex(EventTable.ColsEvent.LOCATION));
        String userid=getString(getColumnIndex(EventTable.ColsEvent.USERID));
        String image=getString(getColumnIndex(EventTable.ColsEvent.EVENT_IMAGE));
        String email=getString(getColumnIndex(EventTable.ColsEvent.USER_EMAIL));

        PiratesEvent event = new PiratesEvent(UUID.fromString(uuidString));
        event.setEventName(title);

        event.setEventDate(new Date(date));
        event.setEventdiscription(description);
        event.setOrganizerName(organizer);
        event.setLocation(location);
        event.setThumbnail(image);
        event.setUserID(userid);
        event.setUserEmail(email);
        return event;
    }
    public User getUser(){
        String userid=getString(getColumnIndex(EventDbSchema.UserTable.ColsUser.USERID));
        String username=getString(getColumnIndex(EventDbSchema.UserTable.ColsUser.USERNAME));
        String email=getString(getColumnIndex(EventDbSchema.UserTable.ColsUser.EMAIL));
        String photouri=getString(getColumnIndex(EventDbSchema.UserTable.ColsUser.PHOTOURI));
        String eventid = getString(getColumnIndex(EventDbSchema.UserTable.ColsUser.EVENTID));

        User user =new User(userid);
        user.setmUsername(username);
        user.setmEmail(email);
        user.setPhotoUri(photouri);
        user.setUserEventID(eventid);
        return user;

    }

}
