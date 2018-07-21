package com.example.arpit.atry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText tx1,txt2;
    Button b1,b2;
    String mVerificationId;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx1=(EditText)findViewById(R.id.num);
        txt2=(EditText)findViewById(R.id.code);
        b1=(Button)findViewById(R.id.send);
        b2=(Button)findViewById(R.id.submit);
        mAuth=FirebaseAuth.getInstance();

        mcall=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId=s;
                Toast.makeText(MainActivity.this,"code send",Toast.LENGTH_SHORT).show();
            }
        };
    }
    public void send_sms(View view)
    {
        String num=tx1.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(num,60, TimeUnit.SECONDS,this,mcall);
    }
    public void signInWithPhone(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();

                      }
                    }
                });
    }
    public void verify(View view)
    {
        String code=txt2.getText().toString();

            verifyPhone(mVerificationId,code);

    }
    public void verifyPhone(String veri,String cod)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(veri,cod);
        signInWithPhone(credential);
    }
}
