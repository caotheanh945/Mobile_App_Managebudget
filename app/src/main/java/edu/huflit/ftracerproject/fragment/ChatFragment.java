package edu.huflit.ftracerproject.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.activity.Dang_Ky_Tv;
import edu.huflit.ftracerproject.activity.Dang_Nhap;
import edu.huflit.ftracerproject.activity.Dang_nhap_tv;
import edu.huflit.ftracerproject.activity.MainActivity;
import edu.huflit.ftracerproject.database.Categories;
import edu.huflit.ftracerproject.database.Chat;import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.app.Activity;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        TextView dangkytv = view.findViewById(R.id.dangkytv);
        TextInputEditText textInputEditText = view.findViewById(R.id.edUserName);
        EditText content = view.findViewById(R.id.edUserName);
        ImageButton btnsend = view.findViewById(R.id.btnsend);
        RecyclerView rcvChat = view.findViewById(R.id.rcvChat);
        TextView tv8 = view.findViewById(R.id.textView8);
        ArrayList chatarraylist = new ArrayList();
        rcvChat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        ChatAdapter chatAdapter= new ChatAdapter(chatarraylist);
        rcvChat.setAdapter(chatAdapter);
        dangkytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Dang_Ky_Tv.class);
                startActivity(i);
            }
        });
        if(!bundle.getString("roleus").equals("Quản trị viên")){
            dangkytv.setVisibility(View.INVISIBLE);
        }
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("chat")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot querySnapshot, FirebaseFirestoreException e) {
                        if (e != null) {
                            // Xử lý lỗi
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                String chatcontent = change.getDocument().getString("chatcontent");
                                String username = change.getDocument().getString("username");

                                if (chatcontent != null && username != null) {
                                    Chat chat = new Chat(chatcontent, username);
                                    chatarraylist.add(chat);
                                }
                            }

                            chatAdapter.notifyDataSetChanged();
                            rcvChat.scrollToPosition(chatarraylist.size()-1);
                            if (chatarraylist.size() != 0)
                                tv8.setVisibility(View.INVISIBLE);
                        }
                    }
                });
       /* if(chatarraylist.isEmpty()) {
            fb.collection("chat")
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String chatcontent = documentSnapshot.get("chatcontent").toString();
                                String username = documentSnapshot.get("username").toString();
                                Chat chat = new Chat(chatcontent, username);
                                chatarraylist.add(chat);
                            }
                            chatAdapter.notifyDataSetChanged();
                            if (chatarraylist.size() != 0)
                                tv8.setVisibility(View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        }*/
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> chat = new HashMap<>();
                String chatcontent = content.getText().toString();
                chat.put("chatcontent",chatcontent);
                chat.put("username",MainActivity.username);
                chat.put("timestamp",new Date());
                fb.collection("chat")
                        .add(chat)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                chatAdapter.notifyDataSetChanged();
                View focusedView = getActivity().getCurrentFocus();
                inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                textInputEditText.setText("");
            }

        });

    }
}