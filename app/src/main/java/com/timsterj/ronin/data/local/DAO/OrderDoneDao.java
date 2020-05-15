package com.timsterj.ronin.data.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.timsterj.ronin.data.model.OrderDone;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface OrderDoneDao {

    @Insert
    Completable insertOrder(OrderDone orderDone);

    @Query("UPDATE OrderDone SET order_status =:status WHERE order_id=:id")
    Completable updateOrderStatus(String status, String id);

    @Query("DELETE FROM `OrderDone`")
    Completable deleteAll();

    @Query("SELECT * FROM `OrderDone`")
    Single<List<OrderDone>> getAllOrders();


}
