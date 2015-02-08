package com.examples.romantick;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.filters.FilterKissesBase;
import model.filters.FilterKissesDate;
import model.filters.FilterKissesDoneStatus;
import model.filters.FiltersManager;
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
		
		List<FilterKissesBase> filters = FiltersManager.getKissesFilters();
		for(FilterKissesBase filter : filters)
		{
			if(filter != null)
			{
                if(filter instanceof FilterKissesDoneStatus)
                {
                	setDoneStatusControls((FilterKissesDoneStatus)filter);
                }
                if(filter instanceof FilterKissesDate)
                {
                	setDateControls((FilterKissesDate)filter);
                }
			}
		}
	}
	
	//Button actions
	public void saveFilters(View view)
	{
		FiltersManager.clearKissesFilters();
		
		if(checkBox_Status.isChecked())
		{
			FilterKissesDoneStatus filter = getDoneStatusFilter();
			if(filter != null)
			{
			    FiltersManager.addKissesFilter(filter);
			}
		}
		if(checkBox_Date.isChecked())
		{
			FilterKissesDate filter = getDateFilter();
			if(filter != null)
			{
				FiltersManager.addKissesFilter(filter);
			}
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
	private FilterKissesDoneStatus getDoneStatusFilter()
	{
		String done = getResources().getString(R.string.done);
		boolean doneStatus = ((String)spinner_Status.getSelectedItem() == done);
		
		return new FilterKissesDoneStatus(this, doneStatus);
	}
	private FilterKissesDate getDateFilter()
	{
		String beforeAfter = (String)spinner_Date.getSelectedItem();
		Date date = UsefulFunctions.stringToDate(textView_Date.getText().toString());
		
		if(date != null)
		{
			return new FilterKissesDate(this, beforeAfter, date);
		}

		return null;
	}
	private void setDoneStatusControls(FilterKissesDoneStatus filter)
	{
		//Checkbox
		checkBox_Status.setChecked(true);
		
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
		checkBox_Date.setChecked(true);
		
		//Spinner
		String before = getResources().getString(R.string.before);
		String after = getResources().getString(R.string.after);
		String[] spinnerItems = getResources().getStringArray(R.array.spinnerDateItems);
		
		String searchedItem = "";
		if(filter.getBeforeAfter() == before)
		{
			searchedItem = before;
		}
		else if(filter.getBeforeAfter() == after)
		{
			searchedItem = after;
		}
			
		int position = UsefulFunctions.findItemPosition(searchedItem, spinnerItems);
		if(position != -1)
		{
		    spinner_Status.setSelection(position);
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