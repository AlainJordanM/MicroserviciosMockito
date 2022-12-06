package com.mintsai.mockito.repositories;

import com.mintsai.mockito.models.Examen;

import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
    Examen save(Examen examen);
}
