import java.util.Objects;

public class Rainha implements Comparable<Rainha> {
    private int linha, coluna;

    public Rainha(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    @Override
    public int compareTo(Rainha obj) {
        if (linha == obj.linha)
            return 0;
        else if (coluna == obj.coluna)
            return 0;

        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rainha)) return false;
        Rainha rainha = (Rainha) o;
        return linha == rainha.linha &&
                coluna == rainha.coluna;
    }

    @Override
    public String toString() {
        return "[ linha: " + (linha + 1) + ", coluna: " + (coluna + 1) + " ]";
    }
}
