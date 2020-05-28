import java.util.*;

public class main {

    public static void main(String[] args) {
        int jogada = 0;
        boolean ctrl = true;
        char[][] tabuleiro = new char[8][8];
        Operacao operacaoRainha = new Operacao();

        operacaoRainha.montarTabuleiro(tabuleiro);
        while (ctrl) {
            jogada = operacaoRainha.lancarRainha(tabuleiro, jogada);
            if (jogada == 8) {
                System.out.println("\n\t*********PARABENS VOCÊ GANHOU*********\n\t\t*******JOGO ENCERRADO********");
                ctrl = false;
            } else if (jogada == 0) {
                System.out.println("\n\t*********QUE PENA VOCÊ PERDEU*********\n\t\t*******JOGO ENCERRADO********");
                ctrl = false;
            }
        }
    }
}

