package com.example.kitchenpal.messagesFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.MemoryData;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.ChatList;
import com.example.kitchenpal.models.MessagesList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private final List<ChatList> chatLists = new ArrayList<>();
    private RecyclerView chatRecyclerView;
    private String chatKey;
    private String receiverUsername;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kitchenpal-a0cda-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageButton backBtn = findViewById(R.id.chat_back_button);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final TextView userName = findViewById(R.id.username);
        final TextView status = findViewById(R.id.status);
        final EditText chatInput = findViewById(R.id.chat_input);
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        //get data from messages adapter class
        receiverUsername = getIntent().getStringExtra("username");
        chatKey = getIntent().getStringExtra("chat key");

        userName.setText(receiverUsername);

        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        if (chatKey.isEmpty()) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (chatKey.isEmpty()) {
                            //generate chat key
                            chatKey = "1";

                        if (snapshot.hasChild("chat")) {
                            chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                        }
                    }

                    if (snapshot.hasChild("chat")) {

                        if(snapshot.child("chat").child(chatKey).hasChild("messages")) {

                            for(DataSnapshot msgSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {

                                if(msgSnapshot.hasChild("msg") && msgSnapshot.hasChild("username")) {

                                    final String msgTimestamp = msgSnapshot.getKey();
                                    final String getUsername = msgSnapshot.child("username").getValue(String.class);
                                    final String getMsg = msgSnapshot.child("msg").getValue(String.class);
                                }
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getTxtMessage = chatInput.getText().toString();

                //get current timestamps
                final String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                MemoryData.saveLastMsgTS(currentTimestamp, chatKey, ChatActivity.this);
                ref.child("chat").child(chatKey).child("user1").setValue(FirebaseHelper.getCurrUsername());
                ref.child("chat").child(chatKey).child("user2").setValue(receiverUsername);
                ref.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);

                //clear edit text
                chatInput.setText("");

                //
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}