package com.example.romantick.test;

import model.Action;
import utils.EnumActionActivityState;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.examples.romantick.AddOrEditActionActivity;
import com.examples.romantick.ActionListActivity;

public class ActionListActivityTest extends ActivityInstrumentationTestCase2<ActionListActivity>
{
	private Instrumentation instrumentation;
	private ActivityMonitor addOrEditActivityMonitor;
	
	private ActionListActivity mainActivity;
	private ListView actionsList;
	private Button addNewButton;

	public ActionListActivityTest() 
	{
		super(ActionListActivity.class);
	}

	@Override
	protected void setUp() throws Exception 
	{
		super.setUp();

		instrumentation = getInstrumentation();
		addOrEditActivityMonitor = instrumentation.addMonitor(AddOrEditActionActivity.class.getName(), null, false);

		mainActivity = getActivity();
		actionsList = (ListView) mainActivity.findViewById(com.example.romantick.R.id.listView_allActions);
		addNewButton = (Button) mainActivity.findViewById(com.example.romantick.R.id.button_AddNew);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialise()
	{
		//expect the inital number of actions in the list to be 0
		assertEquals(0, actionsList.getCount());
	}

	public void testUpdateExisting() 
	{
		AddOrEditActionActivity addOrEditActionActivity = (AddOrEditActionActivity) addOrEditActivityMonitor.getLastActivity();
		
		//test if pressing the AddNew button starts the AddOrEditActivity
		assertNull(addOrEditActionActivity);;
		
        mainActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				//add an action to the list
				int startNum = actionsList.getCount();
				
				Action action = new Action();
				action.setId(startNum);
				action.setSummary("Test Summary " + startNum);
				
				ArrayAdapter<Action> adapter = (ArrayAdapter<Action>) actionsList.getAdapter();
				adapter.add(action);
				actionsList.setAdapter(adapter);
				
                assertEquals(startNum + 1, actionsList.getCount());
                
                actionsList.performItemClick(actionsList.getChildAt(startNum), startNum, actionsList.getAdapter().getItemId(startNum));
			}
		});
        
		addOrEditActionActivity = (AddOrEditActionActivity) instrumentation.waitForMonitorWithTimeout(addOrEditActivityMonitor, 5000);
		assertNotNull(addOrEditActionActivity);
		
		//test that the state of the activity is ADD
		assertEquals(EnumActionActivityState.EDIT, addOrEditActionActivity.getState());
		
		addOrEditActionActivity.finish();
	}
	
	public void testAddNew()
	{
		AddOrEditActionActivity addOrEditActionActivity = (AddOrEditActionActivity) addOrEditActivityMonitor.getLastActivity();
		
		//test if pressing the AddNew button starts the AddOrEditActivity
		assertNull(addOrEditActionActivity);

		mainActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				addNewButton.performClick();
			}
		});

		addOrEditActionActivity = (AddOrEditActionActivity) instrumentation.waitForMonitorWithTimeout(addOrEditActivityMonitor, 5000);
		assertNotNull(addOrEditActionActivity);
		
		//test that the state of the activity is ADD
		assertEquals(EnumActionActivityState.ADD, addOrEditActionActivity.getState());
		
		addOrEditActionActivity.finish();
	}
}
