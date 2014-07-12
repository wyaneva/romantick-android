package com.examples.romantick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.example.romantick.R;

public class MainActivity extends Activity
{
	//controls
	Button button_ToDo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initialise
		initialiseControls();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}
	
	//Initialise
	private void initialiseControls()
	{
		button_ToDo = (Button) findViewById(R.id.button_ToDo);
	}
	
	//Button Actions
    public void showToDoList(View view)
    {
    	Intent intent = new Intent(this, ActivityListActions.class);
    	startActivity(intent);
    }
    
    public void showKissLog(View view)
    {
    	Intent intent = new Intent(this, ActivityListKisses.class);
    	startActivity(intent);
    }
    
    public void showTravelIdeasList(View view)
    {
    	//TODO
    }
}
