package com.studentremarkscalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenneth Rey on 12/1/2016.
 */
public class DatabaseHelper {

    //Database Schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentRecords";


    //Define Table Name
    private static final String TABLE_STUDENT = "studentTable";


    //Define Columns for studentTable
    private static final String STUDENT_ID = "ID";
    private static final String STUDENT_FIRST_NAME = "firstname";
    private static final String STUDENT_LAST_NAME = "lastname";
    private static final String STUDENT_PRELIM = "PG";
    private static final String STUDENT_MIDTERM = "MG";
    private static final String STUDENT_FINALS = "FG";
    private static final String STUDENT_PROGRAM = "program";


    //Define Student Table Description
    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE "
            + TABLE_STUDENT + " ("
            + STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDENT_FIRST_NAME + " TEXT, "
            + STUDENT_LAST_NAME + " TEXT, "
            + STUDENT_PROGRAM + " TEXT, "
            + STUDENT_PRELIM + " INTEGER, "
            + STUDENT_MIDTERM + " INTEGER, "
            + STUDENT_FINALS + " INTEGER)";

    private DBHelper dbHelper;
    private Context dbContext;
    private SQLiteDatabase dbDatabase;




    // In error Select the error> On the Red Bulb> Click then Implement Method

    private static class DBHelper extends SQLiteOpenHelper {

        // Right Click> Generate> Constructor> Context Only the Content
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_STUDENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXIST " + CREATE_TABLE_STUDENT);
        }
    }


    //Right Click> Generate> Constructor> Context Only
    public DatabaseHelper(Context dbContext) {
        this.dbContext = dbContext;
    }


    public DatabaseHelper openDatabase() {
        dbHelper = new DBHelper(dbContext);
        dbDatabase = dbHelper.getWritableDatabase(); //can read and write
        return this;
    }

    public void closeDatabase(){ //to close the database connection
        dbHelper.close();
    }
    public void addNewStudent(StudentClass studObj){
        ContentValues values = new ContentValues();
        values.put(STUDENT_FIRST_NAME,studObj.getFirstName());
        values.put(STUDENT_LAST_NAME,studObj.getLastName());
        values.put(STUDENT_PRELIM,studObj.getPG());
        values.put(STUDENT_MIDTERM,studObj.getMG());
        values.put(STUDENT_FINALS,studObj.getFG());
        values.put(STUDENT_PROGRAM,studObj.getProgram());
        dbDatabase.insert(TABLE_STUDENT, null, values);
    }

    public ArrayList<StudentClass> getAllStudents(){

        ArrayList<StudentClass> studentList = new ArrayList<StudentClass>();
        String query= "SELECT * FROM " + TABLE_STUDENT +  " order by "
                + STUDENT_ID;
        Cursor cursor = dbDatabase.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                int average;
                int pg,mg,fg;
                String Remarks;
                pg = cursor.getInt(cursor.getColumnIndex(STUDENT_PRELIM));
                mg = cursor.getInt(cursor.getColumnIndex(STUDENT_MIDTERM));
                fg = cursor.getInt(cursor.getColumnIndex(STUDENT_FINALS));
                average = pg+mg+fg;

                StudentClass student = new StudentClass();
                student.setSid(cursor.getInt(cursor.getColumnIndex(STUDENT_ID)));
                student.setLastName(cursor.getString(cursor.getColumnIndex(STUDENT_LAST_NAME)));
                student.setFirstName(cursor.getString(cursor.getColumnIndex(STUDENT_FIRST_NAME)));
                student.setProgram(cursor.getString(cursor.getColumnIndex(STUDENT_PROGRAM)));
                student.setPG(cursor.getInt(cursor.getColumnIndex(STUDENT_PRELIM)));
                student.setMG(cursor.getInt(cursor.getColumnIndex(STUDENT_MIDTERM)));
                student.setFG(cursor.getInt(cursor.getColumnIndex(STUDENT_FINALS)));
                student.setAverage(average / 3);
                if (average/3 < 49){
                    student.setRemarks("Fail");
                    Remarks = "Fail";
                }else{
                    student.setRemarks("Pass");
                    Remarks = "Pass";
                }

                //       myarray.add(Integer.toString(cursor.getInt(cursor.getColumnIndex(STUDENT_ID)))+" "+cursor.getString(cursor.getColumnIndex(STUDENT_LAST_NAME))+" "+Integer.toString(average/3)+" "+Remarks );

                studentList.add(student);




            }while (cursor.moveToNext());



        }
        return studentList;
    }

    public StudentClass searchStudent(Integer studID){
        StudentClass student =new StudentClass();
        String query= "SELECT * FROM " + TABLE_STUDENT +  " WHERE  "
                + STUDENT_ID + " = " + studID  ;
        Cursor cursor = dbDatabase.rawQuery(query,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            student.setSid(cursor.getInt(cursor.getColumnIndex(STUDENT_ID)));
            student.setLastName(cursor.getString(cursor.getColumnIndex(STUDENT_LAST_NAME)));
            student.setFirstName(cursor.getString(cursor.getColumnIndex(STUDENT_FIRST_NAME)));
            student.setProgram(cursor.getString(cursor.getColumnIndex(STUDENT_PROGRAM)));
            student.setPG(cursor.getInt(cursor.getColumnIndex(STUDENT_PRELIM)));
            student.setMG(cursor.getInt(cursor.getColumnIndex(STUDENT_MIDTERM)));
            student.setFG(cursor.getInt(cursor.getColumnIndex(STUDENT_FINALS)));

            return student;
        }
        return null;
    }

    public boolean deleteRecord(Integer id) {
        int result = dbDatabase.delete(TABLE_STUDENT,STUDENT_ID+" = ?",new String[] {String.valueOf(id)});
        dbDatabase.close();
        if (result==1)
            return true;
        else
            return false;
    }


    public void updateRecord(StudentClass studObj){
        ContentValues values = new ContentValues();
        values.put(STUDENT_FIRST_NAME,studObj.getFirstName());
        values.put(STUDENT_LAST_NAME,studObj.getLastName());
        values.put(STUDENT_PROGRAM,studObj.getProgram());
        values.put(STUDENT_PRELIM, studObj.getPG());
        values.put(STUDENT_MIDTERM, studObj.getMG());
        values.put(STUDENT_FINALS, studObj.getFG());
        values.put(STUDENT_ID, studObj.getSid());
        int s= dbDatabase.update(TABLE_STUDENT, values, STUDENT_ID + " = " + studObj.getSid(), null);
        //Log.d(" --",studObj.getSid()+" "+studObj.getLastName().toString()+ " "+ s);

    }

}
