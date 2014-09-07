package com.examples.romantick;

import java.util.List;

import utils.general.Constants;

import model.Kiss;
import model.filters.FilterKissesBase;
import model.filters.FilterKissesDoneStatus;
import model.filters.FiltersManager;

import com.example.romantick.R;
import com.example.romantick.R.id;
import com.example.romantick.R.layout;
import com.example.romantick.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.os.Build;

public class ActivityFilterKisses extends Activity {

	//Controls
	CheckBox checkBox_Status = null;
	Spinner spinner_Status = null;
	CheckBox checkBox_Date = null;
	Spinner spinner_Date = null;
	
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
		
		List<FilterKissesBase> filters = FiltersManager.getKissesFilters();
		for(FilterKissesBase filter : filters)
		{
			if(filter != null)
			{
                if(filter instanceof FilterKissesDoneStatus)
                {
                	setDoneStatusControls((FilterKissesDoneStatus)filter);
                }
			}
		}
	}
	
	//Button actions
	public void saveFilters(View view)
	{
		Log.d("TAG", "save kisses filters");

		FiltersManager.clearKissesFilters();
		
		if(checkBox_Status.isChecked())
		{
			FilterKissesDoneStatus filter = getDoneStatusFilter();
			
			if(filter != null)
			{
				Log.d("TAG", "adding a filter");
			    FiltersManager.addKissesFilter(getDoneStatusFilter());
			}
		}
		
		finish();
	}
	
	//Helper Functions
	private FilterKissesDoneStatus getDoneStatusFilter()
	{
		String notDone = getResources().getString(R.string.not_done);
		String done = getResources().getString(R.string.done);
		
		if((String)spinner_Status.getSelectedItem() == notDone)
		{
			return new FilterKissesDoneStatus(false);
		}

		if((String)spinner_Status.getSelectedItem() == done)
		{
			return new FilterKissesDoneStatus(true);
		}
		
		return null;
	}
	private void setDoneStatusControls(FilterKissesDoneStatus filter)
	{
		checkBox_Status.setChecked(true);
		
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
			
		int position = -1;
		for(int i = 0; i < spinnerItems.length; i++)
		{
			if(spinnerItems[i] == searchedItem)
			{
				position = i;
				return;
			}
		}
		
		if(position != -1)
		{
		    spinner_Status.setSelection(position);
		}
	}
	private void setEnabledStatusControls(boolean enabled)
	{
		if(enabled)
		{
			spinner_Status.setEnabled(true);
		}
		else
		{
			spinner_Status.setEnabled(false);
		}
	}
	private void setEnabledDateControls(boolean enabled)
	{
		if(enabled)
		{
			spinner_Date.setEnabled(true);
		}
		else
		{
			spinner_Date.setEnabled(false);
		}
	}
}