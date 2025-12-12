package com.alo.store.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.model.Category;

import java.util.List;

/**
 * Горизонтальный список категорий.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    public interface OnCategoryClick {
        void onCategorySelected(String categoryId);
    }

    private final List<Category> categories;
    private final OnCategoryClick listener;
    private String selectedId = "";

    public CategoryAdapter(List<Category> categories, OnCategoryClick listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setSelected(String id) {
        this.selectedId = id;
        notifyDataSetChanged();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        CategoryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_category);
        }

        void bind(Category category) {
            title.setText(category.getName());
            boolean isSelected = category.getId().equals(selectedId);
            title.setBackgroundResource(isSelected ? R.drawable.bg_category_selected : R.drawable.bg_category);
            title.setTextColor(itemView.getContext().getColor(isSelected ? android.R.color.white : android.R.color.black));

            itemView.setOnClickListener(v -> {
                String newId = category.getId().equals(selectedId) ? "" : category.getId();
                setSelected(newId);
                listener.onCategorySelected(newId);
            });
        }
    }
}




