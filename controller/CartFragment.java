package com.alo.store.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.CartItem;
import com.alo.store.model.Product;
import com.alo.store.view.CartAdapter;

import java.util.List;

/**
 * Экран корзины: список товаров, изменение количества, переход к оформлению.
 */
public class CartFragment extends Fragment implements CartAdapter.CartListener {

    private CartAdapter adapter;
    private TextView totalView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler = view.findViewById(R.id.recycler_cart);
        totalView = view.findViewById(R.id.text_total);
        Button btnCheckout = view.findViewById(R.id.btn_checkout);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CartAdapter(MockRepository.getInstance().getCart(), this);
        recycler.setAdapter(adapter);

        updateTotal();

        btnCheckout.setOnClickListener(v ->
                ((MainActivity) requireActivity()).openFragment(new CheckoutFragment(), true));
    }

    private void updateTotal() {
        double total = MockRepository.getInstance().calculateCartTotal();
        totalView.setText(String.format("Итого: %,.0f ₽", total));
    }

    @Override
    public void onQuantityChanged(@NonNull CartItem item, int newQuantity) {
        MockRepository.getInstance().updateQuantity(item.getProductId(), item.getSize(), newQuantity);
        adapter.updateItems(MockRepository.getInstance().getCart());
        updateTotal();
    }

    @Override
    public void onRemove(@NonNull CartItem item) {
        MockRepository.getInstance().removeFromCart(item.getProductId(), item.getSize());
        adapter.updateItems(MockRepository.getInstance().getCart());
        updateTotal();
    }

    @Override
    public Product resolveProduct(@NonNull String productId) {
        return MockRepository.getInstance().findProductById(productId);
    }
}




