package me.lolevsky.nasaplanetary.presentation.mapper;

public interface IModelDataMapper<T, M> {
    M transform(T response);
}
