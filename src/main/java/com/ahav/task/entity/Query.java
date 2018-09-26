package com.ahav.task.entity;

public class Query {

	private Integer pageSize = 2;
	private Integer currentPage = 1;
	private Integer start;
	private String orderBy = "publish_time asc";
	private String whichPage;
	private String publisher;
	private String surveyor; 
	private String executor; 
	private String taskStatus; 
	private String acceptanceResults;
	private String relatedMeetings; 
	private String endTime1; 
	private String endTime2; 
	private String taskName; 
	private String publishTime1; 
	private String publishTime2; 
	private String keyWord;
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart() {
		this.start = (this.currentPage-1) * this.pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getWhichPage() {
		return whichPage;
	}
	public void setWhichPage(String whichPage) {
		this.whichPage = whichPage;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getSurveyor() {
		return surveyor;
	}
	public void setSurveyor(String surveyor) {
		this.surveyor = surveyor;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getAcceptanceResults() {
		return acceptanceResults;
	}
	public void setAcceptanceResults(String acceptanceResults) {
		this.acceptanceResults = acceptanceResults;
	}
	public String getRelatedMeetings() {
		return relatedMeetings;
	}
	public void setRelatedMeetings(String relatedMeetings) {
		this.relatedMeetings = relatedMeetings;
	}
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}
	public String getEndTime2() {
		return endTime2;
	}
	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getPublishTime1() {
		return publishTime1;
	}
	public void setPublishTime1(String publishTime1) {
		this.publishTime1 = publishTime1;
	}
	public String getPublishTime2() {
		return publishTime2;
	}
	public void setPublishTime2(String publishTime2) {
		this.publishTime2 = publishTime2;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
}
