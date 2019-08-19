package com.ntlts.c196.database;

import android.provider.BaseColumns;

public class AssessmentDB {
    private AssessmentDB(){}

    public static class AssessmentEntry implements BaseColumns {
        public static String TABLE_NAME = "assessmentDb";
        public static String TITLE = "title";
        public static String OAPA = "oaPa";
        public static String COURSEID = "courseId";
    }
}
