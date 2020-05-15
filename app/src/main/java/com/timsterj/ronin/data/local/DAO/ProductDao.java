package com.timsterj.ronin.data.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.timsterj.ronin.data.model.Product;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ProductDao {

    @Insert
    Completable insertAll(List<Product> products);

    @Query("DELETE FROM Product")
    Completable deleteAll();

    @Update
    Single<Integer> updateAll(List<Product> products);

    @Query("SELECT * FROM Product")
    Observable<List<Product>> getProducts();

}
