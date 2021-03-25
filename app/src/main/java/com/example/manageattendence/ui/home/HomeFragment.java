package com.example.manageattendence.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.manageattendence.Helper;
import com.example.manageattendence.R;
import com.example.manageattendence.setgoals;
import com.example.manageattendence.setting;
import com.example.manageattendence.time_table;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    private Helper helper;
    private SQLiteDatabase db;
    static CustomAdapter customAdapter;
    static Integer goal;
    static ArrayList<String> subject_name_for_list_view,present_for_list_view,total_classes_for_list_view;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView goal_text_view=(TextView)root.findViewById(R.id.goal_text_view_home_fragment);
        goal=new Integer(0);
        //->>
        subject_name_for_list_view=new ArrayList<String>();
        present_for_list_view=new ArrayList<String>();
        total_classes_for_list_view=new ArrayList<String>();
        //-->
        helper=new Helper(getContext());
        db=helper.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT *FROM ATTENDENCE_DETAIL",null);
        if (cursor.moveToFirst()){
            do {
                subject_name_for_list_view.add(cursor.getString(0));
                present_for_list_view.add(cursor.getString(1));
                total_classes_for_list_view.add(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        //-->

        Cursor cursor1=db.rawQuery("SELECT *FROM GOAL",null);
        if (cursor1.moveToFirst()){
            goal=Integer.parseInt(cursor1.getString(0));
            goal_text_view.setText(""+goal);
        }




        //->
        final Button add_subject=(Button)root.findViewById(R.id.add_subject_home_button);//get the isntance of add_subject button from home fragment
        //set click listerner on addd sbject
        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override//On click basily we create a call an activity --add_subject-- and add the subject
            public void onClick(View v) {
                Intent call_add_subject_activity=new Intent(getContext(),add_subject.class);
                startActivity(call_add_subject_activity);
            }
        });
       customAdapter=new CustomAdapter(getContext(),subject_name_for_list_view,present_for_list_view,total_classes_for_list_view,goal);
        ListView subject_list=(ListView)root.findViewById(R.id.list_view_home_fragment);// get insatnce of list view and set adapter
        subject_list.setAdapter(customAdapter);
        subject_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
                snackbar.setAction("dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
                LayoutInflater inflater1=LayoutInflater.from(getContext());
                View view1=inflater1.inflate(R.layout.activity_sneakbar_design,null);//inflalate snackbar design view in homefragment for make a snackbar
                //->>
                //set onclick listener on the snackbar item

                TextView delete,reset,edit;
                delete=(TextView)view1.findViewById(R.id.delete_subject_sd);
                reset=(TextView)view1.findViewById(R.id.reset_attendence_sd);
                edit=(TextView)view1.findViewById(R.id.edit_attendence_sd);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        LayoutInflater inflater1=LayoutInflater.from(getContext());
                        View delete_reset_alert_dialog_activity_view=inflater1.inflate(R.layout.activity_alert_dialog_for_delete_and_reset,null);//inflating the view for creatign alert dialog custims
                        builder.setView(delete_reset_alert_dialog_activity_view);
                        builder.setCancelable(true);//set alert dialog at camcellable by clicking outside the slert dialog
                        final AlertDialog alertDialog=builder.create();//create alert dialog
                        TextView type,info;
                        type=(TextView)delete_reset_alert_dialog_activity_view.findViewById(R.id.delete_reset);
                        info=(TextView)delete_reset_alert_dialog_activity_view.findViewById(R.id.delete_reset_info);
                        type.setText("Reset Attendencce");
                        info.setText("Are you sure you want to reset attendence of the subject?\n\nNote: history of the subject will be deleted.");
                        Button yes=(Button)delete_reset_alert_dialog_activity_view.findViewById(R.id.yes_subject_alert_dialog_list_view_add_subejct);
                        Button no=(Button)delete_reset_alert_dialog_activity_view.findViewById(R.id.no_alert_dialog_on_click_delete_image_list_view_add_attendence);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Helper helper=new Helper(getContext());
                                SQLiteDatabase db=helper.getWritableDatabase();
                                db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_PRESENT=0,TOTAL_CLASSES=0 WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");//delete the subject detail from database
                                present_for_list_view.set(position,"0");
                                total_classes_for_list_view.set(position,"0");
                                customAdapter.notifyDataSetChanged();
                                //-->>
                                Toast.makeText(getActivity().getApplicationContext(), "Attendence reseted", Toast.LENGTH_SHORT).show();
                                alertDialog.cancel();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        LayoutInflater inflater1=LayoutInflater.from(getContext());
                        View delete_reset_alert_dialog_activity_view=inflater1.inflate(R.layout.activity_alert_dialog_for_delete_and_reset,null);//inflating the view for creatign alert dialog custims
                        builder.setView(delete_reset_alert_dialog_activity_view);
                        builder.setCancelable(true);//set alert dialog at camcellable by clicking outside the slert dialog
                        final AlertDialog alertDialog=builder.create();//create alert dialog
                        TextView type,info;
                        type=(TextView)delete_reset_alert_dialog_activity_view.findViewById(R.id.delete_reset);
                        info=(TextView)delete_reset_alert_dialog_activity_view.findViewById(R.id.delete_reset_info);
                        type.setText("Delete Subject");
                        info.setText("Are you sure you permanentaly want to delete the subject!\n\nNote: History of the subject will be deleted");
                        Button yes=(Button)delete_reset_alert_dialog_activity_view.findViewById(R.id.yes_subject_alert_dialog_list_view_add_subejct);
                        Button no=(Button)delete_reset_alert_dialog_activity_view.findViewById(R.id.no_alert_dialog_on_click_delete_image_list_view_add_attendence);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Helper helper=new Helper(getContext());
                                SQLiteDatabase db=helper.getWritableDatabase();
                                db.execSQL("DELETE FROM ATTENDENCE_DETAIL WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");//delete the subject detail from database

                                db.execSQL("DELETE FROM ATTENDENCE_STATUS WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");//DELETE ALL STATUS DATE TIME RELATED TO GIVR SUBJECT

                                db.execSQL("DELETE FROM TIME_TABLE WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");
                                subject_name_for_list_view.remove(position);//remove subject name present and total attendece form arraylist and notifying adapater as data changed
                                present_for_list_view.remove(position);
                                total_classes_for_list_view.remove(position);
                                customAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity().getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                alertDialog.cancel();
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });
                        alertDialog.show();
                    }
                });


                //-->>
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                // set padding of the all corners as 0
                snackbarLayout.setPadding(0, 0, 0, 0);//set paddign of the snackbar
                snackbarLayout.addView(view1);
                snackbar.show();
                return true;
            }
        });
        //-->>
        //set long click listner on list view home fragment



        //-->>
        //-->>//for set goal
   ImageView goal_image=(ImageView)root.findViewById(R.id.goal_image_view_home_fragment);
   goal_image.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent intent=new Intent(getActivity().getApplicationContext(), setgoals.class);
           startActivity(intent);
       }
   });


        //->>
        //set click listerner on image view of timetbale home fragment
        ImageView timetable=(ImageView)root.findViewById(R.id.time_table_image_view_home_fragment);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),time_table.class);
                startActivity(intent);
            }
        });

        //->>
        //set on click on setting image view of home fragment
        ImageView settings=(ImageView)root.findViewById(R.id.setting_image_view_home_fragment);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), setting.class);
                startActivity(intent);
            }
        });

        //-->>
                //
        return root;
    }
}
class CustomAdapter extends BaseAdapter{
    private Helper helper;
    private SQLiteDatabase db;
    private Context context;
    private ArrayList<String> subject_name_for_list_view,present_for_list_view,total_classes_for_list_view;
    private Integer goal;
    public CustomAdapter(Context context, ArrayList<String> subject_name_for_list_view,ArrayList<String> present_for_list_view,ArrayList<String> total_classes_for_list_view,Integer goal) {
        super();
        this.context=context;
        this.subject_name_for_list_view=subject_name_for_list_view;
        this.present_for_list_view=present_for_list_view;
        this.total_classes_for_list_view=total_classes_for_list_view;
        this.goal=goal;
        helper=new Helper(context);
        db=helper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return subject_name_for_list_view.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_subject_list_view_in_home_fragments,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.subject_name_list_view_home_fragment);
        TextView present=(TextView)view.findViewById(R.id.present_list_view_home_fragment);
        TextView percentage=(TextView)view.findViewById(R.id.percentage_list_view_home_fragment);
        TextView predict=(TextView)view.findViewById(R.id.identify_bunks_list_view_home_fragment);
        textView.setText(subject_name_for_list_view.get(position));
        present.setText(present_for_list_view.get(position)+"/"+total_classes_for_list_view.get(position));
        Double x,y;
        x=Double.parseDouble(present_for_list_view.get(position));
        y=Double.parseDouble(total_classes_for_list_view.get(position));
        Double per=(x/y)*100;
        int r = (int) Math.round(per*100);
        double f = r / 100.0;
        if(f>=goal){
            percentage.setTextColor(context.getResources().getColor(R.color.green) );
        }else {
            percentage.setTextColor(context.getResources().getColor(R.color.red));
        }
        String p=""+f;
        percentage.setText(p);//set total percentage attendence

