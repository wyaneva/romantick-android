package model;

import android.content.Context;
import utils.enums.EnumLocation;
import utils.enums.EnumUtils;

public class Action extends ToDoBase 
{
	private static final long serialVersionUID = 1L;

	private EnumLocation location;
	
	//Override
	@Override
	public String toString() 
	{
		return getSummary();
	}

	//Getters and setters
	public EnumLocation getLocation() 
	{
		return location;
	}
	public void setLocation(EnumLocation location) 
	{
		this.location = location;
	}
	public String getLocation(Context context)
	{
		return EnumUtils.enumLocationToString(context, location);
	}
	public void setLocation(Context context, String location)
	{
		this.location = EnumUtils.enumLocationFromString(context, location);
	}
}
