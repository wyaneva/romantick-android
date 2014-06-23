package com.example.romantick.test;

import utils.EnumActionActivityState;

import com.examples.romantick.AddOrEditActionActivity;
import com.examples.romantick.MainActivity;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.test.*;
import android.widget.Button;
import android.widget.ListView;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private Instrumentation instrumentation;
	private ActivityMonitor addOrEditActivityMonitor;
	
	private MainActivity mainActivity;
	private ListView actionsList;
	private Button addNewButton;

	private AddOrEditActionActivity addOrEditActionActivity;

	public MainActivityTest() 
	{
		super(MainActivity.class);
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

		addOrEditActionActivity = (AddOrEditActionActivity) addOrEditActivityMonitor.getLastActivity();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		//TODO: Extend
	}

	public void testInitialise()
	{
		//expect the inital number of actions in the list to be 0
		assertEquals(0, actionsList.getCount());
	}

	public void testAddNew()
	{
		noFinishTestAddNew();
		addOrEditActionActivity.finish();
	}

	private void noFinishTestAddNew()
	{
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
	}
}
