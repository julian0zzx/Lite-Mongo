package com.osteching.litemongo;

import com.osteching.litemongo.annotation.Dao;
import com.osteching.litemongo.annotation.Param;
import com.osteching.litemongo.annotation.crud.Delete;
import com.osteching.litemongo.annotation.crud.Insert;
import com.osteching.litemongo.annotation.crud.Query;
import com.osteching.litemongo.annotation.crud.Update;

@Dao(Person.class)
public interface PersonDao {

    @Query
    Person get(@Param("_id") String id);

    @Insert
    void insert(Person p);

    @Update
    void update(Person p);

    @Delete
    void delete(@Param("_id") String id);

    @Query
    Person get(@Param("name") String name, @Param("age") int age);
    
}
