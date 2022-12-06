package com.mintsai.mockito.services;

import com.mintsai.mockito.models.Examen;
import com.mintsai.mockito.repositories.ExamenRepository;
import com.mintsai.mockito.repositories.PreguntaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
    @Mock
    ExamenRepository examenRepository;
    @Mock
    PreguntaRepository preguntaRepository;
    @InjectMocks
    ExamenServiceImpl service;
    @Captor
    ArgumentCaptor<Long> captor;
    @Test
    void findExamenPorNombre() {
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        service.findExamenPorNombreConPreguntas("Historia");
        verify(preguntaRepository).findPreguntasByExamenId(captor.capture());
        assertEquals(3L,captor.getValue());
    }
    @Test
    void testfindExamenPorNombre(){
        //Datos simulados
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        //Llamada al metodo
        Optional<Examen> examen = service.findExamenPorNombre("Historia");
        //Prueba unitaria o la afirmacación de que nuestro examenen se ha obtenido correctamente
        assertTrue(examen.isPresent());
    }
    @Test
    void testPreguntaExamen(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        Examen examen = service.findExamenPorNombreConPreguntas("Historia");

        assertNotNull(examen);

        assertTrue(examen.getPreguntas().contains("Aritmetica"));

        verify(examenRepository,atLeast(2)).findAll();
        verify(preguntaRepository,times(1)).findPreguntasByExamenId(anyLong());
    }
    @Test
    void testException(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasByExamenId(anyLong())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamenPorNombreConPreguntas("Matematicas");
        });

        assertTrue(IllegalArgumentException.class.equals(exception.getClass()));
    }
    @Test
    void testDoThrow(){
        doThrow(IllegalArgumentException.class).when(preguntaRepository).savePreguntas(anyList());
        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);

        assertThrows(IllegalArgumentException.class,() -> service.save(examen));
    }
    @Test
    void testDoAnswer(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        doAnswer(invocationOnMock -> {
            Long id=invocationOnMock.getArgument(0);
            return id==1?Datos.PREGUNTAS: Collections.EMPTY_LIST;
        }).when(preguntaRepository).findPreguntasByExamenId(anyLong());

        Examen examen=service.findExamenPorNombreConPreguntas("Español");

        assertNotNull(examen);
        assertAll(
                ()-> assertNotNull(examen),
                ()-> assertTrue(examen.getPreguntas().isEmpty()),
                ()-> assertEquals(0,examen.getPreguntas().size())

        );
    }
    @Test
    void testExamenSave(){
        when(examenRepository.save(Datos.EXAMEN)).thenReturn(Datos.EXAMEN);
        Examen examen = service.save(Datos.EXAMEN);
        assertNotNull(examen);
        assertEquals(Examen.class, service.save(examen).getClass());
        assertEquals("Quimica", examen.getNombre());
        assertEquals(4L, examen.getId());
    }
    @Test
    void testFindExamenPorNombreConPreguntasNull(){
        when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
        Examen examen = service.findExamenPorNombreConPreguntas("");
        Examen examen1 = service.findExamenPorNombreConPreguntas(null);
        assertNull(examen);
        assertNull(examen1);
    }
}