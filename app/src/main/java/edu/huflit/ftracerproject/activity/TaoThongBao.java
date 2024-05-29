package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.Notificationdb;
import edu.huflit.ftracerproject.fragment.HomeFragment;

public class TaoThongBao extends AppCompatActivity {
    TextInputEditText textInputEditText,noidungthongbao;
    Button btntaothongbao;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_thong_bao);
        textInputEditText = findViewById(R.id.TaoThongBao);
        noidungthongbao = findViewById(R.id.noidungthongbao);
        btntaothongbao = findViewById(R.id.btntaothongbao);
        imgback = findViewById(R.id.img_Back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        btntaothongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notificationdb notificationdb = new Notificationdb();
                notificationdb.setContent(noidungthongbao.getText().toString());
                notificationdb.setDate(textInputEditText.getText().toString());
                notificationdb.setStatus(false);
                notificationdb.setUsername(MainActivity.username);
                notificationdb.setRole(MainActivity.role);
                fb.collection("notification")
                        .add(notificationdb)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(TaoThongBao.this, "Tạo thông báo thành công", Toast.LENGTH_SHORT).show();
                                HomeFragment.unseen+=1;
                            }
                        });
                Notification.notificationdbs.add(notificationdb);
                Notification.adapter.notifyDataSetChanged();
                finish();
            }
        });
        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    textInputEditText.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}