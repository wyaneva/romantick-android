package datastoragehandler.sqlite;

import java.util.LinkedList;
import java.util.List;

import datastoragehandler.IDataHandler;

import model.Action;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.OpenableColumns;

public class ActionListSQliteOpenHelper extends SQLiteOpenHelper implements IDataHandler {

	private static ActionListSQliteOpenHelper singleton = null;
	
	public static ActionListSQliteOpenHelper getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new ActionListSQliteOpenHelper(context);
		}
		
		return singleton;
	}
	private ActionListSQliteOpenHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void addAction(Action action)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, action.getSummary());

		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	public void updateAction(Action action) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, action.getSummary());
		
		db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] {String.valueOf(action.getId())});

		db.close();
	}
	public void deleteAction(Action action)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] {String.valueOf(action.getId())});

		db.close();
	}
	public List<Action> getAllActions()
	{
		List<Action> result = new LinkedList<Action>();

		String query = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Action action = null;
		if(cursor.moveToFirst())
		{
			do
			{
				action = new Action();
				action.setId(Integer.parseInt(cursor.getString(KEY_ID_INDEX)));
				action.setSummary(cursor.getString(KEY_SUMMARY_INDEX));
				
				result.add(action);
			}
			while (cursor.moveToNext());
		}

		return result;
	}

	//SQLiteOpenHelper methods
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		this.onCreate(db);
	}

	//--------------------------------
	private static final long 	serialVersionUID = 1L;
	private static final int 	DATABASE_VERSION 	= 2;
	private static final String DATABASE_NAME 		= "ActionListDB";
	private static final String TABLE_NAME 			= "actions";
	private static final String TABLE_CREATE 		= "CREATE TABLE " + TABLE_NAME + " (" +
						"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"summary TEXT )";

	//Actions table columns
	private static final String KEY_ID				= "id";
	private static final int	KEY_ID_INDEX		= 0;
	private static final String KEY_SUMMARY			= "summary";
	private static final int	KEY_SUMMARY_INDEX 	= 1;
}
