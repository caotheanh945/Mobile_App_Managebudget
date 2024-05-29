package edu.huflit.ftracerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import edu.huflit.ftracerproject.database.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH>{

    ArrayList <User> arrayList;

    Context context;

    public UserAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.familymembers,parent,false);
        UserAdapter.UserVH userVH = new UserAdapter.UserVH(view);
        return userVH;
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {
        User user = arrayList.get(position);
        holder.membername.setText(user.getUsername());
        holder.rolemember.setText(user.getRole());
        if ("Quản trị viên".equals(user.getRole())) {
            holder.imgmember.setImageBitmap(Utils.converttoBitmapFromAsset(context,"admin.png"));
        } else if ("Ông/bà".equals(user.getRole())) {
            holder.imgmember.setImageBitmap(Utils.converttoBitmapFromAsset(context,"old.jpg"));
        } else if ("Mẹ".equals(user.getRole())) {
            holder.imgmember.setImageBitmap(Utils.converttoBitmapFromAsset(context,"parent.jpg"));
        } else if ("Con".equals(user.getRole())) {
            holder.imgmember.setImageBitmap(Utils.converttoBitmapFromAsset(context,"children.jpg"));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class UserVH extends RecyclerView.ViewHolder{
        ImageView imgmember;
        TextView membername, rolemember;

        public UserVH(@NonNull View itemView) {
            super(itemView);
            imgmember = itemView.findViewById(R.id.imgmember);
            membername =itemView.findViewById(R.id.membername);
            rolemember= itemView.findViewById(R.id.rolemember);
        }
    }
}
