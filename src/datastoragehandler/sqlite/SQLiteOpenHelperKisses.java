package datastoragehandler.sqlite;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.Kiss;
import datastoragehandler.IDataHandlerKisses;

public class SQLiteOpenHelperKisses extends SQLiteOpenHelper implements IDataHandlerKisses 
{
	private static SQLiteOpenHelperKisses singleton = null;
	
	public static SQLiteOpenHelperKisses getInstance(Context context)
	{
		if(singleton == null)
		{
			singleton = new SQLiteOpenHelperKisses(context);
		}
		
		return singleton;
	}
	private SQLiteOpenHelperKisses(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void addKiss(Kiss kiss) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, kiss.getSummary());
		values.put(KEY_IS_DONE, kiss.isDone());
		values.put(KEY_DATE, kiss.getDate().getTime());

		db.insert(TABLE_NAME, null, values);
		db.close();
	}

	@Override
	public List<Kiss> getAllKisses()
	{
		List<Kiss> result = new LinkedList<Kiss>();

		String query = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Kiss kiss = null;
		if(cursor.moveToFirst())
		{
			do
			{
				kiss = new Kiss();
				kiss.setId(Integer.parseInt(cursor.getString(KEY_ID_INDEX)));
				kiss.setSummary(cursor.getString(KEY_SUMMARY_INDEX));
				kiss.setDone(Integer.parseInt(cursor.getString(KEY_IS_DONE_INDEX)));
				kiss.setDate(new Date(Long.parseLong((cursor.getString(KEY_DATE_INDEX)))));
				
				result.add(kiss);
			}
			while (cursor.moveToNext());
		}

		return result;
	}

	@Override
	public int updateKiss(Kiss kiss)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_SUMMARY, kiss.getSummary());
		values.put(KEY_IS_DONE, kiss.isDone());
		values.put(KEY_DATE, kiss.getDate().getTime());
		
		int i = db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] {String.valueOf(kiss.getId())});
		db.close();

		return i;
	}

	@Override
	public void deleteKiss(Kiss kiss)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] {String.valueOf(kiss.getId())});

		db.close();
	}
	
	@Override
	public List<Kiss> queryKissesDoneStatus(boolean status) 
	{
		List<Kiss> result = new LinkedList<Kiss>();

		String query;
		
		if(status)
		{
			query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_IS_DONE + " != '0'"; 
		}
		else
		{
			query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_IS_DONE + " = '0'"; 
		}
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		Kiss kiss = null;
		if(cursor.moveToFirst())
		{
			do
			{
				kiss = new Kiss();
				kiss.setId(Integer.parseInt(cursor.getString(KEY_ID_INDEX)));
				kiss.setSummary(cursor.getString(KEY_SUMMARY_INDEX));
				kiss.setDone(Integer.parseInt(cursor.getString(KEY_IS_DONE_INDEX)));
				kiss.setDate(new Date(Long.parseLong((cursor.getString(KEY_DATE_INDEX)))));
				
				result.add(kiss);
			}
			while (cursor.moveToNext());
		}

		return result;
	}
	
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
	private static final String KEY_DATE			= "date";
	private static final int	KEY_DATE_INDEX		= 3;
	
	private static final int 	DATABASE_VERSION 	= 2;
	private static final String DATABASE_NAME 		= "KissListDB";
	private static final String TABLE_NAME 			= "kisses";
	private static final String TABLE_CREATE 		
					= "CREATE TABLE " + TABLE_NAME + " (" +
						KEY_ID 		+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_SUMMARY + " TEXT, " +
						KEY_IS_DONE + " INTEGER, " +
						KEY_DATE + " INTEGER )";
}
