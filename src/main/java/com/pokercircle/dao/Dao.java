package com.pokercircle.dao;
import java.util.List;

// Generic Data Access Object (DAO) interface for CRUD operations
public interface Dao<K, V> {
    V create(V value) throws DaoException;
    V read(K key) throws DaoException;
    List<V> readAll() throws DaoException;
    int update(V value) throws DaoException;
    int delete(K key) throws DaoException;
}
