package com.timsterj.ronin;

import com.timsterj.data.common.RetrofitClient;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.domain.api.FrontPadApi;

import org.junit.Test;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class Example {


    @Test
    public void Test() {

        Retrofit retrofit = RetrofitClient.getClient(Contracts.RetrofitConstant.FRONT_PAD_BASE_URL);


        FrontPadApi frontPadApi = retrofit.create(FrontPadApi.class);

        RequestBody requestBody;

        HashMap<String, RequestBody> map = new HashMap<>();

        requestBody = RequestBody.create(MediaType.parse("text/plain"), Contracts.RetrofitConstant.FRONT_PAD_SECRET);
        map.put("secret", requestBody);


        frontPadApi.getProducts(map)
                .subscribe(value->{
                    System.out.println(value.getName());
                });

    }


}
