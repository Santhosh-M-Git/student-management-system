package com.project.sms;

import java.util.List;

public class DashboardDTO {

    private String name;
    private double attendancePercentage;
    private double averageMarks;
    private String predictedResult;
    private String subjectSuggestion;
    private List<Marks> marksList;
    private List<Alert> alerts;
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAttendancePercentage() {
		return attendancePercentage;
	}
	public void setAttendancePercentage(double attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}
	public double getAverageMarks() {
		return averageMarks;
	}
	public void setAverageMarks(double averageMarks) {
		this.averageMarks = averageMarks;
	}
	public List<Alert> getAlerts() {
		return alerts;
	}
	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public String getPredictedResult() { 
		return predictedResult; 
	}
    public void setPredictedResult(String predictedResult) { 
    	this.predictedResult = predictedResult; 
    }
    public String getSubjectSuggestion() { 
    	return subjectSuggestion; 
    }
    public void setSubjectSuggestion(String subjectSuggestion) { 
    	this.subjectSuggestion = subjectSuggestion; 
    }
    public List<Marks> getMarksList() { 
    	return marksList; 
    	}
    public void setMarksList(List<Marks> marksList) { 
    	this.marksList = marksList; 
    	}

}