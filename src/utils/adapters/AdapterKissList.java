package utils.adapters;

import java.util.List;

import model.Kiss;
import utils.general.Constants;
import utils.general.EnumAddOrEditState;
import utils.general.UsefulFunctions;
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
import com.examples.romantick.ActivityAddOrEditKiss;
import com.examples.romantick.ActivityListKisses;

public class AdapterKissList extends BaseAdapter
{
	private ActivityListKisses activityListKisses;
	private List<Kiss> kissesList;

	public AdapterKissList(ActivityListKisses activity)
	{
		activityListKisses = activity;
	}
	
	public void setKissList(List<Kiss> kisses)
	{
		kissesList = kisses;
	}
	
	@Override
	public int getCount() {
		return kissesList.size();
	}

	@Override
	public Object getItem(int position) {
		return kissesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
	    LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.list_item_kiss, parent, false);
		
		final Kiss kiss = kissesList.get(position);

		//Listener for when we click
		LinearLayout textViewsLayout = (LinearLayout) view.findViewById(R.id.linearLayout_KissListTextViewsLayout);
		textViewsLayout.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
    	        Intent intent = new Intent(activityListKisses, ActivityAddOrEditKiss.class);
    	        intent.putExtra(Constants.EXTRA_ADD_OR_EDIT_STATE, EnumAddOrEditState.EDIT);
            	intent.putExtra(Constants.EXTRA_TODO_TO_EDIT, kiss);
            	activityListKisses.startActivity(intent);
            }
        });
		
		//Date text
		final TextView dateTextView = (TextView) view.findViewById(R.id.textView_KissListDate);
		dateTextView.setText(UsefulFunctions.dateToString(kiss.getDate()));
		
		//Summary text
        final TextView summaryTextView = (TextView) view.findViewById(R.id.textView_KissListSummary);
        summaryTextView.setText(kiss.getSummary());

        //Done checkbox
        final CheckBox isDoneCheckBox = (CheckBox) view.findViewById(R.id.checkBox_KissIsDone);
        isDoneCheckBox.setChecked(kiss.isDone());
        isDoneCheckBox.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				kiss.setDone(isDoneCheckBox.isChecked());
				activityListKisses.getDataHandler().updateKiss(kiss);
			}
		}); 
 
        return view;
	}
}
