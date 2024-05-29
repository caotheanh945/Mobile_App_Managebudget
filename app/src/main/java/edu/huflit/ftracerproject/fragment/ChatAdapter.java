package edu.huflit.ftracerproject.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.ftracerproject.R;
import edu.huflit.ftracerproject.activity.MainActivity;
import edu.huflit.ftracerproject.database.Chat;
import edu.huflit.ftracerproject.databinding.ItemRecievedMessageBinding;
import edu.huflit.ftracerproject.databinding.ItemSentMessageBinding;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;
    ArrayList<Chat> chatArrayList = new ArrayList<>();

    public ChatAdapter(ArrayList<Chat> chatArrayList) {
        this.chatArrayList = chatArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      /*  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchat,parent,false);
        ChatAdapter.ChatVH ChatVH = new ChatAdapter.ChatVH(view);
        return ChatVH;*/
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(
                    ItemSentMessageBinding.inflate(
                            LayoutInflater.from((parent.getContext())),
                            parent,
                            false
                    )
            );
        }
        else {
            return new ReceivedMessageViewHolder(
                    ItemRecievedMessageBinding.inflate(LayoutInflater.from((parent.getContext())), parent, false
                    )
            );
        }
    }

    @Override
    public int getItemViewType(int position) {
            if(chatArrayList.get(position).getUsername().equals(MainActivity.username)){
                return VIEW_TYPE_SENT;
            }
            else return VIEW_TYPE_RECEIVED;
        }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatArrayList.get(position));
        }
        else
        {
            ((ReceivedMessageViewHolder) holder).setData(chatArrayList.get(position));
            ((ReceivedMessageViewHolder) holder).setUsername(chatArrayList.get(position));
        }
    }

   /*@Override
    public void onBindViewHolder(@NonNull ChatVH holder, int position) {
                Chat chat = chatArrayList.get(position);
                holder.chatcontent.setText(chat.getChatcontent());
    }*/

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }


    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemRecievedMessageBinding binding;

        ReceivedMessageViewHolder(ItemRecievedMessageBinding itemReceivedMessageBinding){
            super(itemReceivedMessageBinding.getRoot());
            binding = itemReceivedMessageBinding;
        }
        void setData(Chat message){
            binding.chatcontent.setText(message.getChatcontent());
        }
        void setUsername(Chat message){
            binding.textView10.setText(message.getUsername());
        }
    }
    static class SentMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemSentMessageBinding binding;

        SentMessageViewHolder(ItemSentMessageBinding itemSentMessageBinding){
            super(itemSentMessageBinding.getRoot());
            binding = itemSentMessageBinding;
        }
        void setData(Chat message){
            binding.sentcontent.setText(message.getChatcontent());
        }
    }

}

