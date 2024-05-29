package edu.huflit.ftracerproject.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.database.Post;
import edu.huflit.ftracerproject.fragment.BudgetFragment;

public class PostActi extends AppCompatActivity {

    TextView username;
    EditText edtpostcontent;
    ImageButton imgbtnBack;
    Button btnpost, btnChoose;
    ImageView imgViewPost;
    Uri ImageUrl = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        username = findViewById(R.id.tvuserpostname);
        btnpost = findViewById(R.id.btnpost);
        edtpostcontent = findViewById(R.id.edtpostcontent);
        btnChoose = findViewById(R.id.btnChoose);
        imgViewPost = findViewById(R.id.imgViewPost);
        imgbtnBack = findViewById(R.id.btnBack);

        StorageReference storageRef = storage.getReferenceFromUrl("gs://qlchitieu-ae36b.appspot.com");


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        username.setText(Dang_Nhap.username);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        imgbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageName = UUID.randomUUID().toString();
                StorageReference mountainsRef = storageRef.child(imageName + ".png");
                imgViewPost.setDrawingCacheEnabled(true);
                imgViewPost.buildDrawingCache();
                if(imgViewPost.getDrawable()==null)
                {
                    Post post = new Post();
                    post.setPostcontent(edtpostcontent.getText().toString());
                    String name = Dang_Nhap.username;
                    if(name == null)
                        name = Dang_nhap_tv.username;
                    post.setUsername(name);
                    post.setImgname("null");
                    db.collection("post")
                        .add(post)
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
                    BudgetFragment.arraypost.add(post);
                    BudgetFragment.adapter.notifyDataSetChanged();
                    finish();
                }
                else {
                    Bitmap bitmap = ((BitmapDrawable) imgViewPost.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(PostActi.this, "Fail cmnr", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Post post = new Post();
                            post.setPostcontent(edtpostcontent.getText().toString());
                            String name = Dang_Nhap.username;
                            if (name == null)
                                name = Dang_nhap_tv.username;
                            post.setUsername(name);
                            post.setImgname(imageName);
                            db.collection("post")
                                    .add(post)
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
                            BudgetFragment.arraypost.add(post);
                            BudgetFragment.adapter.notifyDataSetChanged();

                        }
                    });

                }
                finish();
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
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            ImageUrl = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUrl);
                imgViewPost.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




}