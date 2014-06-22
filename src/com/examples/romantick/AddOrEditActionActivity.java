package com.examples.romantick;

import model.Action;
import utils.ActionActivityState;

import com.example.romantick.R;
import com.example.romantick.R.id;
import com.example.romantick.R.layout;
import com.example.romantick.R.menu;

import datastoragehandler.IDataHandler;
import datastoragehandler.sqlite.ActionListSQliteOpenHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddOrEditActionActivity extends ActionBarActivity 
{
	IDataHandler dataHandler = null;
	ActionActivityState state = null;
	Action actionToEdit = null;
	
	//Controls
	EditText textView_Summary = null;
	Button button_Edit = null;
	Button button_Delete = null;
	Button button_Save = null;
	Button button_Cancel = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_add_or_edit_action);

		//Initialise controls
		initialiseControls();

		//set up the datahandler
		dataHandler = ActionListSQliteOpenHelper.getInstance(this);

		//get the info that has been passed
		Intent intent = getIntent();
		state = (ActionActivityState)intent.getSerializableExtra(MainActivity.EXTRA_ACTION_ACTIVITY_STATE);
		actionToEdit = (Action)intent.getSerializableExtra(MainActivity.EXTRA_ACTION_TO_EDIT_ID);

		//set the buttons
		switch (state) 
		{
		    case ADD:
		        setButtonVisibility(false);	
			    break;
		    case EDIT:
		    	setButtonVisibility(true);
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

	//Initialise
	private void initialiseControls()
	{
		textView_Summary = (EditText) findViewById(R.id.editText_Summary);
		button_Edit = (Button) findViewById(R.id.button_Edit);
		button_Delete = (Button) findViewById(R.id.button_Delete);
		button_Save = (Button) findViewById(R.id.button_Save);
		button_Cancel = (Button) findViewById(R.id.button_Cancel);
	}

	//buttons
	public void saveAction(View view)
	{
		switch(state)
		{
		    case ADD:
		    	addAction();
		    	break;
		    case EDIT:
		    	updateAction();
		    	break;
		    default:
			    break;
		}
	}
	
	public void editAction(View view)
	{
		//TODO:
	}

	public void deleteAction(View view)
	{
		//TODO:
	}

	public void cancelEdit(View view)
	{
		//TODO:
	}

	//helper functions
	private void addAction()
	{
		Action newAction = new Action();
		newAction.setSummary(textView_Summary.getText().toString());
		dataHandler.addAction(newAction);
	}

	private void updateAction()
	{
		//TODO:
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
}
