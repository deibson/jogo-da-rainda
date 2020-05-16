import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Operacao {

    private boolean ctrl;
    private Map<Integer, Rainha> posicaoRainha = new HashMap<Integer, Rainha>();

    private Boolean getRainha(int linha, int coluna) {
        Rainha rainha = new Rainha(linha, coluna);
        return posicaoRainha.containsValue(rainha);
    }

    private void put(Integer key, int linha, int coluna) {
        Rainha rainha = new Rainha(linha, coluna);
        posicaoRainha.put(key, rainha);
        if (key > 1) {
            System.out.println("\nRainhas: Você já lançou " + key + " rainhas.");
        } else {
            System.out.println("\nRainha: Você já lançou " + key + " rainha.");
        }
        System.out.println("Rainhas: " + posicaoRainha.toString());
    }

    public void imprimir(char[][] tabuleiro) {
        System.out.println("\n\n\t1 \t2 \t3 \t4 \t5 \t6 \t7 \t8\n");

        for (int linha = 0; linha < 8; linha++) {
            System.out.print(linha + 1);
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print("\t" + tabuleiro[linha][coluna]);
            }
            System.out.println("\n");
        }
    }

    public void montarTabuleiro(char[][] tabuleiro) {
        for (int linha = 0; linha < 8; linha++)
            for (int coluna = 0; coluna < 8; coluna++)
                tabuleiro[linha][coluna] = '-';
    }

    private boolean validarEntrada(char[][] tabuleiro, int linha, int coluna) {

        if (linha < 0 || linha > 8 || coluna < 0 || coluna > 8) {
            System.out.println("Os valores não correspondem ao esperados por favor informe novamente.");
            System.out.println("Valores entre 1 e 8");
            return false;
        } else if (tabuleiro[linha][coluna] == '#') {
            System.out.println("Celula ja informada anteriormente.");
            return false;
        } else return true;
    }

    public int lancarRainha(char[][] tabuleiro, int jogada) {
        ctrl = true;
        int linha, coluna;
        boolean lancamento;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("\nInforme a Linha: ");
            linha = input.nextInt() - 1;

            System.out.print("Informe a Coluna: ");
            coluna = input.nextInt() - 1;

            lancamento = validarEntrada(tabuleiro, linha, coluna);

        } while (!lancamento);

        jogada++;
        tabuleiro[linha][coluna] = '#';
        imprimir(tabuleiro);
        analisarLancamento(linha, coluna);
        put(jogada, linha, coluna);
        if (ctrl) return jogada;
        else return 0;
    }
    private void analisarLancamento(int linha, int coluna) {
        Rainha rainhaLancada = new Rainha(linha, coluna);

        for (Rainha rainha : posicaoRainha.values()) {
            if (rainha.compareTo(rainhaLancada) == 0) {
                printMensagem(rainha);
                break;
            }

            verificarDiagonalDireitaProgessiva(linha, coluna, rainha);
            verificarDiagonalDireitaRegressiva(linha, coluna, rainha);
            verificarDiagonalEsquerdaProgressiva(linha, coluna, rainha);
            verificarDiagonalEsquerdaRegressiva(linha, coluna, rainha);
        }
    }

    private void verificarDiagonalDireitaProgessiva(int linha, int coluna, Rainha rainha){
        while (++linha <= 8) {
            while (++coluna <= 8) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalDireitaRegressiva(int linha, int coluna, Rainha rainha){
        while (--linha >= 0) {
            while (++coluna <= 8) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalEsquerdaProgressiva(int linha, int coluna, Rainha rainha){
        while (++linha <= 8) {
            while (--coluna >= 0) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalEsquerdaRegressiva(int linha, int coluna, Rainha rainha){
        while (--linha >= 0) {
            while (--coluna >= 0) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private boolean validarPosicao(int l, int c, Rainha rainha) {
        if (new Rainha(l, c).equals(rainha)) {
            printMensagem(rainha);
            return false;
        }
        return true;
    }

    private void printMensagem(Rainha rainha) {
        ctrl = false;
        System.out.println("\nVocê comprometeu o jogo,\nExiste uma rainha na posicao [ linha: " + (rainha.getLinha() + 1) + ", coluna: " + (rainha.getColuna() + 1) + " ]");
    }
}
