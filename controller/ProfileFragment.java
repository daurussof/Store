package com.alo.store.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.Order;
import com.alo.store.model.User;
import com.alo.store.view.OrderAdapter;

import java.util.List;

/**
 * Профиль и история заказов.
 */
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView userInfo = view.findViewById(R.id.text_user_info);
        RecyclerView ordersList = view.findViewById(R.id.recycler_orders);

        User user = MockRepository.getInstance().getCurrentUser();
        if (user != null) {
            userInfo.setText(user.getEmail());
        } else {
            userInfo.setText("Гость");
        }

        List<Order> orders = MockRepository.getInstance().getOrders();
        ordersList.setLayoutManager(new LinearLayoutManager(requireContext()));
        ordersList.setAdapter(new OrderAdapter(orders));
    }
}




