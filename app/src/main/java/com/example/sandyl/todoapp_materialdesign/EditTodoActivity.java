package com.example.sandyl.todoapp_materialdesign;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditTodoActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {

    private String TAG;
    private Toolbar mToolbar;
    final Calendar myCalendar = Calendar.getInstance();
    Button addButton;
    EditText todoEditText;
    TextView dateTextView;
    RadioGroup priorityRadioGroup;
    RadioButton medium;
    RadioButton high;
    Spinner statusSpinner;

    String todo;
    String priorityLevel;
    String dateStr;
    String statusSelected;
    ArrayList<String> statusList;

    String dateSelected;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoEditText = (EditText) findViewById(R.id.todoEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        addListenerOnAddDateBtn();
        addListenerOnRadioGroupButton();
        createStatusSpinner();
        setTodoToEdit();

    }



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

        switch (id) {
            case R.id.action_save:

                saveTask();
                finish();
                return true;

            case R.id.action_dismiss:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    public void setTodoToEdit() {

        Intent intent = getIntent();
        String todoName = intent.getStringExtra("task");
        String todoPriority = intent.getStringExtra("priority");
        String todoStatus = intent.getStringExtra("status");
        String todoDate = intent.getStringExtra("date");
        position = intent.getIntExtra("position", -1);

        todoEditText.setText(todoName);
        dateTextView.setText(todoDate);

        dateSelected = todoDate;

        Log.d("TAG", "priority selected : " + todoPriority);
        Log.d("TAG", "status selected : " + todoStatus);

        //setting spinners to selected value from todo selected

        setSelectedPriority(todoPriority);
        setSelectedStatus(todoStatus);

    }

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

    public void saveTask() {

        Intent intent = new Intent();
        intent.putExtra("task", todoEditText.getText().toString());
        intent.putExtra("priority", priorityLevel);
        intent.putExtra("date", dateSelected);
        intent.putExtra("status", statusSelected);
        intent.putExtra("position", position);
        setResult(2, intent);

    }


    //on click listener to show calendar
    public void addListenerOnAddDateBtn() {

        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditTodoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    //Radio Button Group to set priority level
    public void addListenerOnRadioGroupButton() {

        //default value if no value change
        priorityLevel = "medium";

        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);

        priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
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


    public void setSelectedPriority(String priority) {

        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);

        medium = (RadioButton) findViewById(R.id.medium);
        high = (RadioButton) findViewById(R.id.high);

        String priorityToSet = "";

        switch(priority.toLowerCase()) {
            case "medium":
                medium.setChecked(true);
                high.setChecked(false);
                break;
            case "high":
                high.setChecked(true);
                medium.setChecked(false);
                break;
            default:

        }
    }

    public void createStatusSpinner() {

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        statusList = new ArrayList<String>();
        statusList.add("Active");
        statusList.add("Done");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner click listener
        statusSpinner.setOnItemSelectedListener(this);

        // attaching data adapter to spinner
        statusSpinner.setAdapter(dataAdapter);

    }


    public void setSelectedStatus(String status) {

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);

        String statusToSet = new String();

        switch(status.toLowerCase()) {
            case "active":
                statusToSet = "Active";
                break;
            case "done":
                statusToSet =  "Done";
                break;

            default:

        }

        statusSpinner.setSelection(((ArrayAdapter<String>)statusSpinner.getAdapter()).getPosition(statusToSet));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        statusSelected = parent.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        statusSelected = "active";
    }

    public String getStringDate(int date, int month, int year) {

        Log.d("TAG", "date selected");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date(year, month, date);
        String formatedDate = sdf.format(d);

        dateTextView.setText(formatedDate);
        return formatedDate;
    }
}
