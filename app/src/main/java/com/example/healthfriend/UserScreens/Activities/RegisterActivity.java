package com.example.healthfriend.UserScreens.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.DoctorScreens.QuestionnaireDoctorActivity;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.example.healthfriend.R;
//import com.example.healthfriend.UserScreens.QuestionnaireAct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextView loginText;
    EditText email, pass, confirmPass;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    AppCompatButton registerBtn;
    RadioButton doc, user;
    RadioGroup userType;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

//         loginText = findViewById(R.id.login_text);
//         email = findViewById(R.id.reg_email);
//         pass = findViewById(R.id.reg_pass);
//         confirmPass = findViewById(R.id.reg_confirmpass);
//         registerBtn = findViewById(R.id.reg_btn);
//         userType = findViewById(R.id.userType_radio_group);
//         doc = findViewById(R.id.doctor_RadioButton);
//         user = findViewById(R.id.user_RadioButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait for registration");
        progressDialog.setTitle("Registration");
        progressDialog.setCanceledOnTouchOutside(false);

        doc = findViewById(R.id.doctor_RadioButton);
        user = findViewById(R.id.user_RadioButton);
        userType = findViewById(R.id.userType_radio_group);
        email = findViewById(R.id.reg_email);
        loginText = findViewById(R.id.login_text);
        pass = findViewById(R.id.reg_pass);
        confirmPass = findViewById(R.id.reg_confirmpass);
        registerBtn = findViewById(R.id.reg_btn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }

    private void performAuth() {
        String EMAIL = email.getText().toString();
        String PASS = pass.getText().toString();
        String CONPASS = confirmPass.getText().toString();

        if (!EMAIL.matches(emailPattern)) {
            email.setError("Please enter a valid email");
        } else if (PASS.isEmpty() || PASS.length() < 6) {
            pass.setError("Please enter a password with at least 6 characters");
        } else if (!PASS.equals(CONPASS)) {
            confirmPass.setError("Passwords don't match");
        } else {
            if (!EMAIL.matches(emailPattern)) {
                email.setError("Please enter a valid email");
            } else if (PASS.isEmpty() || PASS.length() < 6) {
                pass.setError("Please enter a proper password");
            } else if (!PASS.equals(CONPASS)) {
                confirmPass.setError("Passwords don't match");
            } else {
                progressDialog.setMessage("Please wait for registration");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(EMAIL, PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Registration Done", Toast.LENGTH_LONG).show();

                            // Check which user type is selected and redirect accordingly
//                         int selectedId = userType.getCheckedRadioButtonId();
//                         if (selectedId == R.id.user_RadioButton) {
//                             startActivity(new Intent(RegisterActivity.this, QuestionnaireAct.class)
//                                     .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
//                         } else if (selectedId == R.id.doctor_RadioButton) {
//                             startActivity(new Intent(RegisterActivity.this, QuestionnaireDoctorActivity.class)
//                                     .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                            // Check which radio button is selected
                            int selectedId = userType.getCheckedRadioButtonId();
                            if (selectedId == R.id.user_RadioButton) {
                                IndividualUser newUser = IndividualUser.getInstance();
                                newUser.setEmail(email.getText().toString());
                                Toast.makeText(RegisterActivity.this, "Registration Done for User", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, QuestionnaireAct.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else if (selectedId == R.id.doctor_RadioButton) {
                                Doctor newDoctor = Doctor.getInstance();
                                newDoctor.setEmail(email.getText().toString());
                                Toast.makeText(RegisterActivity.this, "Registration Done for Doctor", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, QuestionnaireDoctorActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegisterActivity.this, "Email is already registered", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                                Log.e(TAG, "Registration failed", task.getException());
                            }
                        }
                    }
                });
            }
        }
    }
}
