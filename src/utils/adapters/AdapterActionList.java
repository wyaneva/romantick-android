package utils.adapters;

import java.util.List;

import model.Action;
import utils.general.Constants;
import utils.general.EnumAddOrEditState;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.romantick.R;
import com.examples.romantick.ActivityAddOrEditAction;
import com.examples.romantick.ActivityListActions;

public class AdapterActionList extends BaseAdapter
{
	ActivityListActions actionListActivity;
	List<Action> actionsList;
	
	public AdapterActionList(ActivityListActions _activity)
	{
		actionListActivity = _activity;
	}
	
	public void setActionList(List<Action> actions)
	{
		actionsList = actions;
	}
	
	@Override
	public int getCount() {
		return actionsList.size();
	}

	@Override
	public Object getItem(int position) {
		return actionsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
	    LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.list_item_action, parent, false);
		
		final Action action = actionsList.get(position);
       
		//Listener for when we click in the item
        final LinearLayout summaryLayout = (LinearLayout) view.findViewById(R.id.linearLayout_ActionListTextViewsLayout);
        summaryLayout.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
    	        Intent intent = new Intent(actionListActivity, ActivityAddOrEditAction.class);
            	intent.putExtra(Constants.EXTRA_ADD_OR_EDIT_STATE, EnumAddOrEditState.EDIT);
            	intent.putExtra(Constants.EXTRA_TODO_TO_EDIT, action);
            	actionListActivity.startActivity(intent);
            }
        });
        
        //Summary
        final TextView summaryTextView = (TextView) view.findViewById(R.id.textView_ActionSummary);
        summaryTextView.setText(action.getSummary());
 
        //Done checkbox
        final CheckBox isDoneCheckBox = (CheckBox) view.findViewById(R.id.checkBox_ActionIsDone);
        isDoneCheckBox.setChecked(action.isDone());
        isDoneCheckBox.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				action.setDone(isDoneCheckBox.isChecked());
				actionListActivity.getDataHandler().updateAction(action);
			}
		}); 
 
        return view;
    }
}
