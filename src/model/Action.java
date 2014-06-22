package model;

import java.io.Serializable;

public class Action implements Serializable 
{
	private static final long serialVersionUID = 1L;

	private int id;
	private String summary;

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

	@Override
	public String toString() 
	{
		return summary;
	}
}
