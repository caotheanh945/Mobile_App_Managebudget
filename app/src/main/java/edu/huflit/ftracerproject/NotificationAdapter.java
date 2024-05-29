package edu.huflit.ftracerproject;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.huflit.ftracerproject.activity.Dang_Nhap;
import edu.huflit.ftracerproject.activity.MainActivity;
import edu.huflit.ftracerproject.activity.Notification;
import edu.huflit.ftracerproject.database.Notificationdb;
import edu.huflit.ftracerproject.fragment.HomeFragment;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {


    ArrayList<Notificationdb> notificationdbs;
    Listener listener;
    public NotificationAdapter(ArrayList<Notificationdb> notificationdbs,Listener listener) {
        this.notificationdbs = notificationdbs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification,parent,false);
        NotificationAdapter.NotificationVH notificationVH = new NotificationAdapter.NotificationVH(view);
        return notificationVH;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationVH holder, int position) {
            Notificationdb notificationdb = notificationdbs.get(position);
            holder.tvcontent.setText(notificationdb.getContent());
            holder.username.setText(notificationdb.getUsername()+"("+notificationdb.getRole()+")"+":");
            holder.tvdatetime.setText("Vào ngày: "+notificationdb.getDate());
            holder.linearBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.ClickNoti(notificationdb);
                    holder.linearBg.setBackgroundColor(Color.WHITE);
                    holder.tvChecked.setText("Đã xem");
                    HomeFragment.unseen-=1;
                }
            });
            if(notificationdb.getStatus()==true)
            {
                holder.linearBg.setBackgroundColor(Color.WHITE);
                holder.tvChecked.setText("Đã xem");
            }
    }

    @Override
    public int getItemCount() {
        return notificationdbs.size();
    }

    public class NotificationVH extends RecyclerView.ViewHolder{
        ImageView imguser;
        TextView tvcontent, tvChecked,username,tvdatetime;
        RelativeLayout linearBg;

        public NotificationVH(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            tvdatetime = itemView.findViewById(R.id.tvtime);
            imguser = itemView.findViewById(R.id.imguser);
            tvcontent =itemView.findViewById(R.id.tvcontent);
            linearBg = itemView.findViewById(R.id.LinearBackground);
            tvChecked= itemView.findViewById(R.id.tv_checked);
        }
    }
    public interface Listener{
         void ClickNoti(Notificationdb notificationdb);
    }
}
