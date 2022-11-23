package com.example.cayxanh;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class SignupActivity extends AppCompatActivity {
    protected FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    Button btn_SignUp;
    TextView txtLoginHere;
    EditText edtEmail, edtPassword, edtUserName, edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        anhXa();
        txtLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUserName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                String confirmPass = edtConfirmPassword.getText().toString().trim();

                if (checkUserName(userName) && checkEmail(email) && checkPassword(pass) && checkConfirmPass(confirmPass)) {
                    funSingUp(email, pass);

                }
            }
        });
    }

    private void funSingUp(String userName, String Password) {
        mFirebaseAuth.createUserWithEmailAndPassword(userName, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "Sing Up Success", Toast.LENGTH_SHORT).show();
                            Log.d("tfirebase", "onComplete: "+task);
                            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                            startActivity(intent);
                        }else {
                            Log.d("tfirebase", "onComplete: "+task);
                            Toast.makeText(SignupActivity.this, "Sing Up Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("tfirebase", "onComplete: " + e.getMessage());
                        Toast.makeText(SignupActivity.this, "Sing Up Fail", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void anhXa() {
        btn_SignUp = findViewById(R.id.buttonSignUp);
        txtLoginHere = findViewById(R.id.tvLoginHere);
        edtUserName = findViewById(R.id.inputUserName);
        edtEmail = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        edtConfirmPassword = findViewById(R.id.inputConfirmPassword);
    }

    private boolean checkUserName(String userName) {
        if (!userName.isEmpty()) {
            edtUserName.setBackgroundResource(R.drawable.round_border);
            edtUserName.setError(null);
            return true;
        } else {
            edtUserName.setError("error");
            edtUserName.setBackgroundResource(R.drawable.errorbg);
        }
        return false;
    }

    private boolean checkEmail(String email) {
        if (!email.isEmpty()) {
            edtEmail.setBackgroundResource(R.drawable.round_border);
            edtEmail.setError(null);
            return true;
        } else {
            edtEmail.setError("error");
            edtEmail.setBackgroundResource(R.drawable.errorbg);
        }
        return false;
    }

    private boolean checkPassword(String password) {
        if (!password.isEmpty() && password.length()>=6) {
            edtPassword.setBackgroundResource(R.drawable.round_border);
            edtPassword.setError(null);
            return true;
        } else {
            edtPassword.setError("error");
            edtPassword.setBackgroundResource(R.drawable.errorbg);
        }
        return false;
    }

    private boolean checkConfirmPass(String confirmpassword) {
        if (!confirmpassword.isEmpty() && confirmpassword.length()>=6) {
            edtConfirmPassword.setBackgroundResource(R.drawable.round_border);
            edtConfirmPassword.setError(null);
            return true;
        } else {
            edtConfirmPassword.setError("error");
            edtConfirmPassword.setBackgroundResource(R.drawable.errorbg);
        }
        return false;
    }
}
