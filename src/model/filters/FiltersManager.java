package model.filters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Kiss;

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
		filterKissesDoneStatus = new FilterDoneStatus<Kiss>(context);
		filterKissesDate = new FilterKissesDate(context);
		
		kissesFilters = new ArrayList<FilterBase<Kiss>>();
		kissesFilters.add(filterKissesDoneStatus);
		kissesFilters.add(filterKissesDate);
	}
	
	private boolean filtersOn = false;
	
	private List<FilterBase<Kiss>> kissesFilters = null;
	private FilterDoneStatus<Kiss> filterKissesDoneStatus = null;
	private FilterKissesDate filterKissesDate = null;
	
	public void clearKissesFilters()
	{
		for(FilterBase<Kiss> filter : kissesFilters)
		{
			filter.setApplied(false);
		}
	}

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
	
	public List<FilterBase<Kiss>> getKissesFilters()
	{
		return kissesFilters;
	}
	
	//Getters and setters
	public boolean filtersOn()
	{
		return filtersOn;
	}
	public void setFiltersOn(boolean filtersOn)
	{
		this.filtersOn = filtersOn;
	}
}
