package model.filters;

import java.util.Iterator;
import java.util.List;

import model.Kiss;
import utils.general.Constants;
import android.content.Context;

public class FilterKissesDoneStatus extends FilterKissesBase
{
	private boolean doneStatus;
	
//	public FilterKissesDoneStatus(Context _context, boolean _status)
//	{
//		super(_context);
//
//		doneStatus = _status;
//	}
	
	public FilterKissesDoneStatus(Context context)
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
	public void applyFilter(List<Kiss> kissList) 
	{
		Iterator<Kiss> iter = kissList.iterator();
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
