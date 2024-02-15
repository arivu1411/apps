package DataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Adapter.ToDoLists;

public class ToDoDataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    //database name
    private static final String DATABASE_NAME="TODODATA_BASE";
    //version of database
    private static final int VERSION=1;

    //table name
    private static final String TABLE_TODO="TodoTable";
    //column names
    private static final String TASK="task";
    private static final String ID="id";
    private static final String STATUS="status";

    private static final String CREATE_TABLE=" CREATE TABLE "+ TABLE_TODO + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK +" TEXT,"  + STATUS + " INTEGER)";



    private SQLiteDatabase db;

    public ToDoDataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
     onCreate(db);
    }

    //open database
   public void openDatabase(){
        database=this.getWritableDatabase();
   }
   //insert task

    public void insertTask(ToDoLists task){
        ContentValues values=new ContentValues();
        values.put(TASK,task.getTask());
        values.put(STATUS,0);
        database.insert(TABLE_TODO,null,values);
    }

    //get all tasks
    @SuppressLint("Range")
    public List<ToDoLists> getAllTasks(){
        List<ToDoLists> toDoLists=new ArrayList<>();
        Cursor cursor=null;
        database.beginTransaction();
        try {
            cursor= database.query(TABLE_TODO, null, null, null, null, null, null,null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ToDoLists task = new ToDoLists();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        toDoLists.add(task);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            database.endTransaction();
            assert cursor!=null;
            cursor.close();
        }
        return toDoLists;
    }

    //update status
    public void updateStatus(int id,int status){
        ContentValues values=new ContentValues();
        values.put(STATUS,status);
        database.update(TABLE_TODO,values,ID+"=?",new String[]{String.valueOf(id)});
    }
    //update task
    public void updateTask(int id,int task){
        ContentValues values=new ContentValues();
        values.put(TASK,task);
        database.update(TABLE_TODO,values,ID+"=?",new String[]{String.valueOf(id)});
    }
    //delete task
    public void deleteTask(int id){
        database.delete(TABLE_TODO,ID+"=?",new String[]{String.valueOf(id)});
    }
}
