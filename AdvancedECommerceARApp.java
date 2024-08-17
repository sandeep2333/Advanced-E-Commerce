import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Product {
    int id;
    String name;
    String category;
    List<String> sizeOptions;
    String description;
    int stock;
    List<String> reviews;
    String brand; // Added brand information

    public Product(int id, String name, String category, List<String> sizeOptions, String description, int stock, String brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sizeOptions = sizeOptions;
        this.description = description;
        this.stock = stock;
        this.reviews = new ArrayList<>();
        this.brand = brand;
    }

    public void addReview(String review) {
        reviews.add(review);
    }
}

class UserProfile {
    int height;
    int weight;
    String size;

    public UserProfile(int height, int weight, String size) {
        this.height = height;
        this.weight = weight;
        this.size = size;
    }

    public String suggestSize(List<String> sizeOptions) {
        if (height < 160) return sizeOptions.contains("S") ? "S" : sizeOptions.get(0);
        if (height < 180) return sizeOptions.contains("M") ? "M" : sizeOptions.get(1);
        return sizeOptions.contains("L") ? "L" : sizeOptions.get(sizeOptions.size() - 1);
    }

    public String recommendProduct(List<Product> products) {
        // Simple recommendation based on category
        return products.stream()
                .filter(p -> p.category.equals("Clothing"))
                .findFirst()
                .map(p -> p.name)
                .orElse("No recommendations available.");
    }
}

