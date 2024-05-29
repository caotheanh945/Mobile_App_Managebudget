package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.User;

public class Dang_Ky_Tv extends AppCompatActivity {
    TextView tvAccounttv, tvPassuser,tvusername;
    Button dkytv;
    FirebaseAuth FbAuth;
    String iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tv);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        dkytv = findViewById(R.id.btndkytv);
        tvAccounttv = findViewById(R.id.accounttv);
        tvPassuser = findViewById(R.id.passtv);
        tvusername = findViewById(R.id.usernametv);
        User user = new User();
        RadioGroup radioGroup = findViewById(R.id.role);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.wife){
                     user.setRole("Vợ");
                }
                else if(i == R.id.oldpeople){
                    user.setRole("Ông/bà");
                }
                else if(i == R.id.children){
                    user.setRole("Con");
                }
            }
        });
        dkytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setAccount(tvAccounttv.getText().toString());
                user.setPassword(tvPassuser.getText().toString());
                user.setCreater(MainActivity.email);
                user.setUsername(tvusername.getText().toString());
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
                    finish();
            }
        });

    }
}