package com.timsterj.ronin.data.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.timsterj.ronin.data.model.Favorite;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface FavoriteDao {

    @Insert
    Completable insertFavorite(Favorite favorite);

    @Update
    Completable updateFavorite(Favorite favorite);

    @Query("SELECT * FROM Favorite")
    Single<List<Favorite>> getFavoriteProducts();

}
