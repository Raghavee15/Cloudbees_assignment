import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    public Product readProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }

    public String updateProduct(Product product) {
        try {
            productRepository.save(product);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Error updating product: " + e.getMessage());
        }
    }

    public String deleteProduct(Long productId) {
        try {
            productRepository.deleteById(productId);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product: " + e.getMessage());
        }
    }

    public String applyDiscountOrTax(Long productId, double percentageOrTax, boolean isDiscount) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            double currentPrice = product.getPrice();
            double modifiedPrice;

            if (isDiscount) {
                modifiedPrice = currentPrice - (currentPrice * percentageOrTax / 100);
            } else {
                modifiedPrice = currentPrice + (currentPrice * percentageOrTax / 100);
            }

            product.setPrice(modifiedPrice);
            productRepository.save(product);
            return "Success";
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }
}
