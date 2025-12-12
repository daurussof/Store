package com.alo.store.api;

import com.alo.store.model.CartItem;
import com.alo.store.model.Category;
import com.alo.store.model.Order;
import com.alo.store.model.Product;
import com.alo.store.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Простое хранилище в памяти.
 * Содержит мок-товары, корзину, историю заказов и текущего пользователя.
 */
public class MockRepository {
    private static MockRepository instance;

    private final List<Category> categories = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<CartItem> cart = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private User currentUser;

    private MockRepository() {
        seedCategories();
        seedProducts();
    }

    public static MockRepository getInstance() {
        if (instance == null) {
            instance = new MockRepository();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public Product findProductById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public List<Product> searchProducts(String query) {
        String normalized = query == null ? "" : query.toLowerCase(Locale.getDefault());
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase(Locale.getDefault()).contains(normalized)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public List<Product> searchProducts(String query, String categoryId) {
        String normalized = query == null ? "" : query.toLowerCase(Locale.getDefault());
        boolean byCategory = categoryId != null && !categoryId.isEmpty();
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            boolean matchName = p.getName().toLowerCase(Locale.getDefault()).contains(normalized);
            boolean matchCat = !byCategory || p.getCategory().equalsIgnoreCase(categoryId);
            if (matchName && matchCat) filtered.add(p);
        }
        return filtered;
    }

    public List<CartItem> getCart() {
        return new ArrayList<>(cart);
    }

    public void addToCart(String productId, String size) {
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId) && item.getSize().equals(size)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cart.add(new CartItem(productId, size, 1));
    }

    public void updateQuantity(String productId, String size, int quantity) {
        for (CartItem item : cart) {
            if (item.getProductId().equals(productId) && item.getSize().equals(size)) {
                item.setQuantity(Math.max(1, quantity));
                return;
            }
        }
    }

    public void removeFromCart(String productId, String size) {
        cart.removeIf(item -> item.getProductId().equals(productId) && item.getSize().equals(size));
    }

    public double calculateCartTotal() {
        double total = 0;
        for (CartItem item : cart) {
            Product product = findProductById(item.getProductId());
            if (product != null) {
                total += product.getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    public Order placeOrder(String customerName, String address, String phone) {
        if (cart.isEmpty()) return null;
        double total = calculateCartTotal();
        List<CartItem> snapshot = new ArrayList<>();
        for (CartItem item : cart) {
            snapshot.add(new CartItem(item.getProductId(), item.getSize(), item.getQuantity()));
        }
        Order order = new Order(
                UUID.randomUUID().toString(),
                snapshot,
                total,
                "Создан",
                customerName,
                address,
                phone
        );
        orders.add(order);
        cart.clear();
        return order;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    private void seedCategories() {
        categories.clear();
        categories.add(new Category("Одежда", "Одежда"));
        categories.add(new Category("Обувь", "Обувь"));
        categories.add(new Category("Аксессуары", "Аксессуары"));
    }

    private void seedProducts() {
        products.clear();
        products.addAll(Arrays.asList(
                new Product("1", "Футболка Alo", "Одежда", "Базовая футболка оверсайз", 2490,
                        Collections.singletonList("https://picsum.photos/400?image=1"), Arrays.asList("S", "M", "L"), "Черный", "Alo"),
                new Product("2", "Худи Alo", "Одежда", "Теплое худи из хлопка", 4290,
                        Collections.singletonList("https://picsum.photos/400?image=2"), Arrays.asList("M", "L", "XL"), "Серый", "Alo"),
                new Product("3", "Штаны Jogger", "Одежда", "Спортивные джоггеры", 3890,
                        Collections.singletonList("https://picsum.photos/400?image=3"), Arrays.asList("S", "M", "L"), "Оливковый", "Alo"),
                new Product("4", "Кроссовки Light", "Обувь", "Легкие беговые кроссовки", 5990,
                        Collections.singletonList("https://picsum.photos/400?image=4"), Arrays.asList("40", "41", "42", "43"), "Белый", "Alo"),
                new Product("5", "Рубашка Linen", "Одежда", "Льняная рубашка на лето", 3190,
                        Collections.singletonList("https://picsum.photos/400?image=5"), Arrays.asList("S", "M", "L"), "Бежевый", "Alo"),
                new Product("6", "Куртка Wind", "Одежда", "Ветровка на молнии", 5490,
                        Collections.singletonList("https://picsum.photos/400?image=6"), Arrays.asList("M", "L", "XL"), "Темно-синий", "Alo"),
                new Product("7", "Спортивный топ", "Одежда", "Дышащий материал", 2790,
                        Collections.singletonList("https://picsum.photos/400?image=7"), Arrays.asList("XS", "S", "M"), "Лавандовый", "Alo"),
                new Product("8", "Леггинсы Flex", "Одежда", "Эластичные леггинсы", 2990,
                        Collections.singletonList("https://picsum.photos/400?image=8"), Arrays.asList("XS", "S", "M", "L"), "Графит", "Alo"),
                new Product("9", "Сумка Tote", "Аксессуары", "Хлопковая сумка-шопер", 1490,
                        Collections.singletonList("https://picsum.photos/400?image=9"), Collections.singletonList("One size"), "Белый", "Alo"),
                new Product("10", "Бейсболка Classic", "Аксессуары", "Регулируемый ремешок", 1290,
                        Collections.singletonList("https://picsum.photos/400?image=10"), Collections.singletonList("One size"), "Черный", "Alo"),
                new Product("11", "Поло Soft", "Одежда", "Поло из хлопка", 2690,
                        Collections.singletonList("https://picsum.photos/400?image=11"), Arrays.asList("S", "M", "L"), "Синий", "Alo"),
                new Product("12", "Пальто Classic", "Одежда", "Демисезонное пальто", 7990,
                        Collections.singletonList("https://picsum.photos/400?image=12"), Arrays.asList("M", "L", "XL"), "Темный графит", "Alo"),
                new Product("13", "Платье Breeze", "Одежда", "Летнее платье миди", 4590,
                        Collections.singletonList("https://picsum.photos/400?image=13"), Arrays.asList("XS", "S", "M"), "Молочный", "Alo"),
                new Product("14", "Рюкзак Urban", "Аксессуары", "Городской рюкзак 18л", 3490,
                        Collections.singletonList("https://picsum.photos/400?image=14"), Collections.singletonList("One size"), "Черный", "Alo"),
                new Product("15", "Носки Sport", "Аксессуары", "Комплект 3 пары", 690,
                        Collections.singletonList("https://picsum.photos/400?image=15"), Collections.singletonList("40-45"), "Белый", "Alo")
        ));
    }
}

