package Jogodetabuleiro;

public class JogadorNormal extends JogadorGeral{
    public JogadorNormal(String cor){
        super(cor);

    }

    public int[] JogarDados(){
        int dado1 = (int)(Math.random() * 6) +1;
        int dado2 = (int)(Math.random() * 6) +1;
        return new int[]{dado1, dado2};
    }
    
}

