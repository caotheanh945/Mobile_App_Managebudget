package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import edu.huflit.ftracerproject.R;

public class Dang_nhap_tv extends AppCompatActivity {

    TextView accounttv, passtv;
    Button logintv;
    public static String account;
    public static int unseen = 0;
    boolean validuser=false;
    public static String username;

    String useraccount,role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore fb= FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_dang_nhap_tv);
        accounttv = findViewById(R.id.accounttv_tv);
        passtv = findViewById(R.id.passtv_tv);
        logintv = findViewById(R.id.logintv_tv);
        Bundle b = new Bundle();
        logintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fb.collection("notification")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    boolean status = documentSnapshot.getBoolean("status");
                                    if (status == false)
                                        unseen+=1;
                         /*   Notificationdb notificationdb = new Notificationdb(id,content,status);
                            notificationdbs.add(notificationdb);*/
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                String pw = passtv.getText().toString();
                useraccount= accounttv.getText().toString();
                if (useraccount.length() == 0 && pw.length() == 0)
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập đủ thông tin.", Toast.LENGTH_LONG).show();
                else{
                fb.collection("user")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    useraccount = documentSnapshot.get("account").toString();
                                    role = documentSnapshot.get("role").toString();
                                    String name = documentSnapshot.get("username").toString();
                                    String password = documentSnapshot.get("password").toString();
                                    String creater = documentSnapshot.get("creater").toString();
                                    if(accounttv.getText().toString().equals(useraccount) && pw.equals(password) && !role.equals("admin"))
                                    {
                                        account = useraccount;
                                        username = name;
                                        Intent intent = new Intent(Dang_nhap_tv.this, MainActivity.class);
                                        b.putString("email",useraccount);
                                        b.putString("creater",creater);
                                        b.putString("role",role);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                        validuser = true;
                                        Toast.makeText(Dang_nhap_tv.this, role, Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                                if(validuser==false)
                                    Toast.makeText(Dang_nhap_tv.this, "Incorrect mail or pass", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                }
            }
        });
    }
}