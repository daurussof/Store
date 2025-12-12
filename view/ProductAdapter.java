package com.alo.store.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.model.Product;

import java.util.List;

/**
 * Адаптер отвечает только за отрисовку карточек (View-слой).
 * Обратные вызовы отдает контроллеру (фрагменту).
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnProductClickListener {
        void onProductClicked(@NonNull Product product);

        void onAddToCartClicked(@NonNull Product product);
    }

    private List<Product> products;
    private final OnProductClickListener listener;

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateProducts(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView price;
        private final ImageView image;
        private final ImageButton btnAdd;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_product_title);
            price = itemView.findViewById(R.id.text_product_price);
            image = itemView.findViewById(R.id.image_product);
            btnAdd = itemView.findViewById(R.id.btn_add_to_cart);
        }

        void bind(Product product) {
            title.setText(product.getName());
            price.setText(String.format("%,.0f ₽", product.getPrice()));

            // В учебном прототипе не грузим реальные картинки. Можно подключить Glide/Picasso позже.
            image.setImageResource(R.drawable.ic_placeholder);

            itemView.setOnClickListener(v -> listener.onProductClicked(product));
            btnAdd.setOnClickListener(v -> listener.onAddToCartClicked(product));
        }
    }
}




