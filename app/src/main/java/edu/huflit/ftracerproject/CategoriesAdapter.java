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

import edu.huflit.ftracerproject.database.Categories;
import edu.huflit.ftracerproject.fragment.HomeFragment;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesVH> {
    ArrayList<Categories> categoriesArraylist;
    public static Categories categories;
    Context context;

    public CategoriesAdapter(ArrayList<Categories> categoriesArraylist, Context context) {
        this.categoriesArraylist = categoriesArraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcate,parent,false);
        CategoriesVH categoriesVH = new CategoriesVH(view);
        return categoriesVH;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesVH holder, int position) {
            categories = categoriesArraylist.get(position);
            holder.tvnamecate.setText(categories.getNamecate());
            holder.imgcate.setImageBitmap(Utils.converttoBitmapFromAsset(context,categories.getImgCate()));
            Double money = categories.getTotal();
            Double total = HomeFragment.totalchi;
            Double percent = (money/total)*100;
            if(categories.getTotal()!=null)
                holder.tvamount.setText(percent+"%");
    }

    @Override
    public int getItemCount() {
        return categoriesArraylist.size();
    }

    public class CategoriesVH extends RecyclerView.ViewHolder{
        ImageView imgcate;
        TextView tvnamecate,tvamount;
        public CategoriesVH(@NonNull View itemView) {
            super(itemView);
            imgcate = itemView.findViewById(R.id.imgcate);
            tvnamecate =itemView.findViewById(R.id.tvNamecate);
            tvamount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
