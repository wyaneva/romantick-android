package model;

import java.util.Date;

public class Kiss extends ToDoBase
{
	private static final long serialVersionUID = 1L;

	private Date date;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() 
	{
		return getSummary();
	}




}
