package com.wpx.dao;

import com.wpx.domain.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll() throws Exception;

    void add(Category c) throws Exception;

    Category getById(String cid) throws Exception;

    void update(Category category) throws Exception;

    void delete(String cid) throws Exception;
}
