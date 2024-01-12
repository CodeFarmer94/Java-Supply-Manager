package com.services;

public class ProcessWebSocketMessage {

	private String status;
	private String idString;
	private int progress;
	
	
	public ProcessWebSocketMessage(  String idString, String status, int progress) {
	
		this.idString = idString;
		this.status = status;
		this.setProgress(progress);
	}

	public String getIdString() {
		return idString;
	}
	public void setIdString(String idString) {
		this.idString = idString;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
}
