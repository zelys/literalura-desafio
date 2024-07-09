package com.desafio.literalura.service;

public interface IConvierteDatos {
    <T> T convertirDatos(String json, Class<T> clase);
}