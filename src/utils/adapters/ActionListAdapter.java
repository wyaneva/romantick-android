package utils.adapters;

import java.util.List;

import model.Action;
import utils.general.Constants;
import utils.general.EnumActionActivityState;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.romantick.R;
import com.examples.romantick.ActivityListActions;
import com.examples.romantick.ActivityAddOrEditAction;

import datastoragehandler.sqlite.SQLiteOpenHelperActions;

public class ActionListAdapter extends BaseAdapter
{
	ActivityListActions actionListActivity;
	List<Action> actionsList;
	
	public ActionListAdapter(ActivityListActions _activity)
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

        final TextView summaryTextView = (TextView) view.findViewById(R.id.textView_ActionListSummary);
        summaryTextView.setText(action.getSummary());
        summaryTextView.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
    	        Intent intent = new Intent(actionListActivity, ActivityAddOrEditAction.class);
            	intent.putExtra(Constants.EXTRA_ACTION_ACTIVITY_STATE, EnumActionActivityState.EDIT);
            	intent.putExtra(Constants.EXTRA_ACTION_TO_EDIT, action);
            	actionListActivity.startActivity(intent);
            }
        });
 
        final CheckBox isDoneCheckBox = (CheckBox) view.findViewById(R.id.checkBox_isDone);
        isDoneCheckBox.setChecked(action.isDone());
        isDoneCheckBox.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				action.setDone(isDoneCheckBox.isChecked());
				SQLiteOpenHelperActions.getInstance(actionListActivity).updateAction(action);
			}
		}); 
 
        return view;
    }
}
