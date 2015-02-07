package model.filters;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.Kiss;
import utils.general.Constants;

public class FilterKissesDoneStatus extends FilterKissesBase
{
	private boolean doneStatus;
	
	public FilterKissesDoneStatus(boolean status)
	{
		doneStatus = status;
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
		while (iter.hasNext()) {
		    if (iter.next().isDone() != doneStatus) {
		        iter.remove();
		    }
		}
	}
	
	public boolean getDoneStatus()
	{
		return doneStatus;
	}
}