        int bunks;//identify how how classes you can bunk and yuo have to takr if idebtift bunk if >0 you can bunk if <0 you have to take
        Integer p_f_b=Integer.parseInt(present_for_list_view.get(position));
        Integer t_f_b=Integer.parseInt(total_classes_for_list_view.get(position));
        bunks=p_f_b-((goal*t_f_b)/100);
        if (bunks>=1){
            predict.setTextColor(context.getResources().getColor(R.color.white));
            predict.setText("Attendence is on track you can bunk next class");
        }else {
            predict.setTextColor(context.getResources().getColor(R.color.red));
            predict.setText("You must have to take next classes");
        }

        ImageView add,cut;
        add=(ImageView)view.findViewById(R.id.add_attendence_list_view_home_fragment);
        cut=(ImageView)view.findViewById(R.id.cut_attendence_list_view_homw_fragment);



        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //check weither switch of vibration is on or off
                //-->>
                LayoutInflater inflater1=LayoutInflater.from(context);
                View view1=inflater1.inflate(R.layout.activity_setting,null);
                Switch x=(Switch)view1.findViewById(R.id.switch1);
                if (!x.isChecked()){
                    final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    final VibrationEffect vibrationEffect1;
                    vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

                    // it is safe to cancel other vibrations currently taking place
                    vibrator.cancel();
                    vibrator.vibrate(vibrationEffect1);
                }


