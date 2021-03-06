package com.examples.romantick;

import model.Action;
import utils.enums.EnumAddOrEditState;
import utils.enums.EnumLocation;
import utils.enums.EnumUtils;
import utils.general.Constants;
import utils.general.UsefulFunctions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.romantick.R;

import datastoragehandler.IDataHandlerActions;
import datastoragehandler.sqlite.SQLiteOpenHelperActions;

public class ActivityAddOrEditAction extends ActionBarActivity 
{
	IDataHandlerActions dataHandler = null;
	EnumAddOrEditState state = null;
	Action actionToEdit = null;
	
	//Controls
	LinearLayout layout_EditControls = null;
	EditText editText_Summary = null;
	Spinner spinner_Location = null;
	Button button_Edit = null;
	Button button_Delete = null;
	Button button_Save = null;
	Button button_Cancel = null;

	//Initialise
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_or_edit_action);

		//Initialise controls
		initialiseControls();

		//set up the datahandler
		dataHandler = SQLiteOpenHelperActions.getInstance(this);

		//get the info that has been passed
		Intent intent = getIntent();
		state = (EnumAddOrEditState)intent.getSerializableExtra(Constants.EXTRA_ADD_OR_EDIT_STATE);
		actionToEdit = (Action)intent.getSerializableExtra(Constants.EXTRA_TODO_TO_EDIT);

		//setup the screen controls
		switch (state) 
		{
		    case ADD:
		    	setupAddStateScreen();
			    break;
		    case EDIT:
		    	setupEditStateScreen(actionToEdit);
		    	break;
		    default:
		    	setButtonVisibility(false);
			    break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void initialiseControls()
	{
		layout_EditControls = (LinearLayout) findViewById(R.id.layout_EditControls);
		editText_Summary = (EditText) findViewById(R.id.editText_Summary);
		spinner_Location = (Spinner) findViewById(R.id.spinner_ActionLocation);
		button_Edit = (Button) findViewById(R.id.button_Edit);
		button_Delete = (Button) findViewById(R.id.button_Delete);
		button_Save = (Button) findViewById(R.id.button_Save);
		button_Cancel = (Button) findViewById(R.id.button_Cancel);
	}
	private void setupAddStateScreen()
	{
		setButtonVisibility(false);
	}
	private void setupEditStateScreen(Action action) 
	{
		setEnableControls(false, layout_EditControls);
		editText_Summary.setText(action.getSummary());
		setLocationInSpinner(action.getLocation(this));
		
		setButtonVisibility(true);
	}

	//buttons
	public void saveAction(View view) throws Exception
	{
		int successStatus;
		switch(state)
		{
		    case ADD:
		        successStatus = addAction();
		    	break;
		    case EDIT:
		    	successStatus = updateAction();
		    	break;
		    default:
		    	throw new Exception("Something is wrong as the activity is neither in ADD nor in EDIT state.");
		}

		if(successStatus == SUCCESS)
		{
		    finish();
		}
	}
	public void editAction(View view)
	{
		setEnableControls(true, layout_EditControls);
		setButtonVisibility(false);
	}
	public void deleteAction(View view)
	{
		dataHandler.deleteAction(actionToEdit);
		
		UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_DELETE);
		finish();
	}
	public void cancelEdit(View view)
	{
		finish();
	}

	//helper functions
	private int addAction() throws Exception
	{
		String summary = editText_Summary.getText().toString();
		if(summary.isEmpty())
		{
			UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_ADD_FAIL);
			return FAIL;
		}
		
		Action newAction = new Action();
		newAction.setSummary(summary);
		newAction.setLocation(getLocationFromSpinner());
		dataHandler.addAction(newAction);
		UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_ADD_SUCCESS);
		return SUCCESS;
	}
	private int updateAction() throws Exception
	{
		String summary = editText_Summary.getText().toString();
		if(summary.isEmpty())
		{
			UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_UPDATE_FAIL);
			return FAIL;
		}
		
		actionToEdit.setSummary(summary);
		actionToEdit.setLocation(getLocationFromSpinner());
		
		int updateResult = dataHandler.updateAction(actionToEdit);
		if(updateResult > 0)
		{
			UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_UPDATE_SUCCESS);
			return SUCCESS;
		}
		else
		{
			UsefulFunctions.showToast(getApplicationContext(), TOAST_MESSAGE_UPDATE_DB_FAIL);
			return DB_FAIL;
		}
	}
	private EnumLocation getLocationFromSpinner() throws Exception
	{
		String selectedLocation = spinner_Location.getSelectedItem().toString();
		
		if(selectedLocation == EnumUtils.getLondon(this))
		    return EnumLocation.LONDON;
		
		else if (selectedLocation == EnumUtils.getEdinburgh(this))
		    return EnumLocation.EDINBURGH;
		
		throw new Exception("Something is wrong as I cannot find location value for selected spinner option " + selectedLocation);
	}
	private void setLocationInSpinner(String location) 
	{
		String[] spinnerItems = getResources().getStringArray(R.array.spinnerLocationItems);
		int position = UsefulFunctions.findItemPosition(location, spinnerItems);
		
		if(position != -1)
		{
			spinner_Location.setSelection(position);
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
	       if (child instanceof ViewGroup)
	       { 
	          setEnableControls(enable, (ViewGroup)child);
	       }
	    }
	}

	//Getters and setters - useful for unit testing
	public EnumAddOrEditState getState() 
	{
		return state;
	}
	public void setState(EnumAddOrEditState _state)
	{
		state = _state;
	}

	//-------------------------------------------------------------------
	private static final String TOAST_MESSAGE_UPDATE_SUCCESS = "Entry was updated.";
    private static final String TOAST_MESSAGE_UPDATE_FAIL = "Cannot update an entry with an empty summary.";
    private static final String TOAST_MESSAGE_UPDATE_DB_FAIL = "Error updating entry.";
	private static final String TOAST_MESSAGE_ADD_SUCCESS = "Entry was added.";
	private static final String TOAST_MESSAGE_ADD_FAIL = "Cannot add an entry with an empty summary.";
	private static final String TOAST_MESSAGE_DELETE = "Entry was deleted.";
	
	private static final int SUCCESS = 1;
	private static final int FAIL = 2;
	private static final int DB_FAIL = 3;
}
