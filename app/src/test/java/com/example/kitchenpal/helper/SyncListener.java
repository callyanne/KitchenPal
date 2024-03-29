package com.example.kitchenpal.helper;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.concurrent.Semaphore;

/**
 * An extension for Firebase's ValueEventListener, which enables simple synchronisation
 */
public class SyncListener<T> implements ValueEventListener {
    private final Class<T> type;
    private T value = null;

    final Semaphore semaphore = new Semaphore(0);

    public SyncListener(Class<T> type) {
        this.type = type;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        value = dataSnapshot.getValue(type);
        semaphore.release();
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void waitForLoad() throws InterruptedException {
        semaphore.acquire();
    }

    public T getValue() {
        return value;
    }
}
