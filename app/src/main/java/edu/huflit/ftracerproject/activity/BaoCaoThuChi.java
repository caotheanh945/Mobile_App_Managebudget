package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import edu.huflit.ftracerproject.BaoCaoAdapter;
import edu.huflit.ftracerproject.NotificationAdapter;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.BaoCao;
import edu.huflit.ftracerproject.database.User;

public class BaoCaoThuChi extends AppCompatActivity {
    RecyclerView rcvBaocao;
    ArrayList arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_thu_chi);
        Toolbar toolbar =  findViewById(R.id.toolbarnotification);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcvBaocao = findViewById(R.id.rcvbaocao);
        rcvBaocao.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        BaoCaoAdapter adapter = new BaoCaoAdapter(arrayList,this);
        rcvBaocao.setAdapter(adapter);
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("report")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                            Double money = queryDocumentSnapshot.getDouble("Amount");
                            String kind = queryDocumentSnapshot.get("kind").toString();
                            String datetime = queryDocumentSnapshot.get("date").toString();
                            String username = queryDocumentSnapshot.get("username").toString();
                            BaoCao baoCao  = new BaoCao(username,money,datetime,kind);
                            arrayList.add(baoCao);
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
}