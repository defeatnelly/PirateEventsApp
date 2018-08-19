package edu.ecu.cs.eventapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.ecu.cs.eventapp.database.EventDbSchema.EventTable;

/**
 * Created by dev on 11/6/17.
 */

public class EventBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "pirateBase.db";
    public EventBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + EventTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                EventTable.ColsEvent.UUID + ", " +
                EventTable.ColsEvent.TITLE + ", " +
                EventTable.ColsEvent.DATE + ", " +
                EventTable.ColsEvent.DESCRIPTION + ", " +
                EventTable.ColsEvent.LOCATION + ","
                + EventTable.ColsEvent.ORGANIZER + "," +
                        EventTable.ColsEvent.USERID + "," +
                        EventTable.ColsEvent.EVENT_IMAGE+"," +
                EventTable.ColsEvent.USER_EMAIL+
                ")"
        );

       db.execSQL("create table " + EventDbSchema.UserTable.NAME + "(" +"_id integer primary key autoincrement," +
                EventDbSchema.UserTable.ColsUser.USERID  + "," +
                EventDbSchema.UserTable.ColsUser.USERNAME + "," +
                EventDbSchema.UserTable.ColsUser.EMAIL +"," +
                EventDbSchema.UserTable.ColsUser.FIRSTNAME+ "," +
                EventDbSchema.UserTable.ColsUser.LASTNAME + "," +
                EventDbSchema.UserTable.ColsUser.PHOTOURI+ ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
