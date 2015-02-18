package com.examples.romantick;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Kiss;
import model.filters.FilterBase;
import model.filters.FilterKissesDate;
import model.filters.FilterDoneStatus;
import model.filters.FiltersManagerKisses;
import utils.general.DatePickerFragment;
import utils.general.UsefulFunctions;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.romantick.R;

public class ActivityFilterKisses extends Activity implements DatePickerDialog.OnDateSetListener
{
	private FiltersManagerKisses filtersManager = null;
	
	//Controls
	CheckBox checkBox_Status = null;
	Spinner spinner_Status = null;
	CheckBox checkBox_Date = null;
	Spinner spinner_Date = null;
	TextView textView_Date = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_kisses);

		filtersManager = FiltersManagerKisses.getInstance(this);
		
		//Initialise controls
		initialiseControls();
		
		//Set listeners
		setListeners();
		
		//Set initial status of the controls
		setInitialState();
	}
	
	//Initialisation
	private void initialiseControls()
	{
		checkBox_Status = (CheckBox) findViewById(R.id.checkBox_KissesStatus);
		spinner_Status = (Spinner) findViewById(R.id.spinner_KissesStatus);
		
		checkBox_Date = (CheckBox) findViewById(R.id.checkBox_KissesDate);
		spinner_Date = (Spinner) findViewById(R.id.spinner_KissesDate);
		textView_Date = (TextView) findViewById(R.id.textView_selectedKissesDate);
	}
	private void setListeners()
	{
		checkBox_Status.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
					{
						setEnabledStatusControls(isChecked);
					}
				});
		
		checkBox_Date.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
					{
						setEnabledDateControls(isChecked);
					}
				});
	}
	private void setInitialState()
	{
		setEnabledStatusControls(false);
		setEnabledDateControls(false);
		textView_Date.setText(UsefulFunctions.dateToString(UsefulFunctions.Today()));
		
		List<FilterBase<Kiss>> filters = filtersManager.getFilters();
		for(FilterBase<Kiss> filter : filters)
		{
            if(filter instanceof FilterDoneStatus)
            {
                setDoneStatusControls((FilterDoneStatus<Kiss>)filter);
            }
            if(filter instanceof FilterKissesDate)
            {
                setDateControls((FilterKissesDate)filter);
            }
		}
	}
	
	//Button actions
	public void saveFilters(View view)
	{
		filtersManager.clearFilters();

		if(checkBox_Status.isChecked())
		{
			filtersManager.addFilterKissesDoneStatus(getDoneStatus());
		}
		if(checkBox_Date.isChecked())
		{
			filtersManager.addFilterKissesDate(getBeforeAfter(), getDate());
		}
		
		finish();
	}
	public void setDate(View view)
	{
		Date defaultDate = UsefulFunctions.stringToDate(textView_Date.getText().toString());
		DialogFragment datePickerFragment = new DatePickerFragment(defaultDate);
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	
	//Helper Functions
	private boolean getDoneStatus()
	{
		String done = getResources().getString(R.string.done);
		boolean doneStatus = ((String)spinner_Status.getSelectedItem() == done);
		
		return doneStatus;
	}
	private String getBeforeAfter()
	{
		return (String)spinner_Date.getSelectedItem();
	}
	private Date getDate()
	{
		return UsefulFunctions.stringToDate(textView_Date.getText().toString());
	}
	
	private void setDoneStatusControls(FilterDoneStatus<Kiss> filter)
	{
		//Checkbox
		checkBox_Status.setChecked(filter.isApplied());
		
		//Spinner
		String notDone = getResources().getString(R.string.not_done);
		String done = getResources().getString(R.string.done);
		String[] spinnerItems = getResources().getStringArray(R.array.spinnerStatusItems);
		
		String searchedItem;
		if(filter.getDoneStatus())
		{
			searchedItem = done;
		}
		else
		{
			searchedItem = notDone;
		}
			
		int position = UsefulFunctions.findItemPosition(searchedItem, spinnerItems);
		if(position != -1)
		{
		    spinner_Status.setSelection(position);
		}
	}
	private void setDateControls(FilterKissesDate filter)
	{
		//Checkbox
		checkBox_Date.setChecked(filter.isApplied());
		
		//Spinner
		String[] spinnerItems = getResources().getStringArray(R.array.spinnerDateItems);
		String searchedItem = filter.getBeforeAfter();
			
		int position = UsefulFunctions.findItemPosition(searchedItem, spinnerItems);
		if(position != -1)
		{
		    spinner_Date.setSelection(position);
		}
		
		//Date text view
		Date selectedDate = filter.getDate();
		if(selectedDate != null)
		{
		    textView_Date.setText(UsefulFunctions.dateToString(selectedDate));
		}
	}
	private void setEnabledStatusControls(boolean enabled)
	{
		spinner_Status.setEnabled(enabled);
	}
	private void setEnabledDateControls(boolean enabled)
	{
		spinner_Date.setEnabled(enabled);
		textView_Date.setEnabled(enabled);
	}

	//override
	@Override
	public void onDateSet(DatePicker view, int year, int month,	int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		Date selectedDate = cal.getTime();
		if(selectedDate != null)
		{
			textView_Date.setText(UsefulFunctions.dateToString(selectedDate));
		}
	}
}