package com.example.sandyl.todoapp_materialdesign;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class EditTodoActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {

    private String TAG;
    private Toolbar mToolbar;
    private Calendar myCalendar;
    Button addButton;
    EditText todoEditText;
    TextView dateTextView;
    RadioGroup priorityRadioGroup;
    RadioButton medium;
    RadioButton high;
    RadioButton low;
    Spinner statusSpinner;
    Button dateButton;

    String todo;
    int uid;
    String priorityLevel;
    String dateStr;
    String statusSelected;
    ArrayList<String> statusList;

    String dateSelected;
    int position;

    int day;
    int month;
    int year;

    TodoManager todoManager = new TodoManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);


        todoEditText = (EditText) findViewById(R.id.todoEditText);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateButton = (Button) findViewById(R.id.addButton);
        dateButton.setText("CHANGE");

        addToolbar();
        addListenerOnAddDateBtn();
        addListenerOnRadioGroupButton();
        createStatusSpinner();
        setTodoToEdit();

        myCalendar = setDateOnView(dateSelected);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_todo_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:

                createDialog();
                return true;

            case R.id.action_save:
                //sends action for update
                sendAction(2);
                finish();
                return true;

            case R.id.action_dismiss:
                sendAction(2);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void setTodoToEdit() {

        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", -1);
        String todoName = intent.getStringExtra("task");
        String todoPriority = intent.getStringExtra("priority");
        String todoStatus = intent.getStringExtra("status");
        String todoDate = intent.getStringExtra("date");
        position = intent.getIntExtra("position", -1);

        todoEditText.setText(todoName);
        dateTextView.setText(todoDate);

        dateSelected = todoDate;

        Log.d("TAG", "id of todo set to edit : " + todoName);

        //setting spinners to selected value from todo selected

        setSelectedPriority(todoPriority);
        setSelectedStatus(todoStatus);

    }


    //Toolbar
    public void addToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    public void setSelectedPriority(String priority) {

        priorityRadioGroup = (RadioGroup) findViewById(R.id.priorityRadioGroup);

        medium = (RadioButton) findViewById(R.id.medium);
        high = (RadioButton) findViewById(R.id.high);
        low = (RadioButton) findViewById(R.id.low);

        String priorityToSet = "";

        switch(priority.toLowerCase()) {
            case "low":
                low.setChecked(true);
                break;
            case "medium":
                medium.setChecked(true);
                break;
            case "high":
                high.setChecked(true);
                break;
            default:

        }
    }

    //Spinner
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

    //on change event listener to update date object before sending back to MainActivity
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

    // display date on datePicker and dateTexView - http://stackoverflow.com/questions/14851285/how-to-get-datepicker-value-in-date-format
    public Calendar setDateOnView(String date) {

        dateTextView.setText(date);
        Date dateSelected = todoManager.getDate(date);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateSelected);

        return calendar;
    }


    //functions
    public String getStringDate(int date, int month, int year) {

        Log.d("TAG", "date selected");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date(year, month, date);
        String formatedDate = sdf.format(d);

        dateTextView.setText(formatedDate);
        return formatedDate;
    }

    //dialog to confirm task deletion
    public void createDialog() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete todo")
                .setMessage("Are you sure you want to delete item from list ?")
                .setView(taskEditText)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //sends action for deletion
                        sendAction(3);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    //handles result codes + data sent back to MainActivity
    public void sendAction(int result) {

        Intent intent = new Intent();
        intent.putExtra("uid", uid);
        intent.putExtra("task", todoEditText.getText().toString());
        intent.putExtra("priority", priorityLevel);
        intent.putExtra("date", dateSelected);
        intent.putExtra("status", statusSelected);
        intent.putExtra("position", position);

        //resultCode 2 = update and save
        //resultCode 3 = delete
        setResult(result, intent);
        finish();

    }

}
