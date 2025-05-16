package JogadorGeral;

public class JogadorGeral{
    public String cor;
    public int posicao;
    public int jogadas;
    public boolean devePularProximaRodada;

    public JogadorGeral(String cor){
        this.cor = cor;
        this.posicao = 0;
        this.jogadas = 0;
        this.devePularProximaRodada = false;
    }

    public int[] JogarDados(){
        int dado1=(int) (Math.random() * 6) + 1;
        int dado2 = (int) (Math.random() * 6) + 1;
        return new int[]{dado1, dado2};
    }
}
