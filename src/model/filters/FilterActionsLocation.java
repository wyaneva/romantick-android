package model.filters;

import java.util.Iterator;
import java.util.List;

import model.Action;
import android.content.Context;

import com.example.romantick.R;

public class FilterActionsLocation extends FilterBase<Action> 
{
	private String location;
	
	public FilterActionsLocation(Context _context) 
	{
		super(_context);
		location = _context.getResources().getString(R.string.edinburgh);
	}

	@Override
	public String getDisplayString() 
	{
		return "Location: " + location;
	}

	@Override
	public void applyFilter(List<Action> actionList) 
	{
		Iterator<Action> iter = actionList.iterator();
		while (iter.hasNext())
		{
			Action action = iter.next();
			if(!location.equals(action.getLocation(context)))
			{
				iter.remove();
			}
		}
	}

	//Getters and setters
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getLocation()
	{
		return location;
	}
}
