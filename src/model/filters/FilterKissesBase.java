package model.filters;

import java.util.List;

import android.content.Context;

import model.Kiss;

public abstract class FilterKissesBase 
{
	protected Context context;
	protected boolean isApplied;
	
	public FilterKissesBase(Context _context)
	{
		context = _context;
	}
	
	public abstract String getDisplayString();
	public abstract void applyFilter(List<Kiss> kissList);

	//Getters and Setters
	public boolean isApplied() 
	{
		return isApplied;
	}
	public void setApplied(boolean isApplied) 
	{
		this.isApplied = isApplied;
	}
}
