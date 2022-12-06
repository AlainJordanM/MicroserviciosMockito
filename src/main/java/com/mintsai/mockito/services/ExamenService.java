package com.mintsai.mockito.services;

import com.mintsai.mockito.models.Examen;

import java.util.Optional;

public interface ExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
    Examen findExamenPorNombreConPreguntas(String nombre);
    Examen save(Examen examen);
}
