package model.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

public class FiltersManager 
{
	private static FiltersManager singleton = null;
	public static FiltersManager getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new FiltersManager(context);
		}
		
		return singleton;
	}
	private FiltersManager(Context context)
	{
		filterKissesDoneStatus = new FilterKissesDoneStatus(context);
		filterKissesDate = new FilterKissesDate(context);
		
		kissesFilters = new ArrayList<FilterKissesBase>();
		kissesFilters.add(filterKissesDoneStatus);
		kissesFilters.add(filterKissesDate);
	}
	
	private List<FilterKissesBase> kissesFilters = null;
	private FilterKissesDoneStatus filterKissesDoneStatus = null;
	private FilterKissesDate filterKissesDate = null;
	
	public void clearKissesFilters()
	{
		for(FilterKissesBase filter : kissesFilters)
		{
			filter.setApplied(false);
		}
	}

	public void addDoneStatusFilter(boolean isDone)
	{
		filterKissesDoneStatus.setDoneStatus(isDone);
		filterKissesDoneStatus.setApplied(true);
	}
	public void removeDoneStatusFilter()
	{
		filterKissesDoneStatus.setApplied(false);
	}
	
	public void addDateFilter(String beforeAfter, Date date)
	{
		filterKissesDate.setBeforeAfter(beforeAfter);
		filterKissesDate.setDate(date);
		filterKissesDate.setApplied(true);
	}
	public void removeDateFilter()
	{
		filterKissesDate.setApplied(false);
	}
	
	public List<FilterKissesBase> getKissesFilters()
	{
		return kissesFilters;
	}
}
