package com.example.easymessadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterStudent extends AppCompatActivity {

    Button Add;
    EditText Name, Email, Password, RegNumber, Course, Branch, Mobile;
    private FirebaseAuth mAuth;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        mAuth = FirebaseAuth.getInstance();
        Add = (Button)findViewById(R.id.adduser);
        Name = (EditText)findViewById(R.id.name);
        RegNumber = (EditText)findViewById(R.id.regnumber);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Course = (EditText)findViewById(R.id.course);
        Branch = (EditText)findViewById(R.id.branch);
        Mobile = (EditText)findViewById(R.id.mobile);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = Name.getText().toString();
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                final String regnumber = RegNumber.getText().toString();
                final String course = Course.getText().toString();
                final String branch = Branch.getText().toString();
                final String mobile = Mobile.getText().toString();
                if (email.isEmpty())
                {
                    Email.setError("Email is Required");
                    Email.requestFocus();
                    return;
                }

                if (password.isEmpty())
                {
                    Password.setError("Password is Required");
                    Password.requestFocus();
                    return;
                }

                if (password.length() < 6)
                {
                    Password.setError("Length should be more than 6");
                    Password.requestFocus();
                    return;
                }
                if (name.isEmpty())
                {
                    Name.setError("Name is Required");
                    Name.requestFocus();
                    return;
                }
                if (regnumber.isEmpty())
                {
                    RegNumber.setError("Reg Number is Required");
                    RegNumber.requestFocus();
                    return;
                }
                if (course.isEmpty())
                {
                    Course.setError("Course is Required");
                    Course.requestFocus();
                    return;
                }
                if (branch.isEmpty())
                {
                    Branch.setError("Branch is Required");
                    Branch.requestFocus();
                    return;
                }
                if (mobile.isEmpty())
                {
                    Mobile.setError("Mobile is Required");
                    Mobile.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterStudent.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    final String key = mAuth.getUid().toString();
                                    db.getReference("Users").child(key)
                                            .setValue(regnumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterStudent.this, "Registered User Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    User user = new User(name, email, password, course, branch, mobile);
                                    db.getReference("User Details").child(regnumber)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });



                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(RegisterStudent.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }

                                // ...
                            }
                        });
            }
        });
    }
}
