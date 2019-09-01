package com.ntlts.c196;

public class Progress {
    private int termId;
    private String termName;
    private int courseId;
    private String courseName;

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private String anticipatedEndDate;
    private String assessmentProgress;

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAnticipatedEndDate() {
        return anticipatedEndDate;
    }

    public void setAnticipatedEndDate(String anticipatedEndDate) {
        this.anticipatedEndDate = anticipatedEndDate;
    }

    public String getAssessmentProgress() {
        return assessmentProgress;
    }

    public void setAssessmentProgress(String assessmentProgress) {
        this.assessmentProgress = assessmentProgress;
    }
}

