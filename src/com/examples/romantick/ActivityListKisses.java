package com.examples.romantick;

import java.util.List;

import model.Action;
import model.Kiss;
import model.filters.FilterBase;
import model.filters.FiltersManagerKisses;
import utils.adapters.AdapterKissList;
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

import datastoragehandler.IDataHandlerKisses;
import datastoragehandler.sqlite.SQLiteOpenHelperKisses;

public class ActivityListKisses extends Activity 
{
	private IDataHandlerKisses dataHandler = null;
	private AdapterKissList kissListAdapter = null;
	private FiltersManagerKisses filtersManager = null;
	
	//controls
	private ListView listView_allKisses = null;
	private Switch switch_Filters = null;
	private TextView textView_doneStatusFilter = null;
	private TextView textView_dateFilter = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kisses);

        //initialise
        initialiseControls();
        
        //create the data handler
        dataHandler = SQLiteOpenHelperKisses.getInstance(this);
        filtersManager = FiltersManagerKisses.getInstance(this);
        
        //create the Action List Adapter
        kissListAdapter = new AdapterKissList(this);

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
    	populateKissList();
    }
    
    //Initialise
    private void initialiseControls()
    {
    	listView_allKisses = (ListView) findViewById(R.id.listView_allKisses);
    	textView_doneStatusFilter = (TextView) findViewById(R.id.textView_DoneStatusFilter);
    	textView_dateFilter = (TextView) findViewById(R.id.textView_DateFilter);
    	switch_Filters = (Switch) findViewById(R.id.switch_Filters);
    }

    //Button Actions
    public void addNewKiss(View view)
    {
    	Intent intent = new Intent(this, ActivityAddOrEditKiss.class);

    	//add the data handler to the intent
    	intent.putExtra(Constants.EXTRA_ADD_OR_EDIT_STATE, EnumAddOrEditState.ADD);
    	intent.putExtra(Constants.EXTRA_TODO_TO_EDIT, (Action)null);
    	startActivity(intent);
    }
    public void setOrClearFilters(View view)
    {
    	filtersManager.setFiltersOn(switch_Filters.isChecked());
    	populateKissList();
    }
    public void setFilters(View view)
    {
    	Intent intent = new Intent(this, ActivityFilterKisses.class);

    	//add the data handler to the intent
    	startActivity(intent);
    } 
    
    //Helper functions
    private void setFiltersView()
    {
    	//Done status filter
    	FilterBase<Kiss> doneStatusFilter = filtersManager.getFilterKissesDoneStatus();
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
    	FilterBase<Kiss> dateFilter = filtersManager.getFilterKissesDate();
    	if(dateFilter.isApplied())
    	{
    		textView_dateFilter.setText(dateFilter.getDisplayString());
    		textView_dateFilter.setVisibility(View.VISIBLE);
    	}
    	else
    	{
    		textView_dateFilter.setText("");
    		textView_dateFilter.setVisibility(View.GONE);
    	}
    }
    private void populateKissList()
    {
    	List<Kiss> kissList = dataHandler.getAllKisses();
    	
    	//Apply filters
    	if(filtersManager.filtersOn())
    	{
    	    List<FilterBase<Kiss>> filters = filtersManager.getFilters();
    	    for(FilterBase<Kiss> filter : filters)
    	    {
    		    if(filter.isApplied())
    		    {
    		        filter.applyFilter(kissList);
    		    }
    	    }
    	}	
    	
    	//Display kisses
    	kissListAdapter.setKissList(kissList);
    	listView_allKisses.setAdapter(kissListAdapter);
    }

    //Getters and setters
    public IDataHandlerKisses getDataHandler()
    {
    	return dataHandler;
    }
}
