package com.alo.store.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;

import java.util.List;

/**
 * Пейджер картинок. В прототипе показывает плейсхолдер вместо загрузки по URL.
 */
public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ImageHolder> {

    private final List<String> urls;

    public ImagePagerAdapter(List<String> urls) {
        this.urls = urls;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_pager, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.image.setImageResource(R.drawable.ic_placeholder);
        // Для реального проекта сюда добавить загрузку по URL (Glide/Picasso).
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    static class ImageHolder extends RecyclerView.ViewHolder {
        final ImageView image;

        ImageHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_pager);
        }
    }
}