public class AdvancedECommerceARApp extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<Product> products;
    private UserProfile userProfile;
    private JTextArea outputArea;
    private JTextField searchField;
    private JTextField tryOnField;
    private JTextField heightField;
    private JTextField weightField;
    private JTextField sizeField;
    private JTextField reviewField;
    private JTextField productId1Field;
    private JTextField productId2Field;

    public AdvancedECommerceARApp() {
        products = new ArrayList<>();
        userProfile = new UserProfile(175, 70, "M");

        // Sample products with stock, reviews, and brands
        Product tShirt = new Product(1, "T-Shirt", "Clothing", List.of("S", "M", "L", "XL"), "Comfortable cotton T-shirt", 10, "Nike");
        Product jeans = new Product(2, "Jeans", "Clothing", List.of("M", "L", "XL"), "Denim jeans with a modern fit", 5, "Levi's");
        Product sneakers = new Product(3, "Sneakers", "Footwear", List.of("7", "8", "9", "10"), "Stylish running sneakers", 7, "Adidas");
        Product jacket = new Product(4, "Jacket", "Clothing", List.of("M", "L", "XL", "XXL"), "Warm winter jacket", 8, "North Face");
        Product sunglasses = new Product(5, "Sunglasses", "Accessories", List.of("One Size"), "UV-protected stylish sunglasses", 15, "Ray-Ban");
        Product belt = new Product(6, "Belt", "Accessories", List.of("S", "M", "L"), "Leather belt", 12, "Gucci");
        Product hat = new Product(7, "Hat", "Accessories", List.of("One Size"), "Comfortable baseball hat", 20, "New Era");

        tShirt.addReview("Great fit!");
        tShirt.addReview("Comfortable and stylish.");
        jeans.addReview("Perfect for casual wear.");
        sneakers.addReview("Very comfortable for running.");
        jacket.addReview("Keeps you warm in winter.");
        sunglasses.addReview("Looks great and protects from the sun.");
        belt.addReview("High quality and durable.");
        hat.addReview("Stylish and fits well.");

        products.add(tShirt);
        products.add(jeans);
        products.add(sneakers);
        products.add(jacket);
        products.add(sunglasses);
        products.add(belt);
        products.add(hat);

        // Set up the frame
        setTitle("Advanced E-Commerce AR Simulation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for buttons and input fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        // Search field and button
        panel.add(new JLabel("Search:"));
        searchField = new JTextField();
        panel.add(searchField);
        JButton searchButton = new JButton("Search Products");
        panel.add(searchButton);

        // Filter category field
        panel.add(new JLabel("Filter by Category:"));
        JTextField categoryField = new JTextField();
        panel.add(categoryField);
        JButton filterButton = new JButton("Filter Products");
        panel.add(filterButton);

        // Try on field and button
        panel.add(new JLabel("Try On Product ID:"));
        tryOnField = new JTextField();
        panel.add(tryOnField);
        JButton tryOnButton = new JButton("Try On");
        panel.add(tryOnButton);

        // User profile management fields
        panel.add(new JLabel("Height (cm):"));
        heightField = new JTextField(String.valueOf(userProfile.height));
        panel.add(heightField);
        panel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField(String.valueOf(userProfile.weight));
        panel.add(weightField);
        panel.add(new JLabel("Size:"));
        sizeField = new JTextField(userProfile.size);
        panel.add(sizeField);
        JButton updateProfileButton = new JButton("Update Profile");
        panel.add(updateProfileButton);

        // Review field and button
        panel.add(new JLabel("Add Review (Product ID):"));
        reviewField = new JTextField();
        panel.add(reviewField);
        JButton addReviewButton = new JButton("Add Review");
        panel.add(addReviewButton);

        // Product comparison fields
        panel.add(new JLabel("Compare Product IDs (ID1, ID2):"));
        productId1Field = new JTextField();
        panel.add(productId1Field);
        productId2Field = new JTextField();
        panel.add(productId2Field);
        JButton compareButton = new JButton("Compare Products");
        panel.add(compareButton);

        add(panel, BorderLayout.NORTH);

        // Add action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts(searchField.getText());
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterProducts(categoryField.getText());
            }
        });

        tryOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryOnProduct(Integer.parseInt(tryOnField.getText()));
            }
        });

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });

        addReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReviewToProduct(Integer.parseInt(reviewField.getText()));
            }
        });

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compareProducts(Integer.parseInt(productId1Field.getText()), Integer.parseInt(productId2Field.getText()));
            }
        });
    }

    private void displayProducts(List<Product> productsToDisplay) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available Products:\n");
        for (Product product : productsToDisplay) {
            sb.append(String.format("ID: %d, Name: %s, Category: %s, Sizes: %s, Description: %s, Stock: %d, Brand: %s%n",
                    product.id, product.name, product.category, String.join(", ", product.sizeOptions),
                    product.description, product.stock, product.brand));
            sb.append("Reviews:\n");
            for (String review : product.reviews) {
                sb.append(" - ").append(review).append("\n");
            }
            sb.append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void searchProducts(String query) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.name.toLowerCase().contains(query.toLowerCase()) ||
                product.category.toLowerCase().contains(query.toLowerCase()) ||
                product.brand.toLowerCase().contains(query.toLowerCase())) {
                results.add(product);
            }
        }
        displayProducts(results);
    }

    private void filterProducts(String category) {
        List<Product> results = new ArrayList<>();
        for (Product product : products) {
            if (product.category.toLowerCase().contains(category.toLowerCase())) {
                results.add(product);
            }
        }
        displayProducts(results);
    }

    private void tryOnProduct(int productId) {
        Product selectedProduct = findProductById(productId);
        if (selectedProduct != null) {
            String suggestedSize = userProfile.suggestSize(selectedProduct.sizeOptions);
            if (selectedProduct.sizeOptions.contains(userProfile.size)) {
                outputArea.setText(String.format("User size '%s' fits the product '%s'.%nDescription: %s%nStock: %d%nBrand: %s",
                        userProfile.size, selectedProduct.name, selectedProduct.description, selectedProduct.stock, selectedProduct.brand));
            } else {
                outputArea.setText(String.format("User size '%s' does not fit the product '%s'. Suggested size based on your profile: '%s'.%nDescription: %s%nStock: %d%nBrand: %s",
                        userProfile.size, selectedProduct.name, suggestedSize, selectedProduct.description, selectedProduct.stock, selectedProduct.brand));
            }
        } else {
            outputArea.setText("Product not found.");
        }
    }

    private void updateProfile() {
        try {
            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            String size = sizeField.getText();
            userProfile = new UserProfile(height, weight, size);
            outputArea.setText("Profile updated successfully.");
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input. Please enter valid numbers for height and weight.");
        }
    }

    private void addReviewToProduct(int productId) {
        Product product = findProductById(productId);
        if (product != null) {
            String review = JOptionPane.showInputDialog("Enter review for product ID " + productId);
            if (review != null && !review.trim().isEmpty()) {
                product.addReview(review);
                outputArea.setText("Review added successfully.");
            } else {
                outputArea.setText("Review cannot be empty.");
            }
        } else {
            outputArea.setText("Product not found.");
        }
    }

    private void compareProducts(int id1, int id2) {
        Product product1 = findProductById(id1);
        Product product2 = findProductById(id2);

        if (product1 != null && product2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Comparing Product ID %d and Product ID %d%n", id1, id2));
            sb.append(String.format("Product 1: %s, Category: %s, Stock: %d, Brand: %s%n", product1.name, product1.category, product1.stock, product1.brand));
            sb.append(String.format("Product 2: %s, Category: %s, Stock: %d, Brand: %s%n", product2.name, product2.category, product2.stock, product2.brand));
            outputArea.setText(sb.toString());
        } else {
            outputArea.setText("One or both products not found.");
        }
    }

    private Product findProductById(int id) {
        for (Product product : products) {
            if (product.id == id) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdvancedECommerceARApp app = new AdvancedECommerceARApp();
            app.setVisible(true);
        });
    }
}
