package com.timsterj.ronin.domain.api;

import com.timsterj.ronin.data.model.User;
import com.timsterj.ronin.domain.modelDTO.ProductDTO;
import com.timsterj.ronin.data.model.OrderDone;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface FrontPadApi {

    @Multipart
    @POST("index.php?get_products")
    Observable<ProductDTO> getProducts(
            @Part("secret") String secret
    );


    @Multipart
    @POST("index.php?get_client")
    Observable<User> getClient(
            @Part("secret") String secret,
            @Part("client_phone") String clientPhone
    );

    @Multipart
    @POST("index.php?new_order")
    Single<OrderDone> doOrder(
            @Part("secret") String secret,
            @PartMap HashMap<String, RequestBody> productsMass,
            @Part("street") String street,
            @Part("home") String home,
            @Part("pod") String pod,
            @Part("et") String et,
            @Part("apart") String apart,
            @Part("phone") String phone,
            @Part("descr") String descr
            );

    @Multipart
    @POST("index.php?get_status")
    Single<OrderDone> getStatus(
            @Part("secret") String secret,
            @Part("order_id") String orderId,
            @Part("client_phone") String clientPhone
    );
}
