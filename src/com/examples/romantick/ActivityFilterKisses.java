package com.examples.romantick;

import com.example.romantick.R;
import com.example.romantick.R.id;
import com.example.romantick.R.layout;
import com.example.romantick.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.os.Build;

public class ActivityFilterKisses extends Activity {

	//Controls
	Spinner spinner_Status = null;
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
		spinner_Status = (Spinner) findViewById(R.id.spinner_status);
		spinner_Date = (Spinner) findViewById(R.id.spinner_date);
	}
}