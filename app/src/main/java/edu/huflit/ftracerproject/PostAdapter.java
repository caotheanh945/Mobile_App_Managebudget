package edu.huflit.ftracerproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.huflit.ftracerproject.database.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostVH> {

    ArrayList<Post> posts = new ArrayList<>();
    Context context;


    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dangtin,parent,false);
        PostAdapter.PostVH postVH = new PostAdapter.PostVH(view);
        return postVH;
    }

    @Override
    public void onBindViewHolder(@NonNull PostVH holder, int position) {
            Post post = posts.get(position);
            holder.tvcaption.setText(post.getPostcontent());
            holder.tvusername.setText(post.getUsername());
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference(post.getImgname()+".png");
        try
        {
            File localfile = File.createTempFile("tempfile",".png");
            firebaseStorage.getFile(localfile)
                    .addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            holder.imgpost.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostVH extends RecyclerView.ViewHolder{
        ImageView imguserdangtin,imgpost;
        TextView tvusername, tvcaption;

        public PostVH(@NonNull View itemView) {
            super(itemView);
            imguserdangtin = itemView.findViewById(R.id.imguserdangtin);
            tvusername =itemView.findViewById(R.id.tvusername);
            tvcaption= itemView.findViewById(R.id.tvcaption);
            imgpost = itemView.findViewById(R.id.imgpost);
        }
    }
}
