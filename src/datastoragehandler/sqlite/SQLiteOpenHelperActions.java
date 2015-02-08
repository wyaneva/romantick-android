package datastoragehandler.sqlite;

import java.util.LinkedList;
import java.util.List;

import model.Action;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import datastoragehandler.IDataHandlerActions;

public class SQLiteOpenHelperActions extends SQLiteOpenHelper implements IDataHandlerActions 
{
	private Context context;
	private static SQLiteOpenHelperActions singleton = null;
	
	public static SQLiteOpenHelperActions getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new SQLiteOpenHelperActions(context);
		}
		
		return singleton;
	}
	private SQLiteOpenHelperActions(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public void addAction(Action action)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, action.getSummary());
		values.put(KEY_IS_DONE, action.isDone());
		values.put(KEY_LOCATION, action.getLocation(context));

		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	public int updateAction(Action action) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, action.getSummary());
		values.put(KEY_IS_DONE, action.isDone());
		values.put(KEY_LOCATION, action.getLocation(context));
		
		int i = db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] {String.valueOf(action.getId())});
		db.close();

		return i;
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
				try 
				{
				    action = new Action();
				    action.setId(Integer.parseInt(cursor.getString(KEY_ID_INDEX)));
				    action.setSummary(cursor.getString(KEY_SUMMARY_INDEX));
				    action.setDone(Integer.parseInt(cursor.getString(KEY_IS_DONE_INDEX)));
					action.setLocation(context, cursor.getString(KEY_LOCATION_INDEX));
				} 
				catch (NumberFormatException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
	//Actions table columns
	private static final String KEY_ID				= "id";
	private static final int	KEY_ID_INDEX		= 0;
	private static final String KEY_SUMMARY			= "summary";
	private static final int	KEY_SUMMARY_INDEX 	= 1;
	private static final String	KEY_IS_DONE			= "isdone";
	private static final int	KEY_IS_DONE_INDEX	= 2;
	private static final String KEY_LOCATION		= "location";
	private static final int    KEY_LOCATION_INDEX	= 3;
	
	private static final long 	serialVersionUID = 1L;
	private static final int 	DATABASE_VERSION 	= 2;
	private static final String DATABASE_NAME 		= "ActionListDB";
	private static final String TABLE_NAME 			= "actions";
	private static final String TABLE_CREATE 		
					= "CREATE TABLE " + TABLE_NAME + " (" +
						KEY_ID 		 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_SUMMARY  + " TEXT, " +
						KEY_IS_DONE  + " INTEGER, " +
						KEY_LOCATION + " TEXT)";
}
