package model.filters;

import java.util.List;

import model.ToDoBase;

import android.content.Context;

public abstract class FilterBase<T extends ToDoBase> 
{
	protected Context context;
	protected boolean isApplied;
	
	public FilterBase(Context _context)
	{
		context = _context;
	}
	
	public abstract String getDisplayString();
	public abstract void applyFilter(List<T> list);

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
