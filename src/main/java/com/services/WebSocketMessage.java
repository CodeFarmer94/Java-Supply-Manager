package com.services;

public class WebSocketMessage {

	private String status;
	private String dataString;
	private String idString;
	private int progress;
	
	
	public WebSocketMessage( String dataString, String idString, String status, int progress) {
		this.dataString = dataString;
		this.idString = idString;
		this.status = status;
		this.setProgress(progress);
	}
	public String getDataString() {
		return dataString;
	}
	public void setDataString(String dataString) {
		this.dataString = dataString;
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
