package com.examples.romantick;

import java.util.List;

import utils.general.Constants;

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
import android.widget.CheckBox;
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
	}
	
	private void initialiseControls()
	{
		checkBox_Status = (CheckBox) findViewById(R.id.checkBox_KissesStatus);
		spinner_Status = (Spinner) findViewById(R.id.spinner_KissesStatus);
		checkBox_Date = (CheckBox) findViewById(R.id.checkBox_KissesDate);
		spinner_Date = (Spinner) findViewById(R.id.spinner_KissesDate);
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
		if(spinner_Status.getSelectedItemPosition() == 0)
		{
			return new FilterKissesDoneStatus(false);
		}

		if(spinner_Status.getSelectedItemPosition() == 1)
		{
			return new FilterKissesDoneStatus(true);
		}
		
		return null;
	}
}