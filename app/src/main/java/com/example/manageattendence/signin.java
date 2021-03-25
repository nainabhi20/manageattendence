package com.example.manageattendence;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link signin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signin extends Fragment {
    FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public signin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment signin.
     */
    // TODO: Rename and change types and number of parameters
    public static signin newInstance(String param1, String param2) {
        signin fragment = new signin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signin, container, false);
        Button sign_in = (Button) root.findViewById(R.id.sigin_sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                EditText email, pass;
                email = (EditText) root.findViewById(R.id.email_sign_in);
                pass = (EditText) root.findViewById(R.id.password_sign_in);
                if (TextUtils.isEmpty(email.getText().toString())) {
                    final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    final VibrationEffect vibrationEffect1;
                    vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

                    // it is safe to cancel other vibrations currently taking place
                    vibrator.cancel();
                    vibrator.vibrate(vibrationEffect1);
                    Toast.makeText(getActivity().getApplicationContext(), "Enter an email first", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(pass.getText().toString())) {
                        final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        final VibrationEffect vibrationEffect1;
                        vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

                        // it is safe to cancel other vibrations currently taking place
                        vibrator.cancel();
                        vibrator.vibrate(vibrationEffect1);
                        Toast.makeText(getActivity().getApplicationContext(), "Enter the password", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Sign in sucessfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    final Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                                    final VibrationEffect vibrationEffect1;
                                    vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE);

                                    // it is safe to cancel other vibrations currently taking place
                                    vibrator.cancel();
                                    vibrator.vibrate(vibrationEffect1);
                                    Toast.makeText(getActivity().getApplicationContext(), "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        final Button slide_right=(Button) root.findViewById(R.id.slide_right);
       final int right=slide_right.getRight();
        slide_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    if (event.getRawX()>right) {
                        slide_right.setRight(Math.round(event.getRawX()));
                        if (event.getRawX() > 850) {
                            Fragment fragment=new signup();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.replace(R.id.fragment, fragment);
                            transaction.commit();
                        }
                    }
                }
                return true;
            }
        });
        return root;
    }
}