package dev.zambone.screenmatch.service;

public interface DataConverter {
    <T> T getData(String json, Class<T> clazz);
}
