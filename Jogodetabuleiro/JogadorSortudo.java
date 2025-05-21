package Jogodetabuleiro;

public class JogadorSortudo extends JogadorGeral{
    public JogadorSortudo(String cor){
        super(cor);
    }

public int[] JogarDados() {
    int dado1 = (int)(Math.random() * 3) + 4;
    int dado2 = (int)(Math.random() * 3) + 4;
    return new int[]{dado1, dado2};
}

}
