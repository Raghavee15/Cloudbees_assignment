import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void createProduct_Success() throws Exception {
        Product inputProduct = new Product();
        inputProduct.setName("Test Product");
        inputProduct.setDescription("Test Description");
        inputProduct.setPrice(50.0);
        inputProduct.setQuantityAvailable(10);

        when(productService.createProduct(any())).thenReturn(inputProduct);

        ResultActions resultActions = mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputProduct)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).createProduct(any());
    }

    @Test
    void readProduct_Success() throws Exception {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setProductId(productId);
        expectedProduct.setName("Test Product");

        when(productService.readProduct(productId)).thenReturn(expectedProduct);

        ResultActions resultActions = mockMvc.perform(get("/api/products/read/{productId}", productId));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).readProduct(productId);
    }

    @Test
    void updateProduct_Success() throws Exception {
        Product inputProduct = new Product();
        inputProduct.setProductId(1L);
        inputProduct.setName("Updated Product");

        ResultActions resultActions = mockMvc.perform(put("/api/products/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputProduct)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Success"));

        verify(productService, times(1)).updateProduct(any());
    }

    @Test
    void deleteProduct_Success() throws Exception {
        Long productId = 1L;

        ResultActions resultActions = mockMvc.perform(delete("/api/products/delete/{productId}", productId));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Success"));

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void applyDiscountOrTax_Success() throws Exception {
        Long productId = 1L;

        ResultActions resultActions = mockMvc.perform(post("/api/products/applyDiscountOrTax/{productId}/{percentageOrTax}/{isDiscount}", productId, 10.0, true));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Success"));

        verify(productService, times(1)).applyDiscountOrTax(eq(productId), anyDouble(), eq(true));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
