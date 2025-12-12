package com.alo.store.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.Product;
import com.alo.store.view.ImagePagerAdapter;

import java.util.List;

/**
 * Детали товара: изображения, описание, выбор размера, добавить в корзину.
 */
public class ProductDetailsFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "arg_product_id";

    public static ProductDetailsFragment newInstance(String productId) {
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String productId = getArguments() != null ? getArguments().getString(ARG_PRODUCT_ID) : null;
        Product product = MockRepository.getInstance().findProductById(productId);
        if (product == null) {
            Toast.makeText(requireContext(), "Товар не найден", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
            return;
        }

        ViewPager2 pager = view.findViewById(R.id.pager_images);
        TextView title = view.findViewById(R.id.text_title);
        TextView price = view.findViewById(R.id.text_price);
        TextView desc = view.findViewById(R.id.text_description);
        RadioGroup sizesGroup = view.findViewById(R.id.group_sizes);
        Button btnAdd = view.findViewById(R.id.btn_add_to_cart_details);

        // Галерея картинок
        pager.setAdapter(new ImagePagerAdapter(product.getImageUrls()));

        title.setText(product.getName());
        price.setText(String.format("%,.0f ₽", product.getPrice()));
        desc.setText(product.getDescription());

        // Динамическое заполнение размеров
        addSizeOptions(sizesGroup, product.getSizes());

        btnAdd.setOnClickListener(v -> {
            int checked = sizesGroup.getCheckedRadioButtonId();
            if (checked == -1) {
                Toast.makeText(requireContext(), "Выберите размер", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rb = view.findViewById(checked);
            String size = rb.getText().toString();
            MockRepository.getInstance().addToCart(product.getId(), size);
            Toast.makeText(requireContext(), "Добавлено в корзину", Toast.LENGTH_SHORT).show();
        });
    }

    private void addSizeOptions(RadioGroup group, List<String> sizes) {
        group.removeAllViews();
        for (String size : sizes) {
            RadioButton rb = new RadioButton(requireContext());
            rb.setText(size);
            group.addView(rb);
        }
        if (group.getChildCount() > 0) {
            ((RadioButton) group.getChildAt(0)).setChecked(true);
        }
    }
}




