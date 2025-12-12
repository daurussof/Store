package com.alo.store.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.model.CartItem;
import com.alo.store.model.Product;

import java.util.List;

/**
 * Адаптер корзины: отображает товары и позволяет менять количество.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    public interface CartListener {
        void onQuantityChanged(@NonNull CartItem item, int newQuantity);

        void onRemove(@NonNull CartItem item);

        Product resolveProduct(@NonNull String productId);
    }

    private List<CartItem> items;
    private final CartListener listener;

    public CartAdapter(List<CartItem> items, CartListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<CartItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView size;
        private final TextView quantity;
        private final TextView price;
        private final ImageButton plus;
        private final ImageButton minus;
        private final ImageButton remove;

        CartHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_cart_title);
            size = itemView.findViewById(R.id.text_cart_size);
            quantity = itemView.findViewById(R.id.text_cart_quantity);
            price = itemView.findViewById(R.id.text_cart_price);
            plus = itemView.findViewById(R.id.btn_plus);
            minus = itemView.findViewById(R.id.btn_minus);
            remove = itemView.findViewById(R.id.btn_remove);
        }

        void bind(CartItem item) {
            Product product = listener.resolveProduct(item.getProductId());
            if (product != null) {
                title.setText(product.getName());
                price.setText(String.format("%,.0f ₽", product.getPrice() * item.getQuantity()));
            } else {
                title.setText("Не найден");
                price.setText("-");
            }
            size.setText("Размер: " + item.getSize());
            quantity.setText(String.valueOf(item.getQuantity()));

            plus.setOnClickListener(v -> listener.onQuantityChanged(item, item.getQuantity() + 1));
            minus.setOnClickListener(v -> listener.onQuantityChanged(item, Math.max(1, item.getQuantity() - 1)));
            remove.setOnClickListener(v -> listener.onRemove(item));
        }
    }
}




