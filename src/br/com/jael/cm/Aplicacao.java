package br.com.jael.cm;

import br.com.jael.cm.modelo.Tabuleiro;
import br.com.jael.cm.visao.TabuleiroConsole;

public class Aplicacao {

    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(8, 8, 8);

        new TabuleiroConsole(tabuleiro);
    }

}
