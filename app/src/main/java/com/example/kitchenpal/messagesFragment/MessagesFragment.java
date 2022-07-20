package com.example.kitchenpal.messagesFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.MemoryData;
import com.example.kitchenpal.R;
import com.example.kitchenpal.adapters.MessagesAdapter;
import com.example.kitchenpal.models.MessagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends Fragment {

    public MessagesFragment() {
        // Required empty public constructor
    }

    private List<MessagesList> messagesLists = new ArrayList<>();
    private String username;
    private RecyclerView messagesRecyclerView;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kitchenpal-a0cda-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private int unseenMsg = 0;
    private String lastMsg = "";
    private String chatKey = "";
    private boolean dataSet = false;
    private MessagesAdapter messagesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messages, container, false);

        messagesRecyclerView = root.findViewById(R.id.messages_view);

        username = FirebaseHelper.getCurrUsername();

        //set adapter to recyclerview
        messagesAdapter = new MessagesAdapter(messagesLists, getActivity());
        messagesRecyclerView.setAdapter(messagesAdapter);

        messagesRecyclerView.setHasFixedSize(true);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();
                unseenMsg = 0;
                lastMsg = "";
                chatKey = "";
                for(DataSnapshot dataSnapshot : snapshot.child("user").getChildren()) {

                    String getUsernameTwo = dataSnapshot.child("username").getValue(String.class);

                    dataSet = false;
                    if(!getUsernameTwo.equals(username)) {

                        ref.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if (getChatCounts > 0) {

                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        if(dataSnapshot1.hasChild("user1") && dataSnapshot1.hasChild("user1") && dataSnapshot1.hasChild("messages")) {
                                            final String getUserOne = dataSnapshot1.child("user1").getValue(String.class);
                                            final String getUserTwo = dataSnapshot1.child("user2").getValue(String.class);

                                            if (getUserOne.equals(username) && getUserTwo.equals(getUsernameTwo) || getUserOne.equals(getUsernameTwo) && getUserTwo.equals(username)) {
                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {

                                                    long getMsgKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    long getLastSeenMsg = Long.parseLong(MemoryData.getLastMsgTS(getActivity(), getKey));

                                                    lastMsg = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if (getMsgKey > getLastSeenMsg) {
                                                        unseenMsg++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if(!dataSet) {
                                    dataSet = true;
                                    MessagesList messagesList = new MessagesList(username, lastMsg, unseenMsg, chatKey);
                                    messagesLists.add(messagesList);
                                    messagesAdapter.updateData(messagesLists);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}