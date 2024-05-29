    package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import edu.huflit.ftracerproject.fragment.ChatFragment;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.fragment.BudgetFragment;
import edu.huflit.ftracerproject.fragment.HomeFragment;
import edu.huflit.ftracerproject.fragment.Nhap_Thu_Chi_Fragment;
import edu.huflit.ftracerproject.fragment.ProfileFragment;

    public class MainActivity extends AppCompatActivity {

        public static String email;
        public static String creater;
        public static String username;
        public int Fragment = 1 ;
        public static String role;
    BottomNavigationView btv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btv1  = findViewById(R.id.btv1);
        username = Dang_Nhap.username;
        if (username == null)
            username = Dang_nhap_tv.username;
        Bundle bundle = getIntent().getExtras();
        role = bundle.getString("role");
        email = bundle.getString("email");
        creater = bundle.getString("creater");
        String [] splitmail=email.split("@");
        String user = splitmail[0];
        Bundle bundle1 = new Bundle();
        bundle1.putString("roleus",role);
        bundle1.putString("emailuser",email);
        bundle1.putString("useremail",user);

        ChangeFragment(new HomeFragment());
        btv1.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        ChangeFragment(new HomeFragment());
                        Fragment = 1 ;
                        break;
                    case R.id.add:
                        if(!role.equals("Quản trị viên") && !role.equals("Mẹ")){
                            Toast.makeText(MainActivity.this, "Không phận sự miễn vào", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        ChangeFragment(new Nhap_Thu_Chi_Fragment());
                        break;
                    case R.id.transaction:
                        ChatFragment chatFragment = new ChatFragment();
                        chatFragment.setArguments(bundle1);
                        ChangeFragment(chatFragment);
                        Fragment = 3 ;
                        break;
                    case R.id.budget:
                        ChangeFragment(new BudgetFragment());
                        Fragment = 4 ;
                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        profileFragment.setArguments(bundle1);
                        ChangeFragment(profileFragment);
                        Fragment = 5 ;
                        break;
                }
                return true;
            }
        });
    }

        @Override
        protected void onResume() {
        if(Fragment == 1)
            ChangeFragment(new HomeFragment());
        super.onResume();
        }

        public void ChangeFragment(Fragment fmNew) {
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.container1, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }
}