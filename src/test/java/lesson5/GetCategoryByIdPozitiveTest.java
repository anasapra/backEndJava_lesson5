package lesson5;

import lesson4.spoonaccular.AbstractTest;
import lesson5.client.MarketService;
import lesson5.market.Product;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GetCategoryByIdPozitiveTest extends AbstractTest {
    private static MarketService marketService;

    @BeforeAll
    static void beforeAll() {
        marketService = new MarketService();

    }


    @SneakyThrows
    @Test
    void getCategoryByIdPozitiveTest() throws Exception{

        List<Product> productList = marketService.getProducts();
        System.out.println(productList);
        System.out.println(marketService.getProduct(116));
        assertJson(getResource("products.json"), productList);
        assertJson(marketService.getProduct(116), "Vacuum cleaner Bosh JX500");
    }
}
