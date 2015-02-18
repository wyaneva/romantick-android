package com.examples.romantick;

import java.util.List;

import model.Action;
import model.filters.FilterBase;
import model.filters.FiltersManagerActions;
import utils.adapters.AdapterActionList;
import utils.enums.EnumAddOrEditState;
import utils.general.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.romantick.R;

import datastoragehandler.IDataHandlerActions;
import datastoragehandler.sqlite.SQLiteOpenHelperActions;

public class ActivityListActions extends Activity {

	private IDataHandlerActions dataHandler = null;
	private AdapterActionList actionListAdapter;
	private FiltersManagerActions filtersManager = null;
	
	//controls
	private ListView listView_allActions = null;
	private TextView textView_doneStatusFilter = null;
	private TextView textView_locationFilter = null;
	private Switch switch_Filters = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_actions);

        //initialise
        initialiseControls();
        
        //create the data handler
        dataHandler = SQLiteOpenHelperActions.getInstance(this);
        filtersManager = FiltersManagerActions.getInstance(this);
        
        //create the Action List Adapter
        actionListAdapter = new AdapterActionList(this);

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
    	
    	//set filters view
    	setFiltersView();
    	
    	//populate the list of actions
    	populateActionList();
    }
    
    //Initialise
    private void initialiseControls()
    {
    	listView_allActions = (ListView) findViewById(R.id.listView_allActions);
    	textView_doneStatusFilter = (TextView) findViewById(R.id.textView_ActionDoneStatusFilter);
    	textView_locationFilter = (TextView) findViewById(R.id.textView_ActionLocationFilter);
    	switch_Filters = (Switch) findViewById(R.id.switch_FiltersAction);
    }

    //Button Actions
    public void addNewAction(View view)
    {
    	Intent intent = new Intent(this, ActivityAddOrEditAction.class);

    	//add the data handler to the intent
    	intent.putExtra(Constants.EXTRA_ADD_OR_EDIT_STATE, EnumAddOrEditState.ADD);
    	intent.putExtra(Constants.EXTRA_TODO_TO_EDIT, (Action)null);
    	startActivity(intent);
    }
    public void setFilters(View view)
    {
    	Intent intent = new Intent(this, ActivityFilterActions.class);

    	//add the data handler to the intent
    	startActivity(intent);
    }
    public void setOrClearFilters(View view)
    {
    	filtersManager.setFiltersOn(switch_Filters.isChecked());
    	populateActionList();
    }
    
    //Helper functions
    private void setFiltersView()
    {
    	//Done status filter
    	FilterBase<Action> doneStatusFilter = filtersManager.getFilterActionsDoneStatus();
    	if(doneStatusFilter.isApplied())
    	{
    		textView_doneStatusFilter.setText(doneStatusFilter.getDisplayString());
    		textView_doneStatusFilter.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		textView_doneStatusFilter.setText("");
    		textView_doneStatusFilter.setVisibility(View.GONE);
    	}
    	
    	//Date filter
    	FilterBase<Action> locationFilter = filtersManager.getFilterActionsLocation();
    	if(locationFilter.isApplied())
    	{
    		textView_locationFilter.setText(locationFilter.getDisplayString());
    		textView_locationFilter.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		textView_locationFilter.setText("");
    		textView_locationFilter.setVisibility(View.GONE);
    	}
    }
    private void populateActionList()
    {
    	List<Action> actionList = dataHandler.getAllActions();
    	
    	//Apply filters
    	if(filtersManager.filtersOn())
    	{
    		List<FilterBase<Action>> filters = filtersManager.getFilters();
    		for(FilterBase<Action> filter : filters)
    		{
    			if(filter.isApplied())
    			{
    				filter.applyFilter(actionList);
    			}
    		}
    	}
    	
    	actionListAdapter.setActionList(actionList);
    	listView_allActions.setAdapter(actionListAdapter);
    }

    //Getters and setters
    public IDataHandlerActions getDataHandler()
    {
    	return dataHandler;
    }
}
