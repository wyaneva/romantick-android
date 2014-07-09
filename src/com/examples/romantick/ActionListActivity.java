package com.examples.romantick;

import java.util.List;

import utils.Constants;
import utils.EnumActionActivityState;
import model.Action;

import com.example.romantick.R;
import com.example.romantick.R.id;
import com.example.romantick.R.layout;
import com.example.romantick.R.menu;

import datastoragehandler.IActionsListDataHandler;
import datastoragehandler.sqlite.ActionListSQliteOpenHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActionListActivity extends Activity {

	private IActionsListDataHandler dataHandler = null;
	
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

//    private void setListeners()
//    {
//    	//listener to detect clicking on a single action in the list
//    	listView_allActions.setOnItemClickListener(new OnItemClickListener()
//	    {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) 
//            {
//            	Action selectedAction = (Action) listView_allActions.getItemAtPosition(pos);
//            	
//    	        Intent intent = new Intent(ActionListActivity.this, AddOrEditActionActivity.class);
//            	intent.putExtra(Constants.EXTRA_ACTION_ACTIVITY_STATE, EnumActionActivityState.EDIT);
//            	intent.putExtra(Constants.EXTRA_ACTION_TO_EDIT, selectedAction);
//            	startActivity(intent);
//            }	
//	    });
//    }
    
    //Button Actions
    public void addNewAction(View view)
    {
    	Intent intent = new Intent(this, AddOrEditActionActivity.class);

    	//add the data handler to the intent
    	//intent.putExtra(EXTRA_DATAHANDLER, dataHandler);
    	intent.putExtra(Constants.EXTRA_ACTION_ACTIVITY_STATE, EnumActionActivityState.ADD);
    	intent.putExtra(Constants.EXTRA_ACTION_TO_EDIT, (Action)null);
    	startActivity(intent);
    }

    //Helper functions
    private void populateActionList()
    {
    	ActionListAdapter adapter = new ActionListAdapter(this);
    	adapter.setActionList(dataHandler.getAllActions());
    	listView_allActions.setAdapter(adapter);
    	
//        ArrayAdapter<Action> arrayAdapter = new ArrayAdapter<Action>(
//        	this,
//        	android.R.layout.simple_list_item_1,
//            dataHandler.getAllActions()	
//        ); 
//        listView_allActions.setAdapter(arrayAdapter);
    }
}
