package model.filters;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.Kiss;
import utils.general.UsefulFunctions;
import android.content.Context;

import com.example.romantick.R;

public class FilterKissesDate extends FilterKissesBase
{
	private String beforeAfter;
	private Date date;
	
	public FilterKissesDate(Context context)
	{
		super(context);
		beforeAfter = context.getResources().getString(R.string.before);
		date = UsefulFunctions.Today();
	}
	
	@Override
	public String getDisplayString() 
	{
		return beforeAfter + " " + UsefulFunctions.dateToString(date);
	}
	@Override
	public void applyFilter(List<Kiss> kissList) 
	{
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

	public void setBeforeAfter(String beforeAfter) 
	{
		this.beforeAfter = beforeAfter;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}
}
