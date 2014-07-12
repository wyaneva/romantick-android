package model.filters;

import java.util.List;

import model.Kiss;

public abstract class FilterKissesBase 
{
	public abstract String getDisplayString();
	public abstract List<Kiss> applyFilter(List<Kiss> kissList);
}
