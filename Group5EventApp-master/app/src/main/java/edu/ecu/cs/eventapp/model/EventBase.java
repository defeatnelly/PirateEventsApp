package edu.ecu.cs.eventapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.ecu.cs.eventapp.database.EventBaseHelper;
import edu.ecu.cs.eventapp.database.EventCursorWrapper;
import edu.ecu.cs.eventapp.database.EventDbSchema;

/**
 * Created by dev on 11/6/17.
 */

public class EventBase {

    private static EventBase sEventBase;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static EventBase get(Context context){
        if (sEventBase==null){
            sEventBase=new EventBase(context);
        }
        return sEventBase;
    }
    private EventBase(Context context){
        mContext=context.getApplicationContext();
        mDatabase= new EventBaseHelper(mContext).getWritableDatabase();
    }



    public void addEvent(PiratesEvent event) {
        ContentValues values = getContentValues(event);
        mDatabase.insert(EventDbSchema.EventTable.NAME, null, values);
    }

    public void addUser(User user) {
        ContentValues values = getUserValues(user);
        mDatabase.insert(EventDbSchema.UserTable.NAME, null, values);
        }

    public void deleteEvent(PiratesEvent event)
    {
        String uuidString = event.getUid().toString();

        mDatabase.delete(EventDbSchema.EventTable.NAME,
                EventDbSchema.EventTable.ColsEvent.UUID + " = ?",
                new String[] { uuidString });
    }

    public List<PiratesEvent> getPirateEvents() {
        List<PiratesEvent > Events = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Events;
    }


    public PiratesEvent getPirateEvent(UUID evenid) {
        EventCursorWrapper cursor = queryEvents(
                EventDbSchema.EventTable.ColsEvent.UUID + " = ?",
                new String[]{evenid.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }
    public void updateEvents(PiratesEvent Event) {
        String uuidString = Event.getUid().toString();
        ContentValues values = getContentValues(Event);
        mDatabase.update(EventDbSchema.EventTable.NAME, values,
                EventDbSchema.EventTable.ColsEvent.UUID + " = ?",
                new String[]{uuidString});
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EventDbSchema.EventTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new EventCursorWrapper(cursor);
    }
   private EventCursorWrapper queryUsers(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                EventDbSchema.UserTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new EventCursorWrapper(cursor);
    }

    public List<PiratesEvent> getUserEvents(String userid) {
        List<PiratesEvent > Events = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(EventDbSchema.EventTable.ColsEvent.USERID + "=?", new String[]{userid});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Events.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return Events;
    }

    public List<String> getEmailList(UUID uuid) {
        List<String> emails = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(EventDbSchema.EventTable.ColsEvent.UUID + "=?", new String[]{uuid.toString()});
        try {

                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    emails.add(cursor.getEvent().getUserEmail());
                    cursor.moveToNext();
                }
            }
            finally{
                cursor.close();
            }
            return emails;


    }

    public void updateEvents(PiratesEvent Event,User user) {
        String uuidString = Event.getUid().toString();
        String userid =user.getmUserID();
        String email=user.getmEmail();
        ContentValues values = getContentValues(Event,userid,email);
        mDatabase.update(EventDbSchema.EventTable.NAME, values,
                EventDbSchema.EventTable.ColsEvent.UUID + " = ? ",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(PiratesEvent event) {
        ContentValues values = new ContentValues();
        values.put(EventDbSchema.EventTable.ColsEvent.UUID, event.getUid().toString());
        values.put(EventDbSchema.EventTable.ColsEvent.TITLE,event.getEventName());
        values.put(EventDbSchema.EventTable.ColsEvent.DESCRIPTION, event.getEventdiscription());
        values.put(EventDbSchema.EventTable.ColsEvent.DATE, event.getEventDate().getTime());
        values.put(EventDbSchema.EventTable.ColsEvent.ORGANIZER, event.getOrganizerName());
        values.put(EventDbSchema.EventTable.ColsEvent.LOCATION, event.getLocation());
        values.put(EventDbSchema.EventTable.ColsEvent.EVENT_IMAGE,event.getThumbnail());
        values.put(EventDbSchema.EventTable.ColsEvent.USER_EMAIL,event.getUserEmail());

        return values;
    }

    private static ContentValues getContentValues(PiratesEvent event,String userid,String email) {
        ContentValues values = new ContentValues();
        values.put(EventDbSchema.EventTable.ColsEvent.UUID, event.getUid().toString());
        values.put(EventDbSchema.EventTable.ColsEvent.TITLE,event.getEventName());
        values.put(EventDbSchema.EventTable.ColsEvent.DESCRIPTION, event.getEventdiscription());
        values.put(EventDbSchema.EventTable.ColsEvent.DATE, event.getEventDate().getTime());
        values.put(EventDbSchema.EventTable.ColsEvent.ORGANIZER, event.getOrganizerName());
        values.put(EventDbSchema.EventTable.ColsEvent.LOCATION, event.getLocation());
        values.put(EventDbSchema.EventTable.ColsEvent.USERID,userid);
        values.put(EventDbSchema.EventTable.ColsEvent.EVENT_IMAGE,event.getThumbnail());
        values.put(EventDbSchema.EventTable.ColsEvent.USER_EMAIL,email);

        return values;
    }

    private static ContentValues getUserValues(User user){
        ContentValues values = new ContentValues();
        values.put(EventDbSchema.UserTable.ColsUser.USERID,user.getmUserID());
        values.put(EventDbSchema.UserTable.ColsUser.EMAIL,user.getmEmail());
        values.put(EventDbSchema.UserTable.ColsUser.USERNAME,user.getmUsername());
        values.put(EventDbSchema.UserTable.ColsUser.PHOTOURI,user.getPhotoUri());
        values.put(EventDbSchema.UserTable.ColsUser.EVENTID,user.getUserEventID());
        return values;
    }


}
