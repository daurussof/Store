package com.alo.store.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.Product;
import com.alo.store.view.CategoryAdapter;
import com.alo.store.view.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Экран каталога. Показывает сетку товаров и реагирует на клики.
 * Здесь выступает в роли контроллера: запрашивает модель (мок-данные)
 * и обновляет вью (RecyclerView).
 */
public class CatalogFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private ProductAdapter adapter;
    private final List<Product> allProducts = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private String selectedCategoryId = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_products);
        RecyclerView recyclerCategories = view.findViewById(R.id.recycler_categories);
        SearchView searchView = view.findViewById(R.id.search_products);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new ProductAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        recyclerCategories.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(MockRepository.getInstance().getCategories(), this::onCategorySelected);
        recyclerCategories.setAdapter(categoryAdapter);

        loadProducts();

        // Простая фильтрация по названию
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void onCategorySelected(String categoryId) {
        selectedCategoryId = categoryId;
        filter(null);
    }

    private void loadProducts() {
        allProducts.clear();
        allProducts.addAll(MockRepository.getInstance().getProducts());
        adapter.updateProducts(allProducts);
    }

    private void filter(String query) {
        List<Product> filtered = MockRepository.getInstance().searchProducts(query, selectedCategoryId);
        adapter.updateProducts(filtered);
    }

    @Override
    public void onProductClicked(@NonNull Product product) {
        Fragment details = ProductDetailsFragment.newInstance(product.getId());
        ((MainActivity) requireActivity()).openFragment(details, true);
    }

    @Override
    public void onAddToCartClicked(@NonNull Product product) {
        // Добавляем с первым доступным размером.
        String size = product.getSizes().isEmpty() ? "One size" : product.getSizes().get(0);
        MockRepository.getInstance().addToCart(product.getId(), size);
        Toast.makeText(requireContext(), product.getName() + " добавлен в корзину", Toast.LENGTH_SHORT).show();
    }
}

