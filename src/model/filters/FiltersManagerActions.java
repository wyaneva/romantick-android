package model.filters;

import model.Action;
import android.content.Context;

public class FiltersManagerActions extends FiltersManagerBase<Action>
{
	private static FiltersManagerActions singleton = null;
	public static FiltersManagerActions getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new FiltersManagerActions(context);
		}
		
		return singleton;
	}
	private FiltersManagerActions(Context context)
	{
		super();
		
		filterActionsDoneStatus = new FilterDoneStatus<Action>(context);
		filterActionsLocation = new FilterActionsLocation(context);

		filters.add(filterActionsDoneStatus);
		filters.add(filterActionsLocation);
	}
	
	private FilterDoneStatus<Action> filterActionsDoneStatus = null;
	private FilterActionsLocation filterActionsLocation = null;
	
	public void addFilterActionsDoneStatus(boolean isDone) 
	{
		filterActionsDoneStatus.setDoneStatus(isDone);
		filterActionsDoneStatus.setApplied(true);
	}
	public void removeFilterActionsDoneStatus()
	{
		super.removeFilter(filterActionsDoneStatus);
	}
	public FilterBase<Action> getFilterActionsDoneStatus()
	{
		return filterActionsDoneStatus;
	}
	
	public void addFilterActionsLocation(String location)
	{
		filterActionsLocation.setLocation(location);
		filterActionsLocation.setApplied(true);
	}
	public void removeFilterActionsLocation()
	{
		super.removeFilter(filterActionsLocation);
	}
	public FilterBase<Action> getFilterActionsLocation()
	{
		return filterActionsLocation;
	}
}
