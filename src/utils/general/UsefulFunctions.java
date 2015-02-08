package utils.general;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

public class UsefulFunctions 
{
	public static void showToast(Context context, String message)
	{
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.getView().setBackgroundColor(Color.rgb(255, 165, 0)); //set the background colour to orange
		toast.show();
	}
	
    //DateFormat
    private final static String DATE_FORMAT = "dd/MM/yyyy";
    @SuppressLint("SimpleDateFormat")
	private final static SimpleDateFormat RomanTickDateFormat = new SimpleDateFormat(DATE_FORMAT);
	
	public static String dateToString(Date date)
	{
		return RomanTickDateFormat.format(date);
	}
	public static Date stringToDate(String dateString)
	{
		Date result = null;
		
		try 
		{
			result = RomanTickDateFormat.parse(dateString);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return result;
	}
	public static Date Today()
	{
		//get today's date
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		
		return today;
	}
	
	//Spinners
	public static int findItemPosition(String searchedItem, String[] items)
	{
		int position = -1;
		for(int i = 0; i < items.length; i++)
		{
			if(items[i].equals(searchedItem))
			{
				position = i;
				break;
			}
		}
		
		return position;
	}	
}
