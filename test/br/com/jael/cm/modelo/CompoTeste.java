package br.com.jael.cm.modelo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.jael.cm.excecao.ExplosaoException;

public class CompoTeste {

    private Campo campo;

    @BeforeEach
    void iniciar() {
        campo = new Campo(3, 3);
    }

    @Test
    public void testeVizinhoReal() {
        Campo vizinho = new Campo(4, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoNaoReal() {
        Campo vizinho = new Campo(1, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    public void testeValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAlterarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    public void testeAbrirMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoNaoMarcado() {
        campo.minar();
        assertThrows(ExplosaoException.class, campo::abrir);
    }

    @Test
    public void testeAbrirComVizinhos() {
        Campo vizinho = new Campo(2, 2);
        Campo vizinhoDoVizinho = new Campo(1, 1);

        vizinho.adicionarVizinho(vizinhoDoVizinho);

        campo.adicionarVizinho(vizinho);

        campo.abrir();

        assertTrue(vizinho.isAberto() && vizinhoDoVizinho.isAberto());
    }

}
