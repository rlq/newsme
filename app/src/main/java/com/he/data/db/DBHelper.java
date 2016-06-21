package com.he.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.he.config.KeyConfig;
import com.he.data.frist.weather.ChannelBean;
import com.he.func.frist.channel.ChannelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "he.db";// 数据库名称
	public static final int VERSION = 1;
	
	public static final String TABLE_CHANNEL = "channel";//数据表

	public static final String ID = "id";//
	public static final String NAME = "name";
	public static final String ORDERID = "orderId";
	public static final String SELECTED = "selected";
	private Context context;
	SQLiteDatabase mDatabase;
	private static DBHelper instance;

	public synchronized static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context.getApplicationContext());
		}
		return instance;
	}

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	public Context getContext(){
		return context;
	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		context = new DatabaseContext(context);
		if (DB_NAME == null ) {
			mDatabase = SQLiteDatabase.create(null);
		} else {
			mDatabase = context.openOrCreateDatabase(DB_NAME, 0, null, new DatabaseErrorHandler() {
				@Override
				public void onCorruption(SQLiteDatabase arg0) {
					Log.i(KeyConfig.TAG_NAME, "openOrCreateDatabase onCorruption");
				}
			});
			if (mDatabase != null) {
				onCreate(mDatabase);
			}
		}
		return mDatabase;//super.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table if not exists "+TABLE_CHANNEL +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ID + " INTEGER , " +
				NAME + " TEXT , " +
				ORDERID + " INTEGER , " +
				SELECTED + " SELECTED)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 更改数据库版本的操作
		Log.e(KeyConfig.TAG_NAME, "onUpgrade");
		onCreate(db);
	}

	public boolean addData(ChannelBean item) {
		boolean flag = false;
		long id = -1;
		try {
			SQLiteDatabase mDatabase = getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("name", item.getName());
			values.put("id", item.getId());
			values.put("orderId", item.getOrderId());
			values.put("selected", item.getSelected());
			id = mDatabase.insert(TABLE_CHANNEL, null, values);
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
			Log.e(KeyConfig.TAG_NAME, e.toString());
		} finally {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
		return flag;
	}

	@SuppressWarnings("unused")
	public boolean deleteData(String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		int count = 0;
		try {
			SQLiteDatabase database = getWritableDatabase();
			count = database.delete(ChannelModel.TABLE_CHANNEL, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			Log.e(KeyConfig.TAG_NAME, e.toString());
		} finally {
			if (mDatabase != null) {
				mDatabase.close();
			}
		}
		return flag;
	}

	@SuppressWarnings("unused")
	public boolean updateData(ContentValues values, String whereClause,String[] whereArgs) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SQLiteDatabase database = null;
		int count = 0;
		try {
			database = getWritableDatabase();
			count = database.update(ChannelModel.TABLE_CHANNEL, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			Log.e(KeyConfig.TAG_NAME, e.toString());
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	@SuppressWarnings("unused")
	public Map<String, String> getData(String selection, String[] selectionArgs) {
		SQLiteDatabase database = null;
		Cursor cursor = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			database = getReadableDatabase();
			cursor = database.query(true, TABLE_CHANNEL, null, selection,
					selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					map.put(cols_name, cols_values);
				}
			}
		} catch (Exception e) {
			Log.e(KeyConfig.TAG_NAME, e.toString());
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return map;
	}

	public List<Map<String, String>> getListData(String selection, String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Cursor cursor = null;
		try {
			SQLiteDatabase database = getReadableDatabase();
			cursor = database.query(false, TABLE_CHANNEL, null,
					selection,selectionArgs, null, null, null, null);
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					String cols_values = cursor.getString(cursor
							.getColumnIndex(cols_name));
					if (cols_values == null) {
						cols_values = "";
					}
					map.put(cols_name, cols_values);
				}
				list.add(map);
			}
		} catch (Exception e) {
			Log.e(KeyConfig.TAG_NAME, e.toString());
		}
		return list;
	}

	public void clearFeedTable() {
		SQLiteDatabase database = getWritableDatabase();
		String sql = "DELETE FROM " + ChannelModel.TABLE_CHANNEL + ";";
		database.execSQL(sql);
		revertSeq();
	}

	private void revertSeq() {
		SQLiteDatabase database = getWritableDatabase();
		String sql = "update sqlite_sequence set seq=0 where name='"
				+ ChannelModel.TABLE_CHANNEL + "'";
		database.execSQL(sql);
	}

	public synchronized void close() {
		try {
			this.getWritableDatabase().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
