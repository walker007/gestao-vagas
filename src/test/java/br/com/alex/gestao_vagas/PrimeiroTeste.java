package br.com.alex.gestao_vagas;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PrimeiroTeste {
    @Test
    public void deve_ser_possivel_calcular_dois_numeros() {
        var result = calculate(2, 3);
        assertEquals(5, result);

    }

    public void validar_valor_incorreto() {
        var result = calculate(2, 3);
        assertNotEquals(6, result);
    }

    public double calculate(int a, int b) {
        return a + b;
    }
}
