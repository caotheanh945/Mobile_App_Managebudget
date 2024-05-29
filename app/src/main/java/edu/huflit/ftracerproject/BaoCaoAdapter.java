package edu.huflit.ftracerproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import edu.huflit.ftracerproject.database.BaoCao;
import edu.huflit.ftracerproject.database.User;

public class BaoCaoAdapter extends RecyclerView.Adapter<BaoCaoAdapter.Baocaovh>{
    ArrayList<BaoCao> arrayList;
    Context context;

    public BaoCaoAdapter(ArrayList<BaoCao> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Baocaovh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembaocao,parent,false);
        BaoCaoAdapter.Baocaovh baocaovh = new BaoCaoAdapter.Baocaovh(view);
        return baocaovh;
    }

    @Override
    public void onBindViewHolder(@NonNull Baocaovh holder, int position) {
        BaoCao baoCao = arrayList.get(position);
        if(baoCao.getKind().equals("thu")) {
            holder.imguser.setImageBitmap(Utils.converttoBitmapFromAsset(context, "receive-amount.png"));
            holder.tvname.setText(baoCao.getUsername() + " đã thêm tiền vào");
        }
        else if (baoCao.getKind().equals("chi")) {
            holder.imguser.setImageBitmap(Utils.converttoBitmapFromAsset(context, "money-transfer.png"));
            holder.tvname.setText(baoCao.getUsername() + " đã rút tiền ");
        }
        holder.tvmoney.setText(baoCao.getMoney().toString());
        holder.tvdatetime.setText(baoCao.getDatetime());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Baocaovh extends RecyclerView.ViewHolder{
        ImageView imguser;
        TextView tvname, tvmoney,tvdatetime;

        public Baocaovh(@NonNull View itemView) {
            super(itemView);
            imguser = itemView.findViewById(R.id.imguser);
            tvname =itemView.findViewById(R.id.tvname);
            tvmoney= itemView.findViewById(R.id.tvmoney);
            tvdatetime= itemView.findViewById(R.id.tvdatetime);
        }
    }
}
