package com.example.doberman;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //references to buttons

    Button btn_viewall, btn_add, btn_news, btn_date;
    EditText et_expensename, et_date, et_expenseamount;
    ListView lv_expenselist;
    ArrayAdapter expenseArrayAdapter;
    DatabaseHelper databaseHelper;
    private int mYear, mMonth, mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewall = findViewById(R.id.btn_viewall);
        btn_news = findViewById(R.id.btn_news);
        btn_date = findViewById(R.id.btn_date);
        et_expensename = findViewById(R.id.et_expensename);
        et_date = findViewById(R.id.et_expensedate);
        et_expenseamount = findViewById(R.id.et_expenseamount);
        lv_expenselist = findViewById(R.id.lv_expenselist);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        ShowExpenseOnListView(databaseHelper);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //showdialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( MainActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });

        //button listeners for actions
        btn_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ExpenseModel expenseModel = null;
                try {
                    expenseModel = new ExpenseModel(-1, et_expensename.getText().toString(), et_date.getText().toString(), Float.parseFloat(et_expenseamount.getText().toString()));
                    //Toast.makeText( MainActivity.this, expenseModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText( MainActivity.this, "error creating expense", Toast.LENGTH_SHORT).show();
                    expenseModel = new ExpenseModel(-1, "error", "11/1/1970",0);
                    //parse string, to get an empty one to prevent error when adding one.??
                }

                DatabaseHelper dataBaseHelper = new DatabaseHelper( MainActivity.this);

                boolean success = dataBaseHelper.addOne(expenseModel);

                Toast.makeText( MainActivity.this, "success: " + success, Toast.LENGTH_SHORT).show();

                ShowExpenseOnListView(dataBaseHelper);

                //success false. something is wrong with that damn date datatype. i swear to god.
            }
        });

        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                ShowExpenseOnListView(databaseHelper);

                //Toast.makeText( MainActivity.this, everything.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        //buttons added.

        lv_expenselist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseModel clickedExpense = (ExpenseModel) parent.getItemAtPosition(position);
                databaseHelper.deleteOne(clickedExpense);
                ShowExpenseOnListView(databaseHelper);

                Toast.makeText( MainActivity.this, "delete success", Toast.LENGTH_SHORT).show();
            }
        });

        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, news_activity.class));
            }

        });
    }

    private void ShowExpenseOnListView(DatabaseHelper databaseHelper) {
        expenseArrayAdapter = new ArrayAdapter<ExpenseModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getEverything());
        //expenseArrayAdapter = new ArrayAdapter<ExpenseModel>(MainActivity.this, android.R.layout.list_content, databaseHelper.getEverything());
        lv_expenselist.setAdapter(expenseArrayAdapter);
    }
}