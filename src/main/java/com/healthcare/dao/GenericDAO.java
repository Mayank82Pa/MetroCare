package com.healthcare.dao;

import java.util.List;

/**
 * Generic DAO Interface demonstrating:
 * 1. Interfaces (OOP)
 * 2. Generics (<T, ID>) for type-safe database operations.
 */
public interface GenericDAO<T, ID> {
    T findById(ID id);
    List<T> findAll();
    boolean delete(ID id);
}
