package com.example.manageattendence.ui.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manageattendence.Helper;
import com.example.manageattendence.MainActivity;
import com.example.manageattendence.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class add_subject extends AppCompatActivity {
    private Helper helper;
    private SQLiteDatabase db;
    private Context context;
    static int p;
   static CustomAdapter_add_subject customAdapter_add_subject;//custom adapter refernce
    static ArrayList<String> subject_name_for_list_view,present_for_list_view,total_classes_for_list_view;//creating three arraylist to storing the retriving data from database
    //from table --attendence_deatil--
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* initialize area from here--to*/
        setContentView(R.layout.activity_add_subject);
        context=this;
        helper=new Helper(this);//get an insatnce of helper class to get database reference
        db=helper.getWritableDatabase();

        subject_name_for_list_view=new ArrayList<String>();//initialize the declared variables
        present_for_list_view=new ArrayList<String>();
        total_classes_for_list_view=new ArrayList<String>();
        //--here

        Cursor retrive_attendence_detail=db.rawQuery("SELECT *FROM ATTENDENCE_DETAIL",null);
        if (retrive_attendence_detail.moveToFirst()){
            do {
                subject_name_for_list_view.add(retrive_attendence_detail.getString(0));//add coorosponding data of subject into table into their corrosponding arraylist
                present_for_list_view.add(retrive_attendence_detail.getString(1));
                total_classes_for_list_view.add(retrive_attendence_detail.getString(2));
            }while (retrive_attendence_detail.moveToNext());
        }


        Button add_subject=(Button)findViewById(R.id.add_subject_add_subject);

        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view;
                LayoutInflater inflater=LayoutInflater.from(context);
                view=inflater.inflate(R.layout.activity_alert_dialog_for_adding_subject,null);
                final EditText subject_name,initial_attendence,total_classes;//get all edit text from view v
                subject_name=(EditText)view.findViewById(R.id.subject_name_alert_box_add_subject);
                initial_attendence=(EditText)view.findViewById(R.id.initial_attendence_alert_box_add_subject);
                total_classes=(EditText)view.findViewById(R.id.initial_total_attendence_alert_box_add_subject);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);//creating a alert dailog to set view of add_subject_alert dailoag into this date 2/25/2021
                builder.setView(view);
                builder.setCancelable(true);
                final AlertDialog alertDialog=builder.create();
                //add click listener on the add button of alert box
                Button add=(Button)view.findViewById(R.id.add_alert_box_add_subject);
                add.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        final Integer x=new Integer(Integer.parseInt(initial_attendence.getText().toString()));//for checking initial attendence must not less than zero make a varaiable to comapre these
                        final Integer y=new Integer(Integer.parseInt(total_classes.getText().toString()));
                        if (TextUtils.isEmpty(subject_name.getText().toString())){//checking weither subject_name must not be empty
                            Toast.makeText(add_subject.this, "Enter a subject name", Toast.LENGTH_SHORT).show();
                            final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//add vibration if the subject _name is empty
                            final VibrationEffect vibrationEffect1;
                            vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                            // it is safe to cancel other vibrations currently taking place
                            vibrator.cancel();
                            vibrator.vibrate(vibrationEffect1);
                        }else{//checking weither initial attendence must be zero and ore than zero
                            if (TextUtils.isEmpty(initial_attendence.getText().toString())||x<0||x>y){
                                Toast.makeText(add_subject.this, "Initial attendence must be 0 or more than zero or less than or equal to total attendence", Toast.LENGTH_SHORT).show();
                                final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                final VibrationEffect vibrationEffect1;
                                vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                                // it is safe to cancel other vibrations currently taking place
                                vibrator.cancel();
                                vibrator.vibrate(vibrationEffect1);
                            }else{
                                if (TextUtils.isEmpty(total_classes.getText().toString())||y<0){
                                    Toast.makeText(add_subject.this, "total classes must be 0 or more than zero", Toast.LENGTH_SHORT).show();
                                    final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    final VibrationEffect vibrationEffect1;
                                    vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                                    // it is safe to cancel other vibrations currently taking place
                                    vibrator.cancel();
                                    vibrator.vibrate(vibrationEffect1);
                                }else{
                                    if (isAlreadypresent(subject_name_for_list_view,subject_name.getText().toString())){
                                        Toast.makeText(add_subject.this, "This subject si already present", Toast.LENGTH_SHORT).show();
                                        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        final VibrationEffect vibrationEffect1;
                                        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                                        // it is safe to cancel other vibrations currently taking place
                                        vibrator.cancel();
                                        vibrator.vibrate(vibrationEffect1);
                                    }else {
                                            ContentValues values = new ContentValues();
                                            values.put("SUBJECT_NAME", subject_name.getText().toString());
                                            values.put("TOTAL_PRESENT", x);
                                            values.put("TOTAL_CLASSES", y);
                                            db.insert("ATTENDENCE_DETAIL", null, values);
                                            subject_name_for_list_view.add(subject_name.getText().toString());
                                            present_for_list_view.add(initial_attendence.getText().toString());
                                            total_classes_for_list_view.add(total_classes.getText().toString());
                                            customAdapter_add_subject.notifyDataSetChanged();//notofying the adapter that data is get changed so add or remove element for list view


                                            alertDialog.cancel();//dismiss the alert dialog after adding the subject
                                            Toast.makeText(add_subject.this, "Subject " + subject_name.getText().toString() + " Added!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }
                });
                alertDialog.show();
                //set cancel button in alert box in add_subejct_activity
                Button cancel=(Button)view.findViewById(R.id.cancel_alert_box_add_subject);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        /*add list view for showinf the detail of subject and attendence into a list view in android

        get instances of list view from add_attendence activity and then
        put all subject in list items of list view


        */
        ListView subject_list_add_attendence=(ListView)findViewById(R.id.subject_list_add_subject);
        customAdapter_add_subject=new CustomAdapter_add_subject(this,subject_name_for_list_view,present_for_list_view,total_classes_for_list_view);//declaration of adapter is on starting of the class
        subject_list_add_attendence.setAdapter(customAdapter_add_subject);
    }
    public boolean isAlreadypresent(ArrayList<String> arrayList,String s){//this function is for check weither given element is already present in Arraylist
        //first argument of the function is arraylist regernce and secound is string
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).equals(s)){
                return true;
            }
        }
        return false;
    }
    public void onBackPressed(){
        Intent a = new Intent(add_subject.this, MainActivity.class);
        startActivity(a);

    }
}