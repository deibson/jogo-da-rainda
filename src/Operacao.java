import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Operacao {

    private boolean ctrl;
    private int countAjuda = 0;
    char[][] tabuleiroAjuda = new char[8][8];
    private Map<Integer, Rainha> posicaoRainha = new HashMap<Integer, Rainha>();
    private Map<Integer, Rainha> ajudaRainha = new HashMap<Integer, Rainha>();

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

        if (linha < 0 || linha >= 8 || coluna < 0 || coluna >= 8) {
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
            if (jogada > 0 && countAjuda < 2)
                System.out.print("\nApós a Segunda jogada você poderá imprimir o tabuleiro de ajuda 2 vezes,\n" +
                        "basta informar o numero 9 na linha e/ou coluna.\n");

            System.out.print("\nInforme a Linha: ");
            linha = input.nextInt() - 1;

            System.out.print("Informe a Coluna: ");
            coluna = input.nextInt() - 1;

            if (jogada > 1 && countAjuda < 2 && linha == 8 && coluna == 8) {
                countAjuda++;
                if (countAjuda == 1) montarTabuleiro(tabuleiroAjuda);
                lancamento = printAjuda(linha, coluna);
            } else if (countAjuda == 2 && linha == 8 && coluna == 8) {
                System.out.print("Desculpe não posse te ajudar, Você já usou todas as suas ajudas. Boa Sorte!!!\n");
                lancamento = false;
            } else lancamento = validarEntrada(tabuleiro, linha, coluna);

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
            } else if (linha == 8 && coluna == 8) {

                tabuleiroAjuda[rainha.getLinha()][rainha.getColuna()] = '#';

                for (int c = 0; c < 8; c++)
                    if (rainha.getColuna() != c)
                        tabuleiroAjuda[rainha.getLinha()][c] = '0';

                for (int l = 0; l < 8; l++)
                    if (rainha.getLinha() != l)
                        tabuleiroAjuda[l][rainha.getColuna()] = '0';
            }

            verificarDiagonalDireitaProgessiva(linha, coluna, rainha);
            verificarDiagonalDireitaRegressiva(linha, coluna, rainha);
            verificarDiagonalEsquerdaProgressiva(linha, coluna, rainha);
            verificarDiagonalEsquerdaRegressiva(linha, coluna, rainha);
        }
    }

    private void verificarDiagonalDireitaProgessiva(int linha, int coluna, Rainha rainha) {
        if (linha == 8 && coluna == 8) {
            linha = rainha.getLinha();
            coluna = rainha.getColuna();
        }
        while (++linha < 8) {
            while (++coluna < 8) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalDireitaRegressiva(int linha, int coluna, Rainha rainha) {
        if (linha == 8 && coluna == 8) {
            linha = rainha.getLinha();
            coluna = rainha.getColuna();
        }
        while (--linha >= 0) {
            while (++coluna <= 7) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalEsquerdaProgressiva(int linha, int coluna, Rainha rainha) {
        if (linha == 8 && coluna == 8) {
            linha = rainha.getLinha();
            coluna = rainha.getColuna();
        }
        while (++linha <= 7) {
            while (--coluna >= 0) {
                if (!validarPosicao(linha, coluna, rainha)) linha = 0;
                break;
            }
        }
    }

    private void verificarDiagonalEsquerdaRegressiva(int linha, int coluna, Rainha rainha) {
        if (linha == 8 && coluna == 8) {
            linha = rainha.getLinha();
            coluna = rainha.getColuna();
        }
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
        if (countAjuda != 0) tabuleiroAjuda[l][c] = '0';
        return true;
    }

    private void printMensagem(Rainha rainha) {
        ctrl = false;
        System.out.println("\nVocê comprometeu o jogo,\nExiste uma rainha na posicao [ linha: " + (rainha.getLinha() + 1) + ", coluna: " + (rainha.getColuna() + 1) + " ]");
    }

    private boolean printAjuda(int linha, int coluna) {
        analisarLancamento(linha, coluna);
        System.out.println("Nas posições onde tiver (0) são posições de conflito.");
        imprimir(tabuleiroAjuda);
        int key = 1;
        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                if (tabuleiroAjuda[l][c] == '-') {
                    ajudaRainha.put(key, new Rainha(l, c));
                    key++;
                }
            }
        }
        System.out.println("Posições Livres: " + ajudaRainha.toString());
        return false;
    }
}
