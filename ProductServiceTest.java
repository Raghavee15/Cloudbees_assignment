import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_Success() {
        Product inputProduct = new Product();
        inputProduct.setName("Test Product");
        inputProduct.setDescription("Test Description");
        inputProduct.setPrice(50.0);
        inputProduct.setQuantityAvailable(10);

        when(productRepository.save(any())).thenReturn(inputProduct);

        Product createdProduct = productService.createProduct(inputProduct);

        assertNotNull(createdProduct);
        assertEquals(inputProduct.getName(), createdProduct.getName());
        assertEquals(inputProduct.getDescription(), createdProduct.getDescription());
        assertEquals(inputProduct.getPrice(), createdProduct.getPrice());
        assertEquals(inputProduct.getQuantityAvailable(), createdProduct.getQuantityAvailable());

        verify(productRepository, times(1)).save(any());
    }

    @Test
    void readProduct_Success() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setProductId(productId);
        expectedProduct.setName("Test Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        Product retrievedProduct = productService.readProduct(productId);

        assertNotNull(retrievedProduct);
        assertEquals(expectedProduct, retrievedProduct);

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void updateProduct_Success() {
        Product inputProduct = new Product();
        inputProduct.setProductId(1L);
        inputProduct.setName("Updated Product");

        productService.updateProduct(inputProduct);

        verify(productRepository, times(1)).save(inputProduct);
    }

    @Test
    void deleteProduct_Success() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void applyDiscountOrTax_Success() {
        Long productId = 1L;
        Product originalProduct = new Product();
        originalProduct.setProductId(productId);
        originalProduct.setPrice(100.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(originalProduct));

        String result = productService.applyDiscountOrTax(productId, 10.0, true);

        assertEquals("Success", result);
        assertEquals(90.0, originalProduct.getPrice());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(originalProduct);
    }
}
