package com.example.wepack4u.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wepack4u.R;
import com.example.wepack4u.utilities.FoodItem;

import java.util.List;

public class Compost extends RecyclerView.Adapter<Compost.ViewHolder> {
    private final Context context;
    private final List<FoodItem> cart;
    private final boolean isPayment;

    public Compost(Context context, List<FoodItem> cart, boolean isPayment) {
        this.context = context;
        this.cart = cart;
        this.isPayment = isPayment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (isPayment) { view = inflater.inflate(R.layout.cart_display_row, parent, false); }
        else { view = inflater.inflate(R.layout.receipt_row, parent, false); }
        return new Compost.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem food = cart.get(position);
        holder.name.setText(food.getName());
        holder.unit.setText(food.getUnit());
        holder.price.setText(food.getPrice());

        if (isPayment) {
            holder.delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cart.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView unit;
        TextView price;
        TextView delete;

        public ViewHolder(@NonNull View view) {
            super(view);

            if (isPayment) {
                name = view.findViewById(R.id.name_a);
                unit = view.findViewById(R.id.unit_a);
                price = view.findViewById(R.id.price_a);
                delete = view.findViewById(R.id.remove);
            }
            else {
                name = view.findViewById(R.id.name_b);
                unit = view.findViewById(R.id.unit_b);
                price = view.findViewById(R.id.price_b);
            }
        }
    }
}
