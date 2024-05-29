package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.User;

public class Dang_Ky extends AppCompatActivity {
    //Đăng ký
    EditText edtFullname, edtPhoneNumber, edtPW, edt_username_admin;
    Button btnregister, Thoat;
    FirebaseAuth FbAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtFullname.getText().toString();
                String number= edtPhoneNumber.getText().toString();
                String pw = edtPW.getText().toString();
                FbAuth.createUserWithEmailAndPassword(name, pw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User user = new User();
                                    user.setAccount(edtFullname.getText().toString());
                                    user.setPassword(edtPW.getText().toString());
                                    user.setUsername(edt_username_admin.getText().toString());
                                    user.setRole("admin");
                                    user.setCreater("null");
                                    db.collection("user")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("TAG", "Dữ liệu đã được chèn thành công vào Firestore. ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("TAG", "Lỗi khi chèn dữ liệu vào Firestore: " + e.getMessage());
                                                }
                                            });
                                    Toast.makeText(Dang_Ky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Dang_Ky.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }

        });
    }


    public void AnhXa()
    {
        edtFullname = findViewById(R.id.edt_fullname);
        edtPhoneNumber = findViewById(R.id.edt_phonenumber);
        edtPW = findViewById(R.id.edt_pw);
        btnregister = findViewById(R.id.btn_Register);
        Thoat=findViewById(R.id.btn_thoat);
        FbAuth = FirebaseAuth.getInstance();
        edt_username_admin = findViewById(R.id.username_admin);
    }


    public void btn_thoat(View view) {
        Intent intent = new Intent(getApplicationContext(), PickRoleLogin.class);
        startActivity(intent);
    }

}