package model.filters;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.Kiss;

public class FilterKissesDate extends FilterKissesBase
{
	private String beforeAfter;
	private Date date;
	
	public FilterKissesDate(String _beforeAfter, Date _date)
	{
		beforeAfter = _beforeAfter;
		date = _date;
	}
	
	@Override
	public String getDisplayString() 
	{
		return beforeAfter + " " + date.toString();
	}

	@Override
	public void applyFilter(List<Kiss> kissList) 
	{
		Iterator<Kiss> iter = kissList.iterator();
		while (iter.hasNext()) 
		{
//		    if (iter.next().intValue() == 5) {
//		        iter.remove();
//		    }
		}
	}
}
