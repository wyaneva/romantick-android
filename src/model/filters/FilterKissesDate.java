package model.filters;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import utils.general.UsefulFunctions;

import com.example.romantick.R;

import android.content.Context;
import android.util.Log;

import model.Kiss;

public class FilterKissesDate extends FilterKissesBase
{
	private String beforeAfter;
	private Date date;
	
	public FilterKissesDate(Context _context, String _beforeAfter, Date _date)
	{
		super(_context);

		beforeAfter = _beforeAfter;
		date = _date;
	}
	
	@Override
	public String getDisplayString() 
	{
		return beforeAfter + " " + UsefulFunctions.dateToString(date);
	}
	@Override
	public void applyFilter(List<Kiss> kissList) 
	{
		Log.d("TAG", "apply date filter");
		
		String before = context.getResources().getString(R.string.before);
		String after = context.getResources().getString(R.string.after);
		boolean isBefore = (beforeAfter == before);
		boolean isAfter = (beforeAfter == after);
		
		Iterator<Kiss> iter = kissList.iterator();
		while (iter.hasNext()) 
		{
			if(isBefore && iter.next().getDate().after((date)))
			{
				iter.remove();
			}
			else if(isAfter && iter.next().getDate().before(date))
			{
				iter.remove();
			}
		}
	}

	//Getters and setters
	public String getBeforeAfter()
	{
		return beforeAfter;
	}
	public Date getDate()
	{
		return date;
	}
}
