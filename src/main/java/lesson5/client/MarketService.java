package lesson5.client;

import lesson5.market.Product;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

import static lesson5.client.RetrofitCallExecutor.executeCall;

public class MarketService {

        private final MarketApi api;
    public MarketService() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(System.out::println);
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            api = new Retrofit.Builder()
                    .baseUrl("https://minimarket1.herokuapp.com/market/api/v1/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MarketApi.class);
        }

public List<Product> getProducts() {
        return executeCall(api.getProducts());
}

        public Product getProduct(long id) {
            return executeCall(api.getProduct(id));
        }

        public Product createProduct(Product product) {
            return executeCall(api.postProduct(product));
        }

    public Product putProduct(Product product) {

        return executeCall(api.putProduct(product));
    }
    public @Nullable ResponseBody deleteProduct(long id) {
        return executeCall(api.deleteProduct(id));
    }
}
