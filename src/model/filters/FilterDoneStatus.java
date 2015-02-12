package model.filters;

import java.util.Iterator;
import java.util.List;

import model.ToDoBase;
import utils.general.Constants;
import android.content.Context;

public class FilterDoneStatus<T extends ToDoBase> extends FilterBase<T>
{
	private boolean doneStatus;
	
	public FilterDoneStatus(Context context)
	{
		super(context);
		doneStatus = false;
	}
	
	@Override
	public String getDisplayString() 
	{
		if(doneStatus)
		{
			return Constants.DONE;
		}
		else
		{
			return Constants.NOT_DONE;
		}
	}

	@Override
	public void applyFilter(List<T> kissList) 
	{
		Iterator<T> iter = kissList.iterator();
		while (iter.hasNext()) 
		{
		    if (iter.next().isDone() != doneStatus) 
		    {
		        iter.remove();
		    }
		}
	}
	
	public boolean getDoneStatus()
	{
		return doneStatus;
	}
	public void setDoneStatus(boolean doneStatus)
	{
		this.doneStatus = doneStatus;
	}
}
