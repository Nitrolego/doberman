package com.example.doberman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_EXPENSE_NAME = "EXPENSE_NAME";
    public static final String COLUMN_EXPENSE_DATE = "EXPENSE_DATE";
    public static final String COLUMN_EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "expenses.db", null, 1);


    }

    //called when database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXPENSE_NAME + " TEXT, " + COLUMN_EXPENSE_DATE + " TEXT, " + COLUMN_EXPENSE_AMOUNT + " FLOAT) ";

        db.execSQL(createTableStatement);
    }

    //helps backward compatibility
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ExpenseModel expenseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPENSE_NAME, expenseModel.getExpenseName());
        cv.put(COLUMN_EXPENSE_DATE, expenseModel.getExpensedate());
        cv.put(COLUMN_EXPENSE_AMOUNT, expenseModel.getExpenseamount());

        long insert = db.insert(EXPENSE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteOne(ExpenseModel expenseModel) {
        //find expense in database, if it is found, delete it and return true.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + COLUMN_ID + " =" + expenseModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean editOne(ExpenseModel expenseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String queryString = "UPDATE " + EXPENSE_TABLE + " WHERE " + COLUMN_ID + " =" + expenseModel.getId();
        //this string is giving me trouble. once i fix this, i should be able to update. maybe add a new button. i should backt his up.

        //where id = id.selected

        cv.put(COLUMN_EXPENSE_NAME, expenseModel.getExpenseName());
        cv.put(COLUMN_EXPENSE_DATE, expenseModel.getExpensedate());
        cv.put(COLUMN_EXPENSE_AMOUNT, expenseModel.getExpenseamount());

        long update = db.update(EXPENSE_TABLE, cv, queryString, null);
        if (update == -1) {
            return false;
        }
        else {
            return true;
        }

        //this is the project for tomorrow.
    }

    public List<ExpenseModel> getEverything() {
        List<ExpenseModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + EXPENSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through cursor create new expense objects.
            do {
                int expenseID = cursor.getInt(0);
                String expenseName = cursor.getString(1);
                String expenseDate = cursor.getString(2);
                float expenseAmount = cursor.getFloat(3);

                ExpenseModel newExpense = new ExpenseModel(expenseID, expenseName, expenseDate, expenseAmount);
                returnList.add(newExpense);
            } while (cursor.moveToNext());
        }
        else {
            //failure
        }

        cursor.close();
        db.close();
        return returnList;

    }
}
