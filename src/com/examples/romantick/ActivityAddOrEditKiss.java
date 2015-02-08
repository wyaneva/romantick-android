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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	LinearLayout layout_EditControls = null;
	TextView kissDate = null;
	EditText kissSummary = null;
	Button button_Edit = null;
	Button button_Delete = null;
	Button button_Save = null;
	Button button_Cancel = null;
    
	//Initialise
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
		layout_EditControls = (LinearLayout) findViewById(R.id.layout_EditControls);
		kissDate = (TextView) findViewById(R.id.textView_KissDateDisplay);
		kissSummary = (EditText) findViewById(R.id.editText_KissSummary);
	    button_Edit = (Button) findViewById(R.id.button_Edit);
		button_Delete = (Button) findViewById(R.id.button_Delete);
		button_Save = (Button) findViewById(R.id.button_Save);
		button_Cancel = (Button) findViewById(R.id.button_Cancel);
	}
	private void setScreenControls()
	{
		if(state == EnumAddOrEditState.EDIT && kissToEdit != null)
		{
			Log.d("tag", "inside edit");
    		setEnableControls(false, layout_EditControls);
			kissDate.setText(UsefulFunctions.dateToString(kissToEdit.getDate()));
			kissSummary.setText(kissToEdit.getSummary());
    		setButtonVisibility(true);
		}
		else
		{
			kissDate.setText(UsefulFunctions.dateToString(UsefulFunctions.Today()));
    		setButtonVisibility(false);
		}
	}
	
	//button functions
	public void saveKiss(View view)
	{
		int successState = FAIL;
		switch (state) 
		{
		    case EDIT:
		    	successState = updateKiss();	
			    break;
			    
		    case ADD:
		    	successState = addKiss();
		    	break;
		}
		
		if(successState == SUCCESS)
		{
		    finish();
		}
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
	public void editKiss(View view)
	{
		setEnableControls(true, layout_EditControls);
		setButtonVisibility(false);
	}
	public void cancelEdit(View view)
	{
		finish();
	}
	public void setDate(View view)
	{
		Date defaultDate = UsefulFunctions.stringToDate(kissDate.getText().toString());
		DialogFragment datePickerFragment = new DatePickerFragment(defaultDate);
		datePickerFragment.show(getFragmentManager(), "datePicker");
	}
	
	//helper functions
	private int addKiss()
	{
		Date date = UsefulFunctions.stringToDate(kissDate.getText().toString());
		String summary = kissSummary.getText().toString();
		if(date != null && !summary.isEmpty()) 
    	{
    		Kiss newKiss = new Kiss();
	    	newKiss.setSummary(summary);
			newKiss.setDate(date);
		    dataHandler.addKiss(newKiss);
		    
		    UsefulFunctions.showToast(getApplicationContext(), TOAST_ADD_SUCCESS);
		    return SUCCESS;
		} 
		else
    	{
    		UsefulFunctions.showToast(getApplicationContext(), TOAST_ADD_FAIL);
    		return FAIL;
		}
	}
	private int updateKiss()
	{
		Date date = UsefulFunctions.stringToDate(kissDate.getText().toString());
		String summary = kissSummary.getText().toString();
		if(date != null && !summary.isEmpty())
		{
			kissToEdit.setSummary(summary);
			kissToEdit.setDate(date);
			dataHandler.updateKiss(kissToEdit);
			
			UsefulFunctions.showToast(getApplicationContext(), TOAST_UPDATE_SUCCESS);
			return SUCCESS;
		}
		else
		{
    		UsefulFunctions.showToast(getApplicationContext(), TOAST_UPDATE_FAIL);
    		return FAIL;
		}
	}
	private void setButtonVisibility(boolean areEditDeleteVisible)
	{		
		button_Edit.setVisibility(boolToVisibility(areEditDeleteVisible));
		button_Delete.setVisibility(boolToVisibility(areEditDeleteVisible));
		button_Save.setVisibility(boolToVisibility(!areEditDeleteVisible));
		button_Cancel.setVisibility(boolToVisibility(!areEditDeleteVisible));
	}
	private int boolToVisibility(boolean isVisible)
	{
		return isVisible ? View.VISIBLE : View.INVISIBLE;
	}
	private void setEnableControls(boolean enable, ViewGroup vg)
	{
	    for (int i = 0; i < vg.getChildCount(); i++){
	       View child = vg.getChildAt(i);
	       child.setEnabled(enable);
	       if (child instanceof ViewGroup){ 
	          setEnableControls(enable, (ViewGroup)child);
	       }
	    }
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
			kissDate.setText(UsefulFunctions.dateToString(selectedDate));
		}
	}
	
	//------------------------------------------------------------
	private final static String TOAST_ADD_SUCCESS   	= "Kiss was added.";
	private final static String TOAST_ADD_FAIL			= "Cannot add a kiss with an empty summary.";
	private final static String TOAST_UPDATE_SUCCESS	= "Kiss was updated.";
	private final static String TOAST_UPDATE_FAIL		= "Cannot update a kiss with an empty summary.";
	private final static String TOAST_DELETE_SUCCESS	= "Kiss was deleted.";
	
	private final static int SUCCESS = 1;
	private final static int FAIL	 = 2;
}
