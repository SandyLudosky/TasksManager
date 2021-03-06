package com.example.sandyl.todoapp_materialdesign;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTodoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String TAG;
    private Toolbar mToolbar;
    final Calendar myCalendar = Calendar.getInstance();
    Button addButton;
    EditText todoEditText;
    TextView dateTextView;
    RadioGroup priorityRadioGroup;
    RadioButton medium;
    RadioButton high;
    RadioButton low;
    Spinner statusSpinner;
    int position;

    String todo;
    String priorityLevel;
    String dateStr;
    String statusSelected;
    ArrayList<String> statusList;

    String dateSelected;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = new Intent();
        intent.putExtra("task", todoEditText.getText().toString());
        intent.putExtra("date", dateSelected);
        intent.putExtra("priority", priorityLevel);
        intent.putExtra("status", statusSelected);
        intent.putExtra("position", position);

        switch (id) {
            case R.id.action_save:
                saveTask(intent);
                return true;

            case R.id.action_dismiss:
                dismissActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoEditText = (EditText) findViewById(R.id.todoEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        todoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoEditText.setText("");
                todoEditText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }

        });

        addToolbar();
        addListenerOnAddDateBtn();
        addListenerOnRadioGroupButton();
        createStatusSpinner();
    }

    //Toolbar
    public void addToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    //DatePicker
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            dateSelected = getStringDate(dayOfMonth,monthOfYear, (year - 1900));
        }
    };

    //on click listener to show calendar
    public void addListenerOnAddDateBtn() {

        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddTodoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    //Radio Button Group to set priority level
    public void addListenerOnRadioGroupButton() {

        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);
        medium = (RadioButton) findViewById(R.id.medium);
        high = (RadioButton) findViewById(R.id.high);
        low = (RadioButton) findViewById(R.id.low);

        //default value if no value change
        priorityLevel = "low";
        low.setChecked(true);

        priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.low:
                        Log.d("TAG", "low checked");
                        priorityLevel = "low";
                        break;
                    case R.id.medium:
                        Log.d("TAG", "medium checked");
                        priorityLevel = "medium";
                        break;
                    case R.id.high:
                        Log.d("TAG", "high checked");
                        priorityLevel = "high";
                        break;
                }
            }
        });

    }



    //Spinner
    public void createStatusSpinner() {

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        statusList = new ArrayList<String>();
        statusList.add("Active");
        statusList.add("Done");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setOnItemSelectedListener(this);
        statusSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       statusSelected = parent.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        statusSelected = "active";
    }

    //functions
    public void saveTask(Intent intent) {
        if(!todoEditText.getText().toString().matches("")) {
            setResult(1, intent);
            finish();
        } else {
            todoEditText.setText("PLEASE ADD TASK");
            todoEditText.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void dismissActivity(Intent intent) {
        setResult(0, intent);
        finish();
    }

    public String getStringDate(int date, int month, int year) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date(year, month, date);
        String formatedDate = sdf.format(d);

        dateTextView.setText(formatedDate);
        return formatedDate;
    }
}
