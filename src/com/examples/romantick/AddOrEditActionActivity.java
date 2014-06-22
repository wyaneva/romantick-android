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
import android.widget.EditText;
import android.widget.TextView;

public class AddOrEditActionActivity extends ActionBarActivity 
{
	IDataHandler dataHandler = null;
	ActionActivityState state = null;
	Integer actionToEditId = null;
	
	//Controls
	EditText textView_Summary = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_add_or_edit_action);

		//Initialise controls
		initialiseControls();

		//set up the datahandler
		dataHandler = ActionListSQliteOpenHelper.getInstance(this);

		//Get the info that has been passed
		Intent intent = getIntent();
		//dataHandler = (IDataHandler)intent.getSerializableExtra(MainActivity.EXTRA_DATAHANDLER);
		state = (ActionActivityState)intent.getSerializableExtra(MainActivity.EXTRA_ACTION_ACTIVITY_STATE);
		actionToEditId = (Integer)intent.getSerializableExtra(MainActivity.EXTRA_ACTION_TO_EDIT_ID);
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
	}

	//button
	public void saveAction(View view)
	{
		switch(state)
		{
		    case ADD:
		    	Action newAction = new Action();
		    	newAction.setSummary(textView_Summary.getText().toString());
		    	dataHandler.addAction(newAction);
		    case EDIT:
		    	//TODO: Update the db
		    default:
			    break;
		}
	}
}
