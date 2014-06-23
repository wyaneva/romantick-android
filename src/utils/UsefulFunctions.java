package utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

public class UsefulFunctions 
{
	public static void showToast(Context context, String message)
	{
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.getView().setBackgroundColor(Color.rgb(255, 165, 0)); //set the background colour to orange
		toast.show();
	}
}
