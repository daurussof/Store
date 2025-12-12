package com.alo.store.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.CartItem;
import com.alo.store.model.Order;
import com.alo.store.model.Product;

import java.util.List;

/**
 * Адаптер истории заказов.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    private final List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        holder.bind(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView items;
        private final TextView total;
        private final TextView status;

        OrderHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_order_id);
            items = itemView.findViewById(R.id.text_order_items);
            total = itemView.findViewById(R.id.text_order_total);
            status = itemView.findViewById(R.id.text_order_status);
        }

        void bind(Order order) {
            title.setText("Заказ " + order.getId().substring(0, 6));
            total.setText(String.format("%,.0f ₽", order.getTotal()));
            status.setText(order.getStatus());
            items.setText(buildItems(order.getItems()));
        }

        private String buildItems(List<CartItem> cartItems) {
            StringBuilder sb = new StringBuilder();
            for (CartItem item : cartItems) {
                Product product = MockRepository.getInstance().findProductById(item.getProductId());
                String name = product != null ? product.getName() : "Товар";
                sb.append(name)
                        .append(" x")
                        .append(item.getQuantity())
                        .append(" (")
                        .append(item.getSize())
                        .append(")")
                        .append("\n");
            }
            return sb.toString().trim();
        }
    }
}




