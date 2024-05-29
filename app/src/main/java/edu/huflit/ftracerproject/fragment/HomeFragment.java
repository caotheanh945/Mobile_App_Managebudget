package edu.huflit.ftracerproject.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.huflit.ftracerproject.activity.BaoCaoThuChi;
import edu.huflit.ftracerproject.activity.Dang_Nhap;
import edu.huflit.ftracerproject.activity.MainActivity;
import edu.huflit.ftracerproject.activity.Notification;
import edu.huflit.ftracerproject.database.Categories;
import edu.huflit.ftracerproject.CategoriesAdapter;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.Notificationdb;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView rcv;
    BottomNavigationView btv;
    Double amount = 0.0;
    public static int unseen = 0;
    FirebaseFirestore fb ;
    public static Double totalAmount=0.0;
    int finalJ=0;
    public static Double totalchi=0.0;
    TextView income,notitotal,baocao,tvchitieunhieunhat;
    public ArrayList dsQQ;
    public ArrayList categories = new ArrayList<>();
    public ArrayList<String> cateArray= new ArrayList<>();
    private static final HomeFragment instance = new HomeFragment();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment getInstance() {
        return instance;
    }
    public ArrayList getDataList() {
        return categories;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvchitieunhieunhat = view.findViewById(R.id.tvchitieunhieunhat);
        notitotal = view.findViewById(R.id.notitotal);
        baocao = view.findViewById(R.id.tv7);
        baocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BaoCaoThuChi.class));
            }
        });
        ImageView imgnoti = view.findViewById(R.id.imgnoti);
        imgnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Notification.class);
                startActivity(i);
            }
        });
    /*    ChangeFragment(new WeekFragment());*/
        income = view.findViewById(R.id.tvTotalmoney);
        fb = FirebaseFirestore.getInstance();
        rcv = view.findViewById(R.id.rcv);
        dsQQ = new ArrayList<>();
        CategoriesAdapter adapter = new CategoriesAdapter(categories,getContext());
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        fb.collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        unseen = 0;
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            boolean status = documentSnapshot.getBoolean("status");
                            if (status == false)
                                unseen+=1;
                        }
                        if(unseen==0)
                            notitotal.setVisibility(View.INVISIBLE);
                        else
                            notitotal.setText(Integer.toString(unseen));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        fb.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                           String idcate = documentSnapshot.getId();
                           String namecate = documentSnapshot.get("Name").toString();
                           String imgcate = documentSnapshot.get("Image").toString();
                           Double spent = documentSnapshot.getDouble("spent");
                           Categories categories1  = new Categories(idcate,namecate,imgcate,spent);
                           categories.add(categories1);
                           cateArray.add(namecate);
                           dsQQ.add(namecate);
                       }
                        adapter.notifyDataSetChanged();
                        Nhap_Thu_Chi_Fragment.setCategories(categories);
                        Nhap_Thu_Chi_Fragment.setArray(dsQQ);
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            fb.collection("report")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            amount=0.0;
                                            for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult())
                                            {
                                                Double money = queryDocumentSnapshot.getDouble("Amount");
                                                String kind = queryDocumentSnapshot.get("kind").toString();
                                                if(kind.equals("chi")) {
                                                    String cate1 = queryDocumentSnapshot.get("cate").toString();
                                                    if(cate1.equals(cateArray.get(finalJ)))
                                                    {
                                                        amount += money;
                                                        updateCategories(finalJ +1,amount);
                                                    }
                                                }

                                            }
                                            adapter.notifyDataSetChanged();
                                            finalJ++;
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        fb.collection("report")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        totalAmount = 0.0;
                        totalchi=0.0;
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Double amount= documentSnapshot.getDouble("Amount");
                            String kind = documentSnapshot.get("kind").toString();
                            if(kind.equals("thu"))
                                totalAmount+=amount;
                            if(kind.equals("chi")) {
                                totalAmount -= amount;
                                totalchi+=amount;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        tvchitieunhieunhat.setText("Số tiền đã chi: "+totalchi);
                        income.setText(totalAmount.toString());
                      //  income.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    /*    btv= view.findViewById(R.id.btv);
        btv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.week:
                        ChangeFragment(new WeekFragment());
                        break;
                    case R.id.month:
                        ChangeFragment(new MonthFragment());
                        break;
                }
                return true;
            }
        });*/
    }
    private void updateCategories(Integer id,Double money)
    {
        DocumentReference documentReference = fb.collection("categories").document(id.toString());
        Map<String, Object> updates = new HashMap<>();
        updates.put("spent", money);
        documentReference.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xảy ra lỗi
                    }
                });
    }
  /*  public void ChangeFragment(Fragment fmNew) {
        FragmentTransaction fmTran = getActivity().getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.container, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }*/
}