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
}
