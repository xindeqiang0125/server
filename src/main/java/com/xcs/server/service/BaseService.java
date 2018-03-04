package com.xcs.server.service;

import java.util.List;

public interface BaseService<T> {
    public T findById(Integer id);
    public List<T> findAll();
    public List<T> findAll(List<Integer> ids);
    public void add(T item);
    public void add(List<T> items);
    public void delete(T item);
    public void delete(List<T> items);
    public void update(T oldItem,T newItem);
    public void update(Integer oldItemId,T newItem);
}
