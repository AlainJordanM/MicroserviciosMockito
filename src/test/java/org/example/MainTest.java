package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void main(){
        String esperado = "Hello world!";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        
        Main.main(null);

        String[] linea = outputStream.toString().split(System.lineSeparator());
        String actual = linea[linea.length-1];

        assertEquals(esperado, actual);

    }

}