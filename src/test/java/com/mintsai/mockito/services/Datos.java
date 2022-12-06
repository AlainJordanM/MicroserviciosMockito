package com.mintsai.mockito.services;

import com.mintsai.mockito.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public static final List<Examen> EXAMENES = Arrays.asList(
            new Examen(1L, "Matematicas"),
            new Examen(2L, "Español"),
            new Examen(3L, "Historia")
    );
    static public final Examen EXAMEN = new Examen(4L,"Quimica");
    static public final List<String> PREGUNTAS = Arrays.asList(
            "Aritmetica",
            "Integrales",
            "Derivadas"
    );
}
