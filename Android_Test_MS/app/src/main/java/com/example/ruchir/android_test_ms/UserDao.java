package com.example.ruchir.android_test_ms;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("Select * from user where username like :musername and password like :mpassword")
    List<User> registered(String musername, String mpassword);

    @Query("Select * from user where username like :musername")
    List<User> usernameexist(String musername);

}
