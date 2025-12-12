package com.alo.store.controller;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alo.store.R;
import com.alo.store.api.MockRepository;
import com.alo.store.model.User;
import com.alo.store.utils.PrefsHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Главная activity выступает в роли контроллера: держит контейнер фрагментов
 * и управляет переходами между экранами.
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        setupBottomNav();

        // Восстанавливаем пользователя, если он уже логинился
        User saved = PrefsHelper.getUser(this);
        if (saved != null) {
            MockRepository.getInstance().setCurrentUser(saved);
        }

        if (savedInstanceState == null) {
            if (MockRepository.getInstance().getCurrentUser() == null) {
                showBottomNav(false);
                openFragment(new AuthFragment(), false);
            } else {
                openFragment(new CatalogFragment(), false);
            }
        }
    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_catalog) {
                openFragment(new CatalogFragment(), false);
                return true;
            } else if (id == R.id.nav_cart) {
                openFragment(new CartFragment(), false);
                return true;
            } else if (id == R.id.nav_profile) {
                openFragment(new ProfileFragment(), false);
                return true;
            }
            return false;
        });
    }

    /**
     * Универсальный метод навигации между фрагментами.
     *
     * @param fragment       экран, который нужно показать
     * @param addToBackStack нужно ли возвращаться назад кнопкой back
     */
    public void openFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    public void showBottomNav(boolean visible) {
        if (bottomNav != null) {
            bottomNav.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}

