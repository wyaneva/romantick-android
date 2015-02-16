package model.filters;

import java.util.ArrayList;
import java.util.List;

import model.ToDoBase;

public class FiltersManagerBase<T extends ToDoBase> 
{
	protected FiltersManagerBase()
	{
		filters = new ArrayList<FilterBase<T>>();
	}
	
	private boolean filtersOn;
	protected List<FilterBase<T>> filters = null;
	
	public void clearFilters()
	{
		for(FilterBase<T> filter : filters)
		{
			filter.setApplied(false);
		}
	}
	public void removeFilter(FilterBase<T> filter)
	{
		filter.setApplied(false);
	}

	//Getters and Setters
	public List<FilterBase<T>> getFilters()
	{
		return filters;
	}
	public boolean filtersOn()
	{
		return filtersOn;
	}
	public void setFiltersOn(boolean filtersOn) 
	{
		this.filtersOn = filtersOn;
	}
}
