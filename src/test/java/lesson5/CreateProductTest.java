package lesson5;

import lesson4.spoonaccular.AbstractTest;
import lesson5.client.MarketService;
import lesson5.market.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CreateProductTest extends AbstractTest {
    private static MarketService marketService;


    @BeforeAll
    static void beforeAll() {
        marketService = new MarketService();

    }


    @SneakyThrows
    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Product product = new Product();
        product.setTitle("Egg");
        product.setPrice(10);
        product.setCategoryTitle("Food");
        Product createdProduct = marketService.createProduct(product);
        assertJson(marketService.getProduct(116),
                "{\"id\":116,\"title\":\"Vacuum cleaner Bosh JX500\",\"price\":12500,\"categoryTitle\":\"Electronic\"}");
        assertJson(marketService.deleteProduct(900), null );

    }
   //org.junit.jupiter.api.extension.ParameterResolutionException: No ParameterResolver registered for parameter
   // [java.lang.Thread arg0] in method [void lesson5.CreateProductTest.tearDown(java.lang.Thread) throws java.lang.Exception].
   @SneakyThrows
    @AfterEach

    void tearDown(Thread createdProduct) throws Exception {

        long id = createdProduct.getId();
     //  @Nullable ResponseBody response =  marketService.deleteProduct(id);
assertJson(marketService.deleteProduct(id),null);
    }
}

