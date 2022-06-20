package lesson5.market;

import lesson5.client.MarketService;

public class Main {
    private static long id;

    public static void main(String[] args) throws Exception{
        MarketService marketService = new MarketService();
        System.out.println(marketService.getProducts());
        System.out.println(marketService.getProduct(120));

        Product product = new Product();
        product.setTitle("Egg");
        product.setPrice(10);
        product.setCategoryTitle("Food");

        Product createdProduct = marketService.createProduct(product);
        System.out.println(createdProduct);

        marketService.putProduct(product);
       createdProduct.setPrice(11);
        System.out.println(createdProduct);
        id = createdProduct.getId();
        marketService.deleteProduct(id);

    }

}
