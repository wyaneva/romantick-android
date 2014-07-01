package com.example.romantick.test;

import utils.EnumActionActivityState;

import com.examples.romantick.AddOrEditActionActivity;

import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

public class AddOrEditActionTest extends ActivityUnitTestCase<AddOrEditActionActivity> 
{
	AddOrEditActionActivity addOrEditActionActivity;
	Button saveButton;
	EditText summaryEditText;

	public AddOrEditActionTest() 
	{
		super(AddOrEditActionActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception 
	{
		super.setUp();
		
		addOrEditActionActivity = getActivity();

		assertNotNull(addOrEditActionActivity);
		saveButton = (Button) addOrEditActionActivity.findViewById(com.example.romantick.R.id.button_Save);
		summaryEditText = (EditText) addOrEditActionActivity.findViewById(com.example.romantick.R.id.editText_Summary);

	}

	public void testSaveInAddState()
	{
		//test save button in ADD state
		addOrEditActionActivity.setState(EnumActionActivityState.ADD);
		assertEquals(EnumActionActivityState.ADD, addOrEditActionActivity.getState());

		summaryEditText.setText("Test 1");
		
		addOrEditActionActivity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				saveButton.performClick();
			}
		});
	}
}