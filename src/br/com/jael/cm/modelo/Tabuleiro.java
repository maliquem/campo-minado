package br.com.jael.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import br.com.jael.cm.excecao.ExplosaoException;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;
    private Random ran = new Random();

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
                    .ifPresent(Campo::abrir);
        } catch (ExplosaoException e) {
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
                .ifPresent(Campo::alternarMarcacao);
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = Campo::isMinado;
        do {
            int aleatorio = ran.nextInt(campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    public void reiniciar() {
        campos.stream().forEach(Campo::reiniciar);
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;
        sb.append("    ");
        for (int coluna = 0; coluna < colunas; coluna++) {
            if ((coluna >= 0) && (coluna < 9)) {
                sb.append("0");
            }
            sb.append((coluna + 1) + "  ");
        }
        sb.append("\n\n");
        for (int linha = 0; linha < linhas; linha++) {
            if ((linha >= 0) && (linha < 9)) {
                sb.append("0");
            }
            sb.append((linha + 1) + "  ");
            for (int coluna = 0; coluna < colunas; coluna++) {
                sb.append(campos.get(i));
                sb.append("   ");
                i++;
            }
            sb.append("\n\n");
        }

        return sb.toString();
    }

}
