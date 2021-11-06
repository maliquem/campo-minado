package br.com.jael.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.jael.cm.excecao.ExplosaoException;
import br.com.jael.cm.excecao.SairException;
import br.com.jael.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while (continuar) {
                cicloDoJogo();

                System.out.println("Outra partida? (S/n): ");
                String resposta = entrada.nextLine();

                if ("n".equalsIgnoreCase(resposta)) {
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }

        } catch (SairException e) {
            System.out.println("FIM DE JOGO!");
        } finally {
            entrada.close();
        }

    }

    private void cicloDoJogo() {
        try {

            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro);

                String digitado = capturarValorDigitado("DIGITE (X, Y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
                        .iterator();

                digitado = capturarValorDigitado("1 PARA ABRIR  |  2 PARA (DES)MARCAR: ");

                if ("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next() - 1, xy.next() - 1);
                } else {
                    tabuleiro.alternarMarcacao(xy.next() - 1, xy.next() - 1);
                }

                System.out.flush();
            }
            System.out.println(tabuleiro);
            System.out.println("VOCÊ GANHOU!");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("VOCÊ PERDEU!");
        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if ("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }

        return digitado;
    }
}
