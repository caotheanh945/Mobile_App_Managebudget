package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.UserAdapter;
import edu.huflit.ftracerproject.database.User;

public class Family extends AppCompatActivity {
Integer count=0;
RecyclerView rcvfamily;
UserAdapter adapter;
User user;
ArrayList arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        Toolbar toolbar =  findViewById(R.id.toolbarfamily);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcvfamily = findViewById(R.id.rcvfamily);
        adapter = new UserAdapter(arrayList,Family.this);
        rcvfamily.setAdapter(adapter);
        rcvfamily.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        TextView members = findViewById(R.id.members);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                            String account = queryDocumentSnapshot.get("account").toString();
                            String username = queryDocumentSnapshot.get("username").toString();
                            String role = queryDocumentSnapshot.get("role").toString();
                            String creater = queryDocumentSnapshot.get("creater").toString();
                            if(creater.equals(MainActivity.email) || account.equals(MainActivity.email) || account.equals(MainActivity.creater) || creater.equals(MainActivity.creater))
                            {
                                count++;
                                user = new User(username,account,role);
                                arrayList.add(user);
                            }
                        }
                    members.setText(count.toString());
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