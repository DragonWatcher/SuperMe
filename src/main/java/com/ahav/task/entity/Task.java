package com.ahav.task.entity;

public class Task {
	/** 任务id */
    private String taskId;

    /** 任务名称 */
    private String taskName;

    /** 任务描述 */
    private String taskDescribe;

    /** 任务发布时间 */
    private String publishTime;

    /** 任务状态 */
    private String taskStatus;

    /** 任务发布人 */
    private String publisher;

    /** 任务验收人 */
    private String surveyor;

    /** 任务执行人 */
    private String executor;

    /** 任务开始时间 */
    private String startTime;

    /** 任务结束时间*/
    private String endTime;

    /** 相关会议*/
    private String relatedMeetings;
    
    /** 验收结果 */
    private String acceptanceResults;
    
    /** 验收评价 */
    private String acceptanceEvaluate;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe == null ? null : taskDescribe.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getSurveyor() {
        return surveyor;
    }

    public void setSurveyor(String surveyor) {
        this.surveyor = surveyor == null ? null : surveyor.trim();
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor == null ? null : executor.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getRelatedMeetings() {
        return relatedMeetings;
    }

    public void setRelatedMeetings(String relatedMeetings) {
        this.relatedMeetings = relatedMeetings == null ? null : relatedMeetings.trim();
    }

	public String getAcceptanceResults() {
		return acceptanceResults;
	}

	public void setAcceptanceResults(String acceptanceResults) {
		this.acceptanceResults = acceptanceResults;
	}

	public String getAcceptanceEvaluate() {
		return acceptanceEvaluate;
	}

	public void setAcceptanceEvaluate(String acceptanceEvaluate) {
		this.acceptanceEvaluate = acceptanceEvaluate;
	}
}