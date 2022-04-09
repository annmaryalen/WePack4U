package com.example.wepack4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class StorePage extends AppCompatActivity {
    //below this is where the data is inputted in
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String auth_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String campus;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);
        recyclerView = findViewById(R.id.recyclerview);

        getStoreList();

    }
    //TODO make the clickable in a list?
    public void goestostore(View view){
        Toast.makeText(this,"selected",Toast.LENGTH_SHORT).show();
    }

    public void getStoreList(){
        DocumentReference userRef = db.collection("users").document(auth_uid);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()){
                        campus = (userDoc.getString("campus"));
                        storeList(campus);
                    }
                    else{
                        Log.d("get_campus", "does not exist");
                    }
                }
            }
        });
    }

    public void storeList(String campus_name) {
        db.collection("Campus").whereEqualTo("name", campus_name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String school_id = document.getId();
                        db.collection("Campus").document(school_id).collection("food_stores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    QuerySnapshot querySnapshot = task.getResult();
                                    List<FoodStore> foodStores = querySnapshot.toObjects(FoodStore.class);
                                    StoreAdapter storeAdapter = new StoreAdapter(StorePage.this, foodStores);
                                    recyclerView.setAdapter(storeAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(StorePage.this));
                                    System.out.println(foodStores.get(0).store_name);
                                }
                            }
                        });
                    }
                }
                else{
                    Log.d("storeList", "Failed to fetch anything");
                }
            }
        });
    }

//    public void launchSUTDCanteenFood5(View view){
//        Toast.makeText(this,"food5 selected",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this,SUTDCanteedFood1.class);
//        startActivity(intent);*/
    //}

}