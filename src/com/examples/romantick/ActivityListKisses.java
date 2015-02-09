package com.examples.romantick;

import java.util.List;

import model.Action;
import model.Kiss;
import model.filters.FilterKissesBase;
import model.filters.FiltersManager;
import utils.adapters.AdapterKissList;
import utils.enums.EnumAddOrEditState;
import utils.general.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.romantick.R;

import datastoragehandler.IDataHandlerKisses;
import datastoragehandler.sqlite.SQLiteOpenHelperKisses;

public class ActivityListKisses extends Activity 
{
	private IDataHandlerKisses dataHandler = null;
	private AdapterKissList kissListAdapter = null;
	
	//controls
	//private CheckBox checkBox_applyFilters = null;
	private Switch switch_Filters = null;
	private ListView listView_allKisses = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kisses);

        //initialise
        initialiseControls();
        
        //create the data handler
        dataHandler = SQLiteOpenHelperKisses.getInstance(this);
        
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
    	
    	//populate the list of actions
    	populateKissList();
    }
    
    //Initialise
    private void initialiseControls()
    {
    	//checkBox_applyFilters = (CheckBox) findViewById(R.id.checkBox_applyFilters);
    	switch_Filters = (Switch) findViewById(R.id.switch_Filters);
    	listView_allKisses = (ListView) findViewById(R.id.listView_allKisses);
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
    	//if(checkBox_applyFilters.isChecked())
    	if(switch_Filters.isChecked())
    	{
    		setFilters(view);
    	}
    	else
    	{
    		clearFilters();
    	}
    }
    public void setFilters(View view)
    {
    	Intent intent = new Intent(this, ActivityFilterKisses.class);

    	//add the data handler to the intent
    	startActivity(intent);
    } 
    
    //Helper functions
    private void populateKissList()
    {
    	List<Kiss> kissList = dataHandler.getAllKisses();
    	
    	//Apply filters
    	List<FilterKissesBase> filters = FiltersManager.getKissesFilters();
    	if(filters != null && filters.size() > 0)
    	{
    		for(FilterKissesBase filter : filters)
    		{
    			filter.applyFilter(kissList);
    		}
    		//checkBox_applyFilters.setChecked(true);
    	    switch_Filters.setChecked(true);
    	}
    	else
    	{
    		//checkBox_applyFilters.setChecked(false);
    		switch_Filters.setChecked(false);
    	}
    	
    	//Display kisses
    	kissListAdapter.setKissList(kissList);
    	listView_allKisses.setAdapter(kissListAdapter);
    }
    private void clearFilters()
    {
    	Log.d("TAG", "clearing filters");
    	FiltersManager.clearKissesFilters();
    	populateKissList();
    }

    //Getters and setters
    public IDataHandlerKisses getDataHandler()
    {
    	return dataHandler;
    }
}
