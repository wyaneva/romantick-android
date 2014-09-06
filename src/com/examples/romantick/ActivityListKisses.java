package com.examples.romantick;

import java.util.List;

import model.Action;
import model.Kiss;
import model.filters.FilterKissesBase;
import utils.adapters.AdapterKissList;
import utils.general.Constants;
import utils.general.EnumAddOrEditState;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.romantick.R;

import datastoragehandler.IDataHandlerKisses;
import datastoragehandler.sqlite.SQLiteOpenHelperKisses;

public class ActivityListKisses extends Activity 
{
	private IDataHandlerKisses dataHandler = null;
	private AdapterKissList kissListAdapter = null;
	
	private List<FilterKissesBase> filters = null;
	
	//controls
	private TextView textView_filtersLabel = null;
	private GridLayout gridLayout_filters = null;
	private ListView listView_allKisses = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kisses);

        //initialise
        initialiseControls();
        setListeners();
        
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
    	textView_filtersLabel = (TextView) findViewById(R.id.textView_filters);
    	listView_allKisses = (ListView) findViewById(R.id.listView_allKisses);
    	gridLayout_filters = (GridLayout) findViewById(R.id.gridLayout_filters);
    }
    
    private void setListeners()
    {
    	textView_filtersLabel.setOnClickListener(
    			new OnClickListener() {
					
					@Override
					public void onClick(View v) 
					{
						setFilters();
					}
				});
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
    
    public void clearFilters(View view)
    {
    	Log.d("TAG", "clearing filters");
    }

    //Helper functions
    private void populateKissList()
    {
    	List<Kiss> kissList = dataHandler.getAllKisses();
    	
    	if(filters != null)
    	{
    		Log.d("TAG", "filters to apply");
    		for(FilterKissesBase filter : filters)
    		{
    			kissList = filter.applyFilter(kissList);
    		}
    	}
    	
    	kissListAdapter.setKissList(kissList);
    	listView_allKisses.setAdapter(kissListAdapter);
    }
    public void setFilters()
    {
    	Log.d("TAG", "adding filters");
    	
    	Intent intent = new Intent(this, ActivityFilterKisses.class);

    	//add the data handler to the intent
    	startActivity(intent);
    } 

    //Getters and setters
    public IDataHandlerKisses getDataHandler()
    {
    	return dataHandler;
    }
}
