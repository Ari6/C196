package com.ntlts.c196;

public class Assessment {
    private long assessmentId;
    private String title;
    private String OaPa;
    private long courseId;

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOaPa() {
        return OaPa;
    }

    public void setOaPa(String oaPa) {
        OaPa = oaPa;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
