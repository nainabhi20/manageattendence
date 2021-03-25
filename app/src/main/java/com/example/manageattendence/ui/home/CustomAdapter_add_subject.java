package com.example.manageattendence.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.manageattendence.Helper;
import com.example.manageattendence.R;

import java.util.ArrayList;

class  CustomAdapter_add_subject extends BaseAdapter {
    Context context;
    ArrayList<String> s_n_f_a_s,p_f_l_v,t_c_f_l_v;
    public CustomAdapter_add_subject(Context context,ArrayList<String> s_n_f_a_s,ArrayList<String> p_f_l_v,ArrayList<String> t_c_f_l_v){
        super();
        this.context=context;
        this.s_n_f_a_s=s_n_f_a_s;
        this.p_f_l_v=p_f_l_v;
        this.t_c_f_l_v=t_c_f_l_v;
    }
    @Override
    public int getCount() {
        return s_n_f_a_s.size();
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
        final LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_subject_list_view_in_add_subject,parent,false);
        TextView sub_name,presents,total_classes;
        sub_name=(TextView)view.findViewById(R.id.subject_name_list_view_add_subject);
        presents=(TextView)view.findViewById(R.id.present_list_view_add_subject);
        total_classes=(TextView)view.findViewById(R.id.total_classes_list_view_add_subject);
        sub_name.setText(s_n_f_a_s.get(position));
        presents.setText(p_f_l_v.get(position));
        total_classes.setText(t_c_f_l_v.get(position));
        ImageView delete_image=(ImageView)view.findViewById(R.id.delete_subject_list_view_add_subject);
        ImageView reset_image=(ImageView)view.findViewById(R.id.reset_attendence_list_view_add_attendence);
        ImageView edit_image=(ImageView)view.findViewById(R.id.edit_subject_detail_list_view_add_attendence);
        delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater1=LayoutInflater.from(context);
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
                        Helper helper=new Helper(context);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        db.execSQL("DELETE FROM ATTENDENCE_DETAIL WHERE SUBJECT_NAME="+"'"+s_n_f_a_s.get(position)+"'");//delete the subject detail from database

                        db.execSQL("DELETE FROM ATTENDENCE_STATUS WHERE SUBJECT_NAME="+"'"+s_n_f_a_s.get(position)+"'");//DELETE ALL STATUS DATE TIME RELATED TO GIVR SUBJECT

                        db.execSQL("DELETE FROM TIME_TABLE WHERE SUBJECT_NAME="+"'"+s_n_f_a_s.get(position)+"'");
                        add_subject.subject_name_for_list_view.remove(s_n_f_a_s.get(position));//remove subject name present and total attendece form arraylist and notifying adapater as data changed
                        add_subject.present_for_list_view.remove(p_f_l_v.get(position));
                        add_subject.total_classes_for_list_view.remove(position);
                        add_subject.customAdapter_add_subject.notifyDataSetChanged();
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
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


        //set on click lister on image reset
        //-->

        ImageView reset =(ImageView)view.findViewById(R.id.reset_attendence_list_view_add_attendence);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater1=LayoutInflater.from(context);
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
                        Helper helper=new Helper(context);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        db.execSQL("UPDATE ATTENDENCE_DETAIL SET TOTAL_PRESENT=0,TOTAL_CLASSES=0 WHERE SUBJECT_NAME="+"'"+s_n_f_a_s.get(position)+"'");//delete the subject detail from database
                        add_subject.present_for_list_view.set(position,"0");
                        add_subject.total_classes_for_list_view.set(position,"0");
                        add_subject.customAdapter_add_subject.notifyDataSetChanged();
                        //-->>
                        Toast.makeText(context, "Attendence reseted", Toast.LENGTH_SHORT).show();
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


        //set click listen on edit subject image in list view of subjects in add_subejct activity
        ImageView edit_detail=(ImageView)view.findViewById(R.id.edit_subject_detail_list_view_add_attendence);
        edit_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,edit_subject_detail.class);
                intent.putExtra("sub_name",s_n_f_a_s.get(position));
                intent.putExtra("initial_att",p_f_l_v.get(position));
                intent.putExtra("total_classes",t_c_f_l_v.get(position));
                add_subject.p=position;
                v.getContext().startActivity(intent);
            }
        });


        return view;
    }
}