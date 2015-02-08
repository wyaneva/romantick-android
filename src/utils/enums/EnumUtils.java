package utils.enums;

import com.example.romantick.R;

import android.content.Context;

public class EnumUtils 
{
	//Locations
	public static String getLondon(Context context)
	{
		return context.getResources().getString(R.string.london);
	}
	public static String getEdinburgh(Context context)
	{
		return context.getResources().getString(R.string.edinburgh);
	}
	public static String enumLocationToString(Context context, EnumLocation location)
	{
		switch(location)
		{
		    case LONDON:
		    	return getLondon(context);

		    case EDINBURGH:
		    	return getEdinburgh(context);
		}
		
		return null;
	}
	public static EnumLocation enumLocationFromString(Context context, String location)
	{
		if(location.equals(getLondon(context)))
			return EnumLocation.LONDON;
		
		if(location.equals(getEdinburgh(context)))
			return EnumLocation.EDINBURGH;
		
		return null;
	}
}
