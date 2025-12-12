package com.alo.store.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.Order;

/**
 * Экран оформления заказа.
 */
public class CheckoutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText name = view.findViewById(R.id.input_name);
        EditText address = view.findViewById(R.id.input_address);
        EditText phone = view.findViewById(R.id.input_phone);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(v -> {
            String nameText = name.getText().toString().trim();
            String addressText = address.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();

            if (TextUtils.isEmpty(nameText) || TextUtils.isEmpty(addressText) || TextUtils.isEmpty(phoneText)) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = MockRepository.getInstance().placeOrder(nameText, addressText, phoneText);
            if (order == null) {
                Toast.makeText(requireContext(), "Корзина пуста", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(requireContext(), "Заказ оформлен", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).openFragment(new ProfileFragment(), false);
        });
    }
}




