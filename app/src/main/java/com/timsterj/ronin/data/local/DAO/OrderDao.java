package com.timsterj.ronin.data.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.timsterj.ronin.data.model.Order;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrder(Order order);

    @Update
    Completable updateOrder(Order order);

    @Query("DELETE FROM `Order`")
    Completable deleteAll();

    @Query("SELECT * FROM `Order`")
    Single<List<Order>> getAllOrders();




}
