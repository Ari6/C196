package com.ntlts.c196.database;

import android.provider.BaseColumns;

public class TermDB  {
    private TermDB(){} // To prevent someone from instantiating.

    public static class TermEntry implements BaseColumns {
        public static final String TABLE_NAME = "termDb";
        public static final String TITLE = "title";
        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
    }
}