                //->>
                Integer present=new Integer(Integer.parseInt(present_for_list_view.get(position))+1);//on click increase the attendence by 1 and update the values into the database
                db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_PRESENT="+present+" WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");
                Integer total_classes=new Integer(Integer.parseInt(total_classes_for_list_view.get(position))+1);
                db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_CLASSES="+total_classes+" WHERE SUBJECT_NAME="+"'"+subject_name_for_list_view.get(position)+"'");
                ContentValues values=new ContentValues();//put status of the classes a given time
                Date d = new Date();//get current dta in
                String dateWithoutTime = d.toString().substring(0, 10);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");//add current time in
                LocalDateTime now = LocalDateTime.now();
                Log.d("TIME ->------",dtf.format(now));
                values.put("DATE",dateWithoutTime);
                values.put("TIME",dtf.format(now));
                values.put("SUBJECT_NAME",subject_name_for_list_view.get(position));
                values.put("STATUS","PRESENT");
                db.insert("ATTENDENCE_STATUS",null,values);
                Log.d("Date is-=-------->",dateWithoutTime);
                total_classes_for_list_view.set(position, "" + total_classes);
                //kal kok status table fill karni hai
                present_for_list_view.set(position,""+present);
                total_classes_for_list_view.set(position,""+total_classes);
                HomeFragment.customAdapter.notifyDataSetChanged();
            }
        });



        cut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //check weither switch of vibration is on or off
                //-->>
                LayoutInflater inflater1=LayoutInflater.from(context);
                View view1=inflater1.inflate(R.layout.activity_setting,null);
                Switch x=(Switch)view1.findViewById(R.id.switch1);
                if (!x.isChecked()){
                    final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    final VibrationEffect vibrationEffect1;
                    vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

                    // it is safe to cancel other vibrations currently taking place
                    vibrator.cancel();
                    vibrator.vibrate(vibrationEffect1);
                }


                //->>
                Integer total_classes = new Integer(Integer.parseInt(total_classes_for_list_view.get(position)) + 1);
                    db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_CLASSES=" + total_classes + " WHERE SUBJECT_NAME=" + "'" + subject_name_for_list_view.get(position) + "'");
                ContentValues values=new ContentValues();//put status of the classes a given time
                Date d = new Date();//get current dta in
                String dateWithoutTime = d.toString().substring(0, 10);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");//add current time in
                LocalDateTime now = LocalDateTime.now();
                Log.d("TIME ->------",dtf.format(now));
                values.put("DATE",dateWithoutTime);
                values.put("TIME",dtf.format(now));
                values.put("SUBJECT_NAME",subject_name_for_list_view.get(position));
                values.put("STATUS","ABSENT");
                db.insert("ATTENDENCE_STATUS",null,values);
                Log.d("Date is-=-------->",dateWithoutTime);
                    total_classes_for_list_view.set(position, "" + total_classes);
                    //kal kok status table fill karni hai
                    HomeFragment.customAdapter.notifyDataSetChanged();
                }
        });
        //->>
        //setr over



        //->>
        return view;
    }
}