package edu.huflit.ftracerproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import edu.huflit.ftracerproject.PhotoAdapter;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.photo;
import me.relex.circleindicator.CircleIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PickRoleLogin extends AppCompatActivity {

    private ViewPager viewPapers;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
Button admin,thanhvien,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_role_login);
        admin = findViewById(R.id.btnloginadmin);
        thanhvien = findViewById(R.id.btnloginthanhvien);
        btnRegister = findViewById((R.id.btn_dk));
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PickRoleLogin.this,Dang_Nhap.class);
                startActivity(i);
            }
        });
        thanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PickRoleLogin.this,Dang_nhap_tv.class);
                startActivity(i);
            }
        });
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(PickRoleLogin.this, Dang_Ky.class);
            startActivity(intent);
        });
        viewPapers = findViewById(R.id.viewpaper);
        circleIndicator = findViewById(R.id.circle_indicator);
        photoAdapter = new PhotoAdapter(this, getListPhoto());
        viewPapers.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPapers);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<photo> getListPhoto(){
        List<photo> list = new ArrayList<>();
        list.add(new photo(R.drawable.theanh));
        list.add(new photo(R.drawable.theanh1));
        list.add(new photo(R.drawable.theanh2));
        list.add(new photo(R.drawable.theanh3));
        return  list;
    }
}