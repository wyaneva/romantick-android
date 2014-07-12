package com.examples.romantick;

import java.util.Calendar;
import java.util.Date;

import model.Kiss;
import utils.general.Constants;
import utils.general.DatePickerFragment;
import utils.general.EnumAddOrEditState;
import utils.general.UsefulFunctions;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.romantick.R;

import datastoragehandler.IDataHandlerKisses;
import datastoragehandler.sqlite.SQLiteOpenHelperKisses;

public class ActivityAddOrEditKiss extends Activity  implements DatePickerDialog.OnDateSetListener 
{
	IDataHandlerKisses dataHandler = null;
	EnumAddOrEditState state = null;
	Kiss kissToEdit = null;
	
	//Controls
	TextView kissDate = null;
	EditText kissSummary = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_or_edit_kiss);

		//Initialise controls
		initialiseControls();
		
		dataHandler = SQLiteOpenHelperKisses.getInstance(this);

		//get the info that has been passed
		Intent intent = getIntent();
		state = (EnumAddOrEditState) intent.getSerializableExtra(Constants.EXTRA_ADD_OR_EDIT_STATE);
		kissToEdit = (Kiss) intent.getSerializableExtra(Constants.EXTRA_TODO_TO_EDIT);
		
		//set the screen based on the passed info
		setScreenControls();
	}
	
	private void initialiseControls()
	{
		kissDate = (TextView) findViewById(R.id.textView_KissDateDisplay);
		kissSummary = (EditText) findViewById(R.id.editText_KissSummary);
	}
	
	private void setScreenControls()
	{
		if(state == EnumAddOrEditState.EDIT && kissToEdit != null)
		{
			kissDate.setText(UsefulFunctions.dateToString(kissToEdit.getDate()));
			kissSummary.setText(kissToEdit.getSummary());
		}
		else
		{
			//get today's date
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			
			kissDate.setText(UsefulFunctions.dateToString(today));
		}
	}
	
	//button functions
	public void saveKiss(View view)
	{
		switch (state) 
		{
		    case EDIT:
		        updateKiss();	
			    break;
			    
		    case ADD:
		    	addKiss();
		    	break;

		    default:
			    break;
		}
		
		finish();
	}
	
	public void deleteKiss(View view)
	{
		if(state == EnumAddOrEditState.EDIT)
		{
			dataHandler.deleteKiss(kissToEdit);
			UsefulFunctions.showToast(getApplicationContext(), TOAST_DELETE_SUCCESS);
		}
		
		finish();
	}
	
	public void setDate(View view)
	{
		Date defaultDate = UsefulFunctions.stringToDate(kissDate.getText().toString());
		DialogFragment datePickerFragment = new DatePickerFragment(defaultDate);
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	
	//helper functions
	private void addKiss()
	{
		Date date = UsefulFunctions.stringToDate(kissDate.getText().toString());
		if(date != null) 
    	{
    		Kiss newKiss = new Kiss();
	    	newKiss.setSummary(kissSummary.getText().toString());
			newKiss.setDate(date);
		    dataHandler.addKiss(newKiss);
		    
		    UsefulFunctions.showToast(getApplicationContext(), TOAST_ADD_SUCCESS);
		} 
		else
    	{
    		UsefulFunctions.showToast(getApplicationContext(), TOAST_ADD_FAIL);
		}
    	
    	//TODO: add toasts
	}
	
	private void updateKiss()
	{
		Date date = UsefulFunctions.stringToDate(kissDate.getText().toString());
		if(date != null)
		{
			kissToEdit.setSummary(kissSummary.getText().toString());
			kissToEdit.setDate(date);
			dataHandler.updateKiss(kissToEdit);
			
			UsefulFunctions.showToast(getApplicationContext(), TOAST_UPDATE_SUCCESS);
		}
		else
		{
    		UsefulFunctions.showToast(getApplicationContext(), TOAST_UPDATE_FAIL);
		}
		
		//TODO: add toasts
	}
	
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
			kissDate.setText(UsefulFunctions.dateToString(selectedDate));
		}
	}
	
	//------------------------------------------------------------
	private final static String TOAST_ADD_SUCCESS   	= "Kiss has been added.";
	private final static String TOAST_ADD_FAIL			= "Kiss was NOT added.";
	private final static String TOAST_UPDATE_SUCCESS	= "Kiss has been updated.";
	private final static String TOAST_UPDATE_FAIL		= "Kiss was NOT updated.";
	private final static String TOAST_DELETE_SUCCESS	= "Kiss has been deleted.";
	
}
