package model.filters;

import java.util.Date;

import model.Kiss;
import android.content.Context;

public class FiltersManagerKisses extends FiltersManagerBase<Kiss>
{
	private static FiltersManagerKisses singleton = null;
	public static FiltersManagerKisses getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new FiltersManagerKisses(context);
		}
		
		return singleton;
	}
	private FiltersManagerKisses(Context context)
	{
		super();
		
		filterKissesDoneStatus = new FilterDoneStatus<Kiss>(context);
		filterKissesDate = new FilterKissesDate(context);
		
		filters.add(filterKissesDoneStatus);
		filters.add(filterKissesDate);
	}
	
	private FilterDoneStatus<Kiss> filterKissesDoneStatus = null;
	private FilterKissesDate filterKissesDate = null;

	public void addFilterKissesDoneStatus(boolean isDone)
	{
		filterKissesDoneStatus.setDoneStatus(isDone);
		filterKissesDoneStatus.setApplied(true);
	}
	public void removeFilterKissesDoneStatus()
	{
		filterKissesDoneStatus.setApplied(false);
	}
	public FilterBase<Kiss> getFilterKissesDoneStatus()
	{
		return filterKissesDoneStatus;
	}
	
	public void addFilterKissesDate(String beforeAfter, Date date)
	{
		filterKissesDate.setBeforeAfter(beforeAfter);
		filterKissesDate.setDate(date);
		filterKissesDate.setApplied(true);
	}
	public void removeFilterKissesDate()
	{
		filterKissesDate.setApplied(false);
	}
	public FilterBase<Kiss> getFilterKissesDate()
	{
		return filterKissesDate;
	}
}