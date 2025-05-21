package Jogodetabuleiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tabuleiro {

    public static void main(String[] args) {
        List<JogadorGeral> jogadores = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean debugMode = true;

        jogadores.add(new JogadorNormal("Azul"));
        jogadores.add(new JogadorNormal("Verde"));
        jogadores.add(new JogadorNormal("Amarelo"));
        jogadores.add(new JogadorAzarado("Branco"));
        jogadores.add(new JogadorAzarado("Roxo"));
        jogadores.add(new JogadorSortudo("Preto"));

        boolean jogoAtivo = true;

        while (jogoAtivo) {
            for (int i = 0; i < jogadores.size(); i++) {
                JogadorGeral jogador = jogadores.get(i);

                if (jogador.devePularProximaRodada) {
                    System.out.println(jogador.cor + " está pulando esta rodada.");
                    jogador.devePularProximaRodada = false;
                    continue;
                }

                boolean jogadaExtra = true;

                while (jogadaExtra) {
                    jogadaExtra = false;

                    int movimento;

                    if (debugMode) {
                        System.out.print("DEBUG - Digite a casa para onde " + jogador.cor + " deve ir: ");
                        int destino = scanner.nextInt();
                        movimento = destino - jogador.posicao;
                    } else {
                        int[] dados = jogador.JogarDados();
                        movimento = dados[0] + dados[1];
                        System.out.println(
                                jogador.cor + " tirou " + dados[0] + " e " + dados[1] + " (total: " + movimento + ")");

                        if (dados[0] == dados[1]) {
                            System.out.println(jogador.cor + " tirou dois dados IGUAIS e vai jogar novamente!");
                            jogadaExtra = true;
                        }
                    }

                    jogador.posicao += movimento;
                    jogador.jogadas++;
                    System.out.println(jogador.cor + " foi para a casa " + jogador.posicao);

                    // Casas mágicas (troca com o último)
                    if (jogador.posicao == 20 || jogador.posicao == 35) {
                        JogadorGeral maisAtrasado = null;
                        for (JogadorGeral outro : jogadores) {
                            if (outro != jogador) {
                                if (maisAtrasado == null || outro.posicao < maisAtrasado.posicao) {
                                    maisAtrasado = outro;
                                }
                            }
                        }

                        if (maisAtrasado != null && jogador.posicao > maisAtrasado.posicao) {
                            int temp = jogador.posicao;
                            jogador.posicao = maisAtrasado.posicao;
                            maisAtrasado.posicao = temp;
                            System.out.println(jogador.cor + " caiu na casa " + temp
                                    + " (mágica) e TROCOU de lugar com " + maisAtrasado.cor + "!");
                        } else {
                            System.out.println(jogador.cor + " caiu na casa " + jogador.posicao
                                    + " (mágica), mas já era o último. Não troca com ninguém.");
                        }
                    }

                    if (jogador.posicao == 17 || jogador.posicao == 27) {
                        System.out.println(jogador.cor + " caiu na casa " + jogador.posicao
                                + "! Vai escolher um jogador para voltar ao início.");
                        List<JogadorGeral> oponentes = new ArrayList<>(jogadores);
                        oponentes.remove(jogador);
                        int sorteado = (int) (Math.random() * oponentes.size());
                        JogadorGeral escolhido = oponentes.get(sorteado);
                        escolhido.posicao = 0;
                        System.out.println(
                                 jogador.cor + " escolheu " + escolhido.cor + " para voltar ao início!");
                    }

                    if (jogador.posicao == 10 || jogador.posicao == 25 || jogador.posicao == 38) {
                        System.out.println(jogador.cor + " caiu numa casa que perde a próxima rodada!");
                        jogador.devePularProximaRodada = true;
                    }

                    if ((jogador.posicao == 5 || jogador.posicao == 15 || jogador.posicao == 30) &&
                            !(jogador instanceof JogadorAzarado)) {
                        jogador.posicao += 3;
                        System.out.println(jogador.cor + " caiu em uma CASA DA SORTE e andou mais 3 casas!");
                    }

                    if (jogador.posicao == 13) {
                        System.out.println(jogador.cor + " caiu na casa 13! Vai tirar uma carta surpresa...");
                        int tipoSorteado = (int) (Math.random() * 3);
                        JogadorGeral novoJogador;
                        switch (tipoSorteado) {
                            case 0:
                                novoJogador = new JogadorNormal(jogador.cor);
                                System.out.println(jogador.cor + " virou um Jogador Normal!");
                                break;
                            case 1:
                                novoJogador = new JogadorAzarado(jogador.cor);
                                System.out.println(jogador.cor + " virou um Jogador Azarado!");
                                break;
                            default:
                                novoJogador = new JogadorSortudo(jogador.cor);
                                System.out.println(jogador.cor + " virou um Jogador Sortudo!");
                                break;
                        }
                        novoJogador.posicao = jogador.posicao;
                        novoJogador.jogadas = jogador.jogadas;
                        jogadores.set(i, novoJogador);
                        jogador = novoJogador;
                    }

                    if (jogador.posicao >= 40) {
                        System.out.println("VENCEDOR: " + jogador.cor + "!");
                        jogoAtivo = false;
                        break;
                    }
                }
            }

            System.out.println("\n== POSIÇÃO ATUAL DOS JOGADORES ==");
            for (JogadorGeral jogador : jogadores) {
                System.out.println(jogador.cor + " está na casa " + jogador.posicao);
            }
            System.out.println("----------------------------------\n");            

        }

        System.out.println("\n== RESUMO FINAL ==");
        for (JogadorGeral jogador : jogadores) {
            System.out.println(jogador.cor + ": " + jogador.jogadas + " jogadas | casa final: " + jogador.posicao);
        }

        scanner.close();
    }
}
