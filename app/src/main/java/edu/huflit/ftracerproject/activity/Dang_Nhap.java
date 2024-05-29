package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.Categories;

public class Dang_Nhap extends AppCompatActivity {
    //Đăng nhập
    EditText edtuserMail, edtPassWord;
    Button btnLogin;
    FirebaseAuth FbAuth;
    FirebaseUser user;

    public static String account;
    public String role;
    public static String userid;
    public static String username;
    boolean check = true;
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        button_login_onClick();
    }
    public void AnhXa() {
        edtuserMail = findViewById(R.id.edt_username);
        edtPassWord = findViewById(R.id.edt_Password);
        btnLogin = findViewById(R.id.btn_login);
        FbAuth = FirebaseAuth.getInstance();
    }

    public void button_login_onClick(){
        //Button dăng nhập
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(view -> {
            String mail = edtuserMail.getText().toString();
            String pw = edtPassWord.getText().toString();
            fb.collection("user")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                String id = documentSnapshot.getId();
                                String name = documentSnapshot.get("username").toString();
                                String useraccount = documentSnapshot.get("account").toString();
                                role = documentSnapshot.get("role").toString();
                                if(edtuserMail.getText().toString().equals(useraccount) && role.equals("Quản trị viên"))
                                {
                                    check = true;
                                    account = useraccount;
                                    userid = id;
                                    username = name;
                                    Intent intent = new Intent(Dang_Nhap.this, MainActivity.class);
                                    b.putString("email",mail);
                                    b.putString("role",role);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    Toast.makeText(Dang_Nhap.this, role, Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else
                                    check =false;
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            if (mail.length() != 0 && pw.length() != 0) {
                FbAuth.signInWithEmailAndPassword(mail, pw)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (check) {
                                    user = FbAuth.getCurrentUser();
                                    // Sign in success, update UI with the signed-in user's information
                                   // Toast.makeText(Dang_Nhap.this, "Login Success " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                    // Lưu thông tin đăng nhập khi tắt app
                                    SharedPreferences login = getApplicationContext().getSharedPreferences("loginUser", MODE_PRIVATE);
                                    SharedPreferences.Editor  thanhvien= login.edit();
                                    thanhvien.putString("cusId", user.getUid());
                                    thanhvien.apply();
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Dang_Nhap.this, "Incorrect mail or pass", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                Toast.makeText(getApplicationContext(), "Bạn chưa nhập đủ thông tin.", Toast.LENGTH_LONG).show();
            }
        });
    }
}