package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.huflit.ftracerproject.NotificationAdapter;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.Categories;
import edu.huflit.ftracerproject.database.Notificationdb;
import edu.huflit.ftracerproject.fragment.HomeFragment;

public class Notification extends AppCompatActivity implements NotificationAdapter.Listener{

    public static ArrayList notificationdbs = new ArrayList<>();
    public boolean status;
    RecyclerView rcvNotification;
    FirebaseFirestore fb;
    String id;
    public static NotificationAdapter adapter;
    FloatingActionButton btnaddnoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        btnaddnoti = findViewById(R.id.btnaddnoti);
        btnaddnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Notification.this,TaoThongBao.class));
            }
        });
        Toolbar toolbar =  findViewById(R.id.toolbarnotification);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcvNotification = findViewById(R.id.rcvnotification);
        rcvNotification.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter = new NotificationAdapter(notificationdbs,this);
        rcvNotification.setAdapter(adapter);
        fb = FirebaseFirestore.getInstance();
        fb.collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        notificationdbs.clear();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            id=documentSnapshot.getId();
                            String content = documentSnapshot.get("content").toString();
                            String username = documentSnapshot.get("username").toString();
                            String role = documentSnapshot.get("role").toString();
                            String datetime = documentSnapshot.get("date").toString();
                            status = documentSnapshot.getBoolean("status");
                            Notificationdb notificationdb = new Notificationdb(id,content,username,role,datetime,status);
                            notificationdbs.add(notificationdb);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ClickNoti(Notificationdb notificationdb) {
        fb.collection("notification")
                .document(notificationdb.getId())
                .update("status",true)
                .addOnSuccessListener(aVoid -> {
                    // Xử lý khi thành công
                    Log.d("Firestore", "Trường đã được cập nhật thành công");
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi không thành công
                    Log.e("Firestore", "Lỗi khi cập nhật trường", e);
                });
    }
}