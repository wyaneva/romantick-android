package model.filters;

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
	public List<Kiss> applyFilter(List<Kiss> kissList) 
	{
		List<Kiss> result = new LinkedList<Kiss>(); 
		
		for(Kiss kiss : kissList)
		{
			if(kiss.isDone() == doneStatus)
			{
				result.add(kiss);
			}
		}
		
		return result;
	}
}
