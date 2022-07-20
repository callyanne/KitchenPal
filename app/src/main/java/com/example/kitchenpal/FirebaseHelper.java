package com.example.kitchenpal;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {

    static FirebaseAuth auth = FirebaseAuth.getInstance();
    static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    static String currUsername = "";

    public FirebaseHelper() {
    }

    public static void getUsernameFB(FirebaseSuccessListener callback) {
        ref.child("users").child(getCurrUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currUsername = snapshot.child("username").getValue(String.class);
                callback.onDataFound(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "failed to retrieve username from Firebase");
            }
        });
    }

    public static String getCurrUsername() {
        return currUsername;
    }

    public static String getCurrUserID() {
        return auth.getCurrentUser().getUid();
    }
}
