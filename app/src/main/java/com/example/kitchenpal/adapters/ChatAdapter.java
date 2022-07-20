package com.example.kitchenpal.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.MemoryData;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.ChatList;

import org.w3c.dom.Text;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Viewholder> {

    private final List<ChatList> list;
    private final Context context;
    private String username;

    public ChatAdapter(List<ChatList> list, Context context) {
        this.list = list;
        this.context = context;
        this.username = MemoryData.getName(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ChatList list2 = list.get(position);

        if(list2.getUsername().equals(username)) {
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);
        } else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMsg, myMsg;
        private TextView oppoTime, myTime;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMsg = itemView.findViewById(R.id.oppoMessage);
            myMsg = itemView.findViewById(R.id.myMessage);
            oppoTime = itemView.findViewById(R.id.oppoTimeSent);
            myTime = itemView.findViewById(R.id.myTimeSent);

        }
    }
}
