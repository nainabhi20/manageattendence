package com.example.manageattendence.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.manageattendence.Helper;
import com.example.manageattendence.MainActivity;
import com.example.manageattendence.R;
import com.example.manageattendence.time_table;

import java.util.ArrayList;
import java.util.Calendar;

public class add_subject_time_table extends AppCompatActivity {
    private Helper helper;
    private SQLiteDatabase db;
    private ArrayList<String> subject_names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject_time_table);
        subject_names=new ArrayList<String>();
        helper=new Helper(this);
        db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT SUBJECT_NAME FROM ATTENDENCE_DETAIL",null);
        if (cursor.moveToFirst()){
            int i=0;
            do {
                subject_names.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        final Intent intent=getIntent();
        final TextView select_subject,start,end;
        select_subject=(TextView)findViewById(R.id.select_subject_for_time_table);
        start=(TextView)findViewById(R.id.starting_time_for_time_table);
        end=(TextView)findViewById(R.id.end_time_for_time_table);
        final EditText location=(EditText)findViewById(R.id.location_add_subject_time_table);
        select_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(add_subject_time_table.this);

                // set the custom icon to the alert dialog
                // title of the alert dialog
                alertDialog.setTitle("Choose an Item");

                // list of the items to be displayed to
                // the user in the form of list
                // so that user can select the item from
                final String[] listItems = new String[subject_names.size()];
                for (int i=0;i<subject_names.size();i++){
                    listItems[i]=subject_names.get(i);
                }
                final int[] checkedItem={-1};
                // the function setSingleChoiceItems is the function which builds
                // the alert dialog with the single item selection
                alertDialog.setSingleChoiceItems(listItems, checkedItem[0], new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // update the selected item which is selected by the user
                        // so that it should be selected when user opens the dialog next time
                        // and pass the instance to setSingleChoiceItems method
                        checkedItem[0] = which;
                        select_subject.setText(listItems[which]);
                        // now also update the TextView which previews the selected item

                        // when selected an item the dialog should be closed with the dismiss method
                        dialog.dismiss();
                    }
                });

                // set the negative button if the user
                // is not interested to select or change
                // already selected item
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setCancelable(false);
                // create and build the AlertDialog instance
                // with the AlertDialog builder instance
                AlertDialog customAlertDialog = alertDialog.create();

                // show the alert dialog when the button is clicked
                customAlertDialog.show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(add_subject_time_table.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        start.setText( ""+selectedHour + ":" + selectedMinute);
                    }
                },3,30,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(add_subject_time_table.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end.setText( ""+selectedHour + ":" + selectedMinute);
                    }
                },3,30,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        Button add=(Button)findViewById(R.id.add_time_table);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_subject.getText().toString().equals("Select Subject")){
                    Toast.makeText(add_subject_time_table.this, "choose subject", Toast.LENGTH_SHORT).show();
                }else {
                    if (start.getText().toString().equals("Starting Time")){
                        Toast.makeText(add_subject_time_table.this, "choose the starting time of the class", Toast.LENGTH_SHORT).show();
                    }else {
                        if (end.getText().toString().equals("End Time")){
                            Toast.makeText(add_subject_time_table.this, "choose class end time", Toast.LENGTH_SHORT).show();
                        }else {
                            if (location.getText().toString().equals("")){
                                ContentValues values=new ContentValues();
                                values.put("SUBJECT_NAME",select_subject.getText().toString());
                                values.put("DAY_NAME",intent.getStringExtra("DAY"));
                                values.put("TIMING",start.getText().toString()+"-"+end.getText().toString());
                                values.put("LOCATION","---");
                                db.insert("TIME_TABLE",null,values);
                                select_subject.setText("Select Subject");//to avoid add multiple values at a time
                                Intent intent1=new Intent(add_subject_time_table.this, time_table.class);
                                startActivity(intent1);
                                Toast.makeText(add_subject_time_table.this, "Inserted---", Toast.LENGTH_SHORT).show();
                            }else {
                                ContentValues values=new ContentValues();
                                values.put("SUBJECT_NAME",select_subject.getText().toString());
                                Toast.makeText(add_subject_time_table.this, intent.getStringExtra("DAY"), Toast.LENGTH_SHORT).show();
                                values.put("DAY_NAME",intent.getStringExtra("DAY"));
                                values.put("TIMING",start.getText().toString()+"-"+end.getText().toString());
                                values.put("LOCATION",location.getText().toString());
                                db.insert("TIME_TABLE",null,values);
                                Intent intent1=new Intent(add_subject_time_table.this, time_table.class);
                                startActivity(intent1);
                                Toast.makeText(add_subject_time_table.this, "Inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}