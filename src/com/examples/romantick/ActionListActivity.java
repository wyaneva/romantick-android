package com.examples.romantick;

import model.Action;
import utils.Constants;
import utils.EnumActionActivityState;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.romantick.R;

import datastoragehandler.IActionsListDataHandler;
import datastoragehandler.sqlite.ActionListSQliteOpenHelper;

public class ActionListActivity extends Activity {

	private IActionsListDataHandler dataHandler = null;
	private ActionListAdapter actionListAdapter;
	
	//controls
	private ListView listView_allActions = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        //initialise
        initialiseControls();
        //setListeners();
        
        //create the data handler
        dataHandler = ActionListSQliteOpenHelper.getInstance(this);
        
        //create the Action List Adapter
        actionListAdapter = new ActionListAdapter(this);

        //no need to populate the list of actions, as this is done OnResume()
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() 
    {
    	super.onResume();
    	
    	//populate the list of actions
    	populateActionList();
    }
    
    //Initialise
    private void initialiseControls()
    {
    	listView_allActions = (ListView) findViewById(R.id.listView_allActions);
    }

    //Button Actions
    public void addNewAction(View view)
    {
    	Intent intent = new Intent(this, AddOrEditActionActivity.class);

    	//add the data handler to the intent
    	intent.putExtra(Constants.EXTRA_ACTION_ACTIVITY_STATE, EnumActionActivityState.ADD);
    	intent.putExtra(Constants.EXTRA_ACTION_TO_EDIT, (Action)null);
    	startActivity(intent);
    }

    //Helper functions
    private void populateActionList()
    {
    	actionListAdapter.setActionList(dataHandler.getAllActions());
    	listView_allActions.setAdapter(actionListAdapter);
    }
}
