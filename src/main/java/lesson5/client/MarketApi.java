package lesson5.client;

import lesson5.market.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MarketApi {
    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @POST("products")
    Call<Product> postProduct(@Body Product product);

    @PUT("products")
    Call<Product> putProduct(@Body Product product);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") Long id);
}

