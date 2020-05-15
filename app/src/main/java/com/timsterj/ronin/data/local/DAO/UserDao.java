package com.timsterj.ronin.data.local.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.timsterj.ronin.data.model.User;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert
    Completable insertUser(User user);

    @Query("DELETE FROM User")
    Completable deleteUser();

    @Update
    Single<Integer> updateUser(User user);

    @Query("UPDATE User SET user_name=:name, user_street=:street,  user_home=:home, user_pod=:pod, user_et=:et, user_apart=:apart WHERE user_phone_number=:phoneNumber")
    Single<Integer> updateUserInfo(
            String name,
            String phoneNumber,
            String street,
            String home,
            String pod,
            String et,
            String apart
    );



    @Query("SELECT * FROM User")
    Observable<User> getUser();
}
