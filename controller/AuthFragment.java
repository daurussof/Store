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
import com.alo.store.model.User;
import com.alo.store.utils.PrefsHelper;

import java.util.UUID;

/**
 * Экран авторизации/регистрации.
 * Для учебного проекта просто сохраняем пользователя локально.
 */
public class AuthFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText email = view.findViewById(R.id.input_email);
        EditText password = view.findViewById(R.id.input_password);
        Button btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
                Toast.makeText(requireContext(), "Введите email и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            // Пароль не проверяем, так как это учебный прототип.
            User user = new User(UUID.randomUUID().toString(), emailText, emailText);
            PrefsHelper.saveUser(requireContext(), user);
            MockRepository.getInstance().setCurrentUser(user);

            Toast.makeText(requireContext(), "Успешный вход", Toast.LENGTH_SHORT).show();
            ((MainActivity) requireActivity()).showBottomNav(true);
            ((MainActivity) requireActivity()).openFragment(new CatalogFragment(), false);
        });
    }
}




