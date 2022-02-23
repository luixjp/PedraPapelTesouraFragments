package br.edu.ifsp.scl.sdm.pedrapapeltesourafragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import br.edu.ifsp.scl.sdm.pedrapapeltesourafragments.databinding.FragmentMainBinding;

public class MainFragment extends Fragment implements View.OnClickListener {

    private FragmentMainBinding fragmentMainBinding;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         fragmentMainBinding = FragmentMainBinding.inflate(getLayoutInflater(), container, false);

         fragmentMainBinding.btnPedra.setOnClickListener(this);
         fragmentMainBinding.btnPapel.setOnClickListener(this);
         fragmentMainBinding.btnTesoura.setOnClickListener(this);

         configureGame();

         return fragmentMainBinding.getRoot();
     }

    private void configureGame() {

         //clean up
        ((MainActivity)getActivity()).loopRodadas = ((MainActivity)getActivity()).nrRodadas-1;
        ((MainActivity)getActivity()).qtdPontosJogador = 0;
        ((MainActivity)getActivity()).qtdPontosComputadores = 0;
        fragmentMainBinding.ivJogadaComputador1.setImageResource(android.R.color.transparent);
        fragmentMainBinding.ivJogadaComputador2.setImageResource(android.R.color.transparent);
        fragmentMainBinding.tvResultado.setText("");
        fragmentMainBinding.tvQuantidadeRodadas.setText(getString(R.string.qtdRodadas) + " " + ((MainActivity)getActivity()).nrRodadas);

        //set visibility
        fragmentMainBinding.tvJogadaComputador2.setVisibility(((MainActivity)getActivity()).isTrio ? View.VISIBLE : View.GONE);
        fragmentMainBinding.ivJogadaComputador2.setVisibility(((MainActivity)getActivity()).isTrio ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        String jogada = "Nulo";

        switch (view.getId()) {
            case R.id.btnPedra:
                jogada = "Pedra";
                break;
            case R.id.btnPapel:
                jogada = "Papel";
                break;
            case R.id.btnTesoura:
                jogada = "Tesoura";
                break;
            default:
                break;
        }
        jogarJoKenPo(jogada);
    }

    private void jogarJoKenPo(String jogada) {

        StringBuilder resultadoSb = new StringBuilder();
        resultadoSb.append("Sua jogada: ");
        resultadoSb.append(jogada);
        resultadoSb.append(", Jogada do computador 1: ");

        Random rnd = new Random(System.currentTimeMillis());
        int jogadaComputador1 = rnd.nextInt(3);
        int jogadaComputador2 = rnd.nextInt(3);

        //Setando imagem da jogada do computador 1
        int imagemJogadaComputadorId = -1;
        switch (jogadaComputador1) {
            case 0: {
                imagemJogadaComputadorId = R.mipmap.pedra;
                resultadoSb.append("Pedra");
                break;
            }
            case 1: {
                imagemJogadaComputadorId = R.mipmap.papel;
                resultadoSb.append("Papel");
                break;
            }
            case 2: {
                imagemJogadaComputadorId = R.mipmap.tesoura;
                resultadoSb.append("Tesoura");
                break;
            }
            default:
                break;
        }

        fragmentMainBinding.ivJogadaComputador1.setImageResource(imagemJogadaComputadorId);

        if (((MainActivity)getActivity()).isTrio) {

            resultadoSb.append(" , Jogada do computador 2: ");

            //Setando imagem da jogada do computador 2
            int imagemJogadaComputador2Id = -1;
            switch (jogadaComputador2) {
                case 0: {
                    imagemJogadaComputador2Id = R.mipmap.pedra;
                    resultadoSb.append("Pedra");
                    break;
                }
                case 1: {
                    imagemJogadaComputador2Id = R.mipmap.papel;
                    resultadoSb.append("Papel");
                    break;
                }
                case 2: {
                    imagemJogadaComputador2Id = R.mipmap.tesoura;
                    resultadoSb.append("Tesoura");
                    break;
                }
                default: {
                    break;
                }
            }
            fragmentMainBinding.ivJogadaComputador2.setImageResource(imagemJogadaComputador2Id);
        }


        analisarResultado(resultadoSb, jogada, jogadaComputador1, jogadaComputador2);
    }

    private void analisarResultado(StringBuilder resultadoSb, String jogada, int jogadaComputador1, int jogadaComputador2) {


        String status1 = verificaStatus(jogada, jogadaComputador1);

        if(!((MainActivity)getActivity()).isTrio) {
            resultadoSb.append(". Você " + status1);

            switch (status1) {
                case "Ganhou!":
                    ((MainActivity)getActivity()).qtdPontosJogador++;
                    break;
                case "Perdeu!":
                    ((MainActivity)getActivity()).qtdPontosComputadores++;
                    break;
                default:
                    break;
            }
        }
        else {
            resultadoSb.append(". Contra o Computador 1, Você " + status1);

            String status2 = verificaStatus(jogada, jogadaComputador2);

            resultadoSb.append(" Contra o Computador 2, Você " + status2);

            if(status1.equals("Perdeu!") && status2.equals("Perdeu!")) {
                resultadoSb.append(" Resultado Final: Você Perdeu para os Computadores!");
                ((MainActivity)getActivity()).qtdPontosComputadores++;
            }
            else if(status1.equals("Empatou!") && status2.equals("Empatou!")) {
                resultadoSb.append(" Resultado Final: Você Empatou com os Computadores!");
            }
            else if(status1.equals("Ganhou!") && status2.equals("Ganhou!")) {
                resultadoSb.append(" Resultado Final: Parabéns Você Ganhou dos Computadores!");
                ((MainActivity)getActivity()).qtdPontosJogador++;
            }
            else if(status1.equals("Ganhou!") && status2.equals("Empatou!") ||
                    status2.equals("Ganhou!") && status1.equals("Empatou!")) {
                resultadoSb.append(" Resultado Final: Parabéns Você Ganhou dos Computadores!");
                ((MainActivity)getActivity()).qtdPontosJogador++;
            }
            else if(status1.equals("Ganhou!") && status2.equals("Perdeu!") ||
                    status2.equals("Ganhou!") && status1.equals("Perdeu!")) {
                resultadoSb.append(" Resultado Final: Você Empatou com dos Computadores!");
            }
            else if(status1.equals("Empatou!") && status2.equals("Perdeu!") ||
                    status2.equals("Empatou!") && status1.equals("Perdeu!")) {
                resultadoSb.append(" Resultado Final: Você Perdeu dos Computadores!");
                ((MainActivity)getActivity()).qtdPontosComputadores++;
            }
            else {
                resultadoSb.append(" Resultado Impossível!");
            }
        }

        fragmentMainBinding.tvResultado.setText(resultadoSb.toString());

        verificarRodadas();
    }

    private String verificaStatus(String jogada, int jogadaComputador) {

        String status = "";

        if (jogada.equals("Pedra")) {

            switch (jogadaComputador) {
                case 0:
                    //Pedra X Pedra
                    status = "Empatou!";
                    break;
                case 1:
                    //Pedra x Papel
                    status = "Perdeu!";
                    break;
                case 2:
                    //Pedra x Tesoura
                    status = "Ganhou!";
                    break;
                default:
                    break;
            }
        }

        if (jogada.equals("Papel")) {

            switch (jogadaComputador) {
                case 0:
                    //Papel X Pedra
                    status = "Ganhou!";
                    break;
                case 1:
                    //Papel x Papel
                    status = "Empatou!";
                    break;
                case 2:
                    //Papel x Tesoura
                    status = "Perdeu!";
                    break;
                default:
                    break;
            }
        }

        if (jogada.equals("Tesoura")) {

            switch (jogadaComputador) {
                case 0:
                    //Tesoura X Pedra
                    status = "Perdeu!";
                    break;
                case 1:
                    //Tesoura x Papel
                    status = "Ganhou!";
                    break;
                case 2:
                    //Tesoura x Tesoura
                    status = "Empatou!";
                    break;
                default:
                    break;
            }
        }

        return status;
    }

    private void verificarRodadas() {
        switch (((MainActivity)getActivity()).nrRodadas) {
            case 1:
                break;
            case 3:
            case 5: {
                StringBuilder resultadoRodada = new StringBuilder();
                resultadoRodada.append(fragmentMainBinding.tvResultado.getText());
                resultadoRodada.append("\n");
                if (((MainActivity)getActivity()).loopRodadas > 0) {
                    resultadoRodada.append("===== FIM RODADA NR: ");
                    resultadoRodada.append(((MainActivity)getActivity()).nrRodadas-((MainActivity)getActivity()).loopRodadas);
                    resultadoRodada.append("=====\n");
                    fragmentMainBinding.tvResultado.setText(resultadoRodada.toString());
                    ((MainActivity)getActivity()).loopRodadas--;
                }
                else {

                    resultadoRodada.append("===== FIM DE JOGO =====\n");
                    resultadoRodada.append("Seus pontos foram: " + ((MainActivity)getActivity()).qtdPontosJogador);
                    resultadoRodada.append("\nPontos do(s) Computador(es): " + ((MainActivity)getActivity()).qtdPontosComputadores);
                    resultadoRodada.append("\nJogadas em Empate: " + (((MainActivity)getActivity()).nrRodadas-(((MainActivity)getActivity()).qtdPontosComputadores+((MainActivity)getActivity()).qtdPontosJogador)));

                    if(((MainActivity)getActivity()).qtdPontosJogador > ((MainActivity)getActivity()).qtdPontosComputadores) {
                        resultadoRodada.append("\nParabéns! Na disputa por rodadas vocês ganhou!!!");
                    }
                    else if (((MainActivity)getActivity()).qtdPontosJogador < ((MainActivity)getActivity()).qtdPontosComputadores) {
                        resultadoRodada.append("\nQue pena! Na disputa por rodadas vocês perdeu!!!");
                    }
                    else {
                        resultadoRodada.append("\nNa disputa por rodadas deu empate!!! Tente novamente!");
                    }
                    fragmentMainBinding.tvResultado.setText(resultadoRodada.toString());

                    //Fim de jogo, reiniciar jogo
                    ((MainActivity)getActivity()).loopRodadas = ((MainActivity)getActivity()).nrRodadas-1;
                    ((MainActivity)getActivity()).qtdPontosJogador = 0;
                    ((MainActivity)getActivity()).qtdPontosComputadores = 0;
                }
                break;
            }
            default:
                break;

        }
    }

}