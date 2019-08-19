package com.ntlts.c196.database;

import android.provider.BaseColumns;

public class MentorDB {
    private MentorDB(){}

    public static class MentorEntry implements BaseColumns {
        public static String TABLE_NAME = "mentorDb";
        public static String NAME = "name";
        public static String PHONE = "phone";
        public static String EMAIL = "email";
    }
}
