package com.ntlts.c196.database;

import android.provider.BaseColumns;

public class CourseDB {
    private CourseDB(){}

    public static class CourseEntry implements BaseColumns {
        public static String TABLE_NAME = "courseDb";
        public static String TITLE = "title";
        public static String START = "start";
        public static String ANTICIPATED_DATE = "anticipatedDate";
        public static String DUE_DATE = "dueDate";
        public static String NOTE = "note";
        public static String STATUS = "status";
        //public static String MENTORID = "mentorId";
        public static String TERMID = "termId";
        public static String MENTORNAME = "mentorName";
        public static String MENTORPHONE = "mentorPhone";
        public static String MENTOREMAIL = "mentorEmail";

    }
}
