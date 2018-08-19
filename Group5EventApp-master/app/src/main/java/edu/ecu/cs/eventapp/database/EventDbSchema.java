package edu.ecu.cs.eventapp.database;

/**
 * Created by dev on 11/6/17.
 */

public class EventDbSchema {

    public static final class EventTable {
        public static final String NAME = "events";

        public static final class ColsEvent {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "eventdate";
            public static final String ORGANIZER = "organizer";
            public static final String DESCRIPTION = "eventdescription";
            public static final String LOCATION = "location";
            public static final String USERID="userid";
            public static final String  EVENT_IMAGE="eventimage";
            public static final String USER_EMAIL="useremail";

        }
    }

    public static class UserTable {
        public static final String NAME = "users";
        public static final class ColsUser {
            public static final String USERID = "userid";
            public static final String USERNAME = "username";
            public static final String EMAIL = "email";
            public static final String PHOTOURI = "photoUri";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String EVENTID = "eventid";

        }
    }
}
