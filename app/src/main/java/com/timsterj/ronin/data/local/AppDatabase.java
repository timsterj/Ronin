package com.timsterj.ronin.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.timsterj.ronin.data.local.DAO.FavoriteDao;
import com.timsterj.ronin.data.local.DAO.OrderDao;
import com.timsterj.ronin.data.local.DAO.OrderDoneDao;
import com.timsterj.ronin.data.local.DAO.ProductDao;
import com.timsterj.ronin.data.local.DAO.UserDao;
import com.timsterj.ronin.data.model.Favorite;
import com.timsterj.ronin.data.model.Order;
import com.timsterj.ronin.data.model.OrderDone;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.data.model.User;

@Database(
        entities = {
                Order.class,
                Favorite.class,
                Product.class,
                User.class,
                OrderDone.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract FavoriteDao getFavoriteDao();
    public abstract ProductDao getProductDao();
    public abstract OrderDao getOrderDao();
    public abstract UserDao getUserDao();
    public abstract OrderDoneDao getOrderDoneDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            return Room.databaseBuilder(context, AppDatabase.class, "ronin-database")
                    .build();
        }

        return INSTANCE;
    }


}
