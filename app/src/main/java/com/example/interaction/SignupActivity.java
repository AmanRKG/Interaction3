package com.example.interaction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    EditText emailbox,passwordbox,namebox;
    Button loginbutton,signupbtn;

    FirebaseFirestore database;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        emailbox=findViewById(R.id.emailbox);
        passwordbox=findViewById(R.id.passwordbox);
        namebox= findViewById(R.id.Name);

        Auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        loginbutton=findViewById(R.id.loginbutton);
        signupbtn=findViewById(R.id.Alsignup);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,name,pass;
                email   = emailbox.getText().toString();
                name = namebox.getText().toString();
                pass= passwordbox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPass(pass);

                Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("User")
                                            .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));

                                        }
                                    });
                      //      Toast.makeText(SignupActivity.this,"Account is Created",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}