package com.example.manageattendence.ui.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.manageattendence.Helper;
import com.example.manageattendence.R;

import java.util.ArrayList;

public class edit_subject_detail extends AppCompatActivity {
    private Helper helper;
    private SQLiteDatabase db;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject_detail);
        status=0;
        final Intent intent=getIntent();//get intent from the add_subject with extra of subject_name,initial attendence,total classes
        helper=new Helper(this);
        final EditText sub_name,initial_att,total_classes;
        sub_name=(EditText)findViewById(R.id.subject_name_edit_subejct_name);
        initial_att=(EditText)findViewById(R.id.present_edit_subject_name);
        total_classes=(EditText)findViewById(R.id.total_classes_edit_subject_name);
        sub_name.setHint(intent.getStringExtra("sub_name"));     //set hint of edit texts as the previous detail of subjects
        initial_att.setHint(intent.getStringExtra("initial_att"));
        total_classes.setHint(intent.getStringExtra("total_classes"));
        Button update=(Button)findViewById(R.id.update_edit_subject_name);
        update.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                db=helper.getWritableDatabase();
                if (TextUtils.isEmpty(sub_name.getText().toString())){

                }else{
                    if (isAlreadypresent(add_subject.subject_name_for_list_view,sub_name.getText().toString())){
                        Toast.makeText(edit_subject_detail.this, "Subject name is already present", Toast.LENGTH_SHORT).show();
                        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//add vibration if the subject _name is empty
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                    }else {
                        Toast.makeText(edit_subject_detail.this, "you cant change the subject name", Toast.LENGTH_SHORT).show();
                        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//add vibration if the subject _name is empty
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                    }
                }
                if (TextUtils.isEmpty(initial_att.getText().toString())){
                    status=1;
                }else {
                    Integer x=new Integer(Integer.parseInt(initial_att.getText().toString()));
                    Integer y;
                    if (total_classes.getText().toString().equals("")){
                        y=new Integer(Integer.parseInt(intent.getStringExtra("total_classes")));
                    }else {
                        y = new Integer(Integer.parseInt(total_classes.getText().toString()));
                    }
                    if (x<0||x>y){
                        Toast.makeText(edit_subject_detail.this, "Initial attendence must be greater than zero", Toast.LENGTH_SHORT).show();
                        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//add vibration if the subject _name is empty
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                    }else {
                        status=1;
                        db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_PRESENT="+x+" WHERE SUBJECT_NAME="+"'"+add_subject.subject_name_for_list_view.get(add_subject.p)+"'");
                        add_subject.present_for_list_view.set(add_subject.p,initial_att.getText().toString());
                        initial_att.setHint(add_subject.present_for_list_view.get(add_subject.p));
                    }
                }
                if (TextUtils.isEmpty(total_classes.getText().toString())){
                    status=1;
                }else {
                    Integer y=new Integer(Integer.parseInt(total_classes.getText().toString()));
                    Integer x;
                    if (initial_att.getText().toString().equals("")){
                        x=new Integer(Integer.parseInt(intent.getStringExtra("initial_att")));
                    }else {
                        x = new Integer(Integer.parseInt(initial_att.getText().toString()));
                    }
                    if (y<0||x>y){
                        Toast.makeText(edit_subject_detail.this, "total classes must be greater than zero", Toast.LENGTH_SHORT).show();
                        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);//add vibration if the subject _name is empty
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);

                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                    }else {
                        status=1;
                        db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_CLASSES="+y+" WHERE SUBJECT_NAME="+"'"+add_subject.subject_name_for_list_view.get(add_subject.p)+"'");
                        add_subject.total_classes_for_list_view.set(add_subject.p,total_classes.getText().toString());
                        total_classes.setHint(add_subject.total_classes_for_list_view.get(add_subject.p));
                    }
                }
                sub_name.setText(null);
                initial_att.setText(null);
                total_classes.setText(null);
                add_subject.customAdapter_add_subject.notifyDataSetChanged();

               if (status==1){//rin only if something chnaged
                   Toast.makeText(edit_subject_detail.this, "Updated sucessfully", Toast.LENGTH_SHORT).show();
                   Intent intent1=new Intent(edit_subject_detail.this,add_subject.class);
                   startActivity(intent1);
               }
            }
        });
    }
    public boolean isAlreadypresent(ArrayList<String> arrayList, String s){//this function is for check weither given element is already present in Arraylist
        //first argument of the function is arraylist regernce and secound is string
        for (int i=0;i<arrayList.size();i++){
            if (arrayList.get(i).equals(s)){
                return true;
            }
        }
        return false;
    }

}