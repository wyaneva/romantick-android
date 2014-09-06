package model.filters;

import java.util.ArrayList;
import java.util.List;

public class FiltersManager 
{
	private static List<FilterKissesBase> kissesFilters = new ArrayList<FilterKissesBase>();
	
	public static void clearKissesFilters()
	{
		kissesFilters.clear();
	}

	public static void addKissesFilter(FilterKissesBase filter)
	{
		kissesFilters.add(filter);
	}
	
	public static List<FilterKissesBase> getKissesFilters()
	{
		return kissesFilters;
	}
}
