package utils.general;

import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
{
	private Date defaultDate = null;
	
	public DatePickerFragment(Date date)
	{
		defaultDate = date;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		Calendar cal = Calendar.getInstance();
		
		if(defaultDate != null)
		{
			cal.setTime(defaultDate);
		}

		int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
	}
}
