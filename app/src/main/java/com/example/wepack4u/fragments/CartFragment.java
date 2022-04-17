package com.example.wepack4u.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.wepack4u.adaptors.CartAdapter;
import com.example.wepack4u.utilities.CartListener;
import com.example.wepack4u.utilities.FoodItem;
import com.example.wepack4u.R;
import com.example.wepack4u.utilities.TotalPrice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class CartFragment extends Fragment implements CartListener {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String auth_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private RecyclerView recyclerView;
    private CartFragment reference = this;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Checkout section
        recyclerView = view.findViewById(R.id.cart_recycler_a);
        foodList();

        TextView emptyCart = getView().findViewById(R.id.empty_cart);
        emptyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(auth_uid).collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            for (QueryDocumentSnapshot document: querySnapshot) {
                                db.collection("users").document(auth_uid).collection("cart").document(document.getId()).delete();
                            }
                        }
                    }
                });
                foodList();
            }
        });

        // Payment section
        RadioGroup payment_method = view.findViewById(R.id.payment_method);
        Button checkout = view.findViewById(R.id.checkout_button);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(auth_uid).collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult().size()>0){
                            int selected = payment_method.getCheckedRadioButtonId();
                            if (selected != -1) {
                                RadioButton method = view.findViewById(selected);
                                Fragment nextFragment = new ConfirmationFragment();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).addToBackStack("CartStack").commit();
                            } else {
                                Toast.makeText(getContext(), R.string.choose_payment, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), R.string.empty_cart, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void foodList() {
        db.collection("users").document(auth_uid).collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<FoodItem> foodItems = querySnapshot.toObjects(FoodItem.class);

                    ArrayList<String> stalls = new ArrayList<>();
                    for (FoodItem each: foodItems){
                        if (!stalls.contains(each.getStall())) { stalls.add(each.getStall()); }
                    }

                    CartAdapter cartAdapter = new CartAdapter(getContext(), foodItems, stalls,
                            true, reference);
                    recyclerView.setAdapter(cartAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
                        @Override
                        public boolean canScrollVertically() { return false; }
                    });

                    TextView total = getView().findViewById(R.id.total);
                    total.setText(new TotalPrice(foodItems).getTotal());
                } else {
                    Log.d("cart_list", "Failed to fetch anything");
                }
            }
        });
    }

    // Update the fragment when item is removed
    @Override
    public void OnRemove() {
        foodList();
    }
}