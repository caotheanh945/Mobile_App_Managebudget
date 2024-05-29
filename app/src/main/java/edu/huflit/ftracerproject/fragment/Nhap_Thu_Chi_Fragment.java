package edu.huflit.ftracerproject.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.units.qual.A;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.huflit.ftracerproject.CategoriesAdapter;
import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.activity.Dang_Nhap;
import edu.huflit.ftracerproject.activity.Dang_nhap_tv;
import edu.huflit.ftracerproject.activity.MainActivity;
import edu.huflit.ftracerproject.activity.PickRoleLogin;
import edu.huflit.ftracerproject.database.Categories;

public class Nhap_Thu_Chi_Fragment extends Fragment {

    FirebaseFirestore db;
    public ArrayList<String> cateArray;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    public static ArrayList dsQQ;
    public static ArrayList categories;
    EditText levyEditText,titlechiEditText;
    boolean savebuttonisselected=false;
    boolean levyEditTextisSelected=false;
    boolean titlechiEditTextisSelected=false;
    Double amount = 0.0;
    private HomeFragment homeFragment = HomeFragment.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhap__thu__chi_, container, false);
        // Ánh xạ các thành phần giao diện vào biến instance
        levyEditText = view.findViewById(R.id.levyEditText);
        levyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levyEditTextisSelected=true;
                titlechiEditTextisSelected=false;
                showDatePicker();
            }
        });
        EditText levyamountEditText = view.findViewById(R.id.levyamountEditText);
        EditText notelevyEditText = view.findViewById(R.id.notelevyEditText);
        Button levysaveButton = view.findViewById(R.id.levysaveButton);
        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,getArray());
        spinner.setAdapter(adapter);
        titlechiEditText = view.findViewById(R.id.titlechiEditText);
        titlechiEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titlechiEditTextisSelected=true;
                levyEditTextisSelected=false;
                showDatePicker();
            }
        });
        EditText amountchiEditText = view.findViewById(R.id.amountchiEditText);
        EditText notechiEditText = view.findViewById(R.id.notechiEditText);
        Button saveButton = view.findViewById(R.id.saveButton);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Xử lý sự kiện khi nhấn nút "Thêm khoản thu"
        levysaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savebuttonisselected = false;
                // Lấy thông tin ngày, số tiền, ghi chú từ EditText
                String date = levyEditText.getText().toString();
                Double money = Double.parseDouble(levyamountEditText.getText().toString());
                String note = notelevyEditText.getText().toString();

                // Tiến hành lưu thông tin này vào Firestore trong tài khoản của khách hàng
                // (Bạn cần phải có mã xử lý để đăng nhập và xác định tài khoản của từng khách hàng trước khi lưu dữ liệu)

                // Gọi hàm lưu dữ liệu vào Firestore ở đây
                saveDataToFirestore("report", date, money, note,null);
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm khoản chi"
         saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkmoney;
                if(Double.parseDouble(amountchiEditText.getText().toString())>HomeFragment.totalAmount)
                {
                    checkmoney=false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông báo")
                            .setMessage("Có đủ tiền đâu mà mua :(")
                            .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .show();
                }
                else
                {
                    checkmoney=true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông báo")
                            .setMessage("Đã trừ vào số dư tài khoản")
                            .setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            })
                            .show();
                }
                if (checkmoney==false)
                    return;
                savebuttonisselected =true;
                // Lấy thông tin ngày, số tiền, ghi chú từ EditText
                String date = titlechiEditText.getText().toString();
                Double money = Double.parseDouble(amountchiEditText.getText().toString());
                String note = notechiEditText.getText().toString();
                String cate = spinner.getSelectedItem().toString();
                // Tiến hành lưu thông tin này vào Firestore trong tài khoản của khách hàng
                // (Bạn cần phải có mã xử lý để đăng nhập và xác định tài khoản của từng khách hàng trước khi lưu dữ liệu)
                // Gọi hàm lưu dữ liệu vào Firestore ở đây
                cateArray = new ArrayList<String>();
                saveDataToFirestore("report", date, money, note,cate);
            }
         });
        return view;
    }
    private void saveDataToFirestore(String collectionName, String date, Double money, String note,String cate) {
        // Thực hiện lưu dữ liệu lên Firestore database
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        // Tạo một tài liệu mới
        String account = Dang_Nhap.account;
        if (account==null)
            account = Dang_nhap_tv.account;
        Map<String, Object> thuChi = new HashMap<>();
        if(savebuttonisselected)
        {
            thuChi.put("date", date);
            thuChi.put("Amount", money);
            thuChi.put("note", note);
            thuChi.put("username", MainActivity.username);
            thuChi.put("useraccount", account);
            thuChi.put("kind","chi");
            thuChi.put("cate",cate);
        }else
        {
            thuChi.put("date", date);
            thuChi.put("Amount", money);
            thuChi.put("note", note);
            thuChi.put("kind","thu");
            thuChi.put("username", MainActivity.username);
            thuChi.put("useraccount", account);
        }

        // Tiến hành lưu dữ liệu vào Firestore
        db.collection(collectionName)
                .add(thuChi)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                        if(savebuttonisselected)
                            HomeFragment.totalAmount -=amount;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public static ArrayList getArray(){
        return dsQQ;
    }
    public static void setArray(ArrayList arrayList){
        dsQQ = arrayList;
    }

    public static ArrayList getCategories(){
        return categories;
    }

    public static void setCategories(ArrayList arrayList){
        categories = arrayList;
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    if(levyEditTextisSelected)
                        levyEditText.setText(formattedDate);
                    if(titlechiEditTextisSelected)
                        titlechiEditText.setText(formattedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }
}