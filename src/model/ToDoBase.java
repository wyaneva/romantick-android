package model;

import java.io.Serializable;

public class ToDoBase implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String summary;
	private boolean isDone;

	public ToDoBase()
	{
		isDone = false;
	}
	
	//Getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public void setDone(int isDone){
		this.isDone = isDone == 0 ? false : true;
	}
}
