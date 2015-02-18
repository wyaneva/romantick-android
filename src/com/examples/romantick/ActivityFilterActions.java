package com.examples.romantick;

import java.util.List;

import utils.general.UsefulFunctions;

import model.Action;
import model.filters.FilterActionsLocation;
import model.filters.FilterBase;
import model.filters.FilterDoneStatus;
import model.filters.FiltersManagerActions;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import com.example.romantick.R;

public class ActivityFilterActions extends Activity
{
	private FiltersManagerActions filtersManager = null;
	
	//Controls
	CheckBox checkBox_Status = null;
	Spinner spinner_Status = null;
	CheckBox checkBox_Location = null;
	Spinner spinner_Location = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_actions);
		
		filtersManager = FiltersManagerActions.getInstance(this);
		
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
		checkBox_Status = (CheckBox) findViewById(R.id.checkBox_ActionsStatusFilter);
		spinner_Status = (Spinner) findViewById(R.id.spinner_ActionsStatusFilter);
		
		checkBox_Location = (CheckBox) findViewById(R.id.checkBox_ActionsLocationFilter);
		spinner_Location = (Spinner) findViewById(R.id.spinner_ActionsLocationFilter);
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
			
		checkBox_Location.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
						
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
					{
						setEnabledLocationControls(isChecked);
					}
				});
	}
	private void setInitialState()
	{
		setEnabledStatusControls(false);
		setEnabledLocationControls(false);
			
		List<FilterBase<Action>> filters = filtersManager.getFilters();
		for(FilterBase<Action> filter : filters)
		{
	           if(filter instanceof FilterDoneStatus)
	           {
	               setDoneStatusControls((FilterDoneStatus<Action>)filter);
	           }
	           if(filter instanceof FilterActionsLocation)
	           {
	               setLocationControls((FilterActionsLocation)filter);
	           }
		}
	}
	
	//Button actions
	public void saveFilters(View view)
	{
		filtersManager.clearFilters();

		if(checkBox_Status.isChecked())
		{
			filtersManager.addFilterActionsDoneStatus(getDoneStatus());
		}
		if(checkBox_Location.isChecked())
		{
			filtersManager.addFilterActionsLocation(getLocation());
		}
		
		finish();
	}
	
	//Helper Functions
	private void setEnabledStatusControls(boolean isEnabled)
	{
		spinner_Status.setEnabled(isEnabled);
	}
	private void setEnabledLocationControls(boolean isEnabled)
	{
		spinner_Location.setEnabled(isEnabled);
	}
	private void setDoneStatusControls(FilterDoneStatus<Action> filter)
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
	private void setLocationControls(FilterActionsLocation filter)
	{
		//Checkbox
		checkBox_Location.setChecked(filter.isApplied());
		
		//Spinner
		String[] spinnerItems = getResources().getStringArray(R.array.spinnerLocationItems);
		String searchedItem = filter.getLocation();
		int position = UsefulFunctions.findItemPosition(searchedItem, spinnerItems);
		if(position != -1)
		{
			spinner_Location.setSelection(position);
		}
	}
	private boolean getDoneStatus()
	{
		String done = getResources().getString(R.string.done);
		boolean doneStatus = ((String)spinner_Status.getSelectedItem() == done);
		
		return doneStatus;
	}
	private String getLocation()
	{
		return (String)spinner_Location.getSelectedItem();
	}
}
