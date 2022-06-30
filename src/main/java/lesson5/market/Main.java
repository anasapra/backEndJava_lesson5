package lesson5.market;

import lesson5.db.dao.ProductsMapper;
import lesson5.db.model.Products;
import lesson5.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;

public class Main {
    private static long id;

    public static void main(String[] args) throws Exception{
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));

        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(116L);


            System.out.println(product);

            ProductsExample example = new ProductsExample();
            example.createCriteria()
                    .andTitleLike("Egg")
                    .andPriceGreaterThan(9);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println(products);

            example.clear();
            example.createCriteria()
                    .andCategoryIdEqualTo(2L);

            products = productsMapper.selectByExample(example);
            System.out.println(products);

            productsMapper.deleteByPrimaryKey(852L);
            example.clear();

            example.createCriteria()
                    .andTitleLike("Egg")
                    .andPriceGreaterThan(9);

            products = productsMapper.selectByExample(example);
            System.out.println(products);

        }
}
    }
       /* MarketService marketService = new MarketService();
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
    */

