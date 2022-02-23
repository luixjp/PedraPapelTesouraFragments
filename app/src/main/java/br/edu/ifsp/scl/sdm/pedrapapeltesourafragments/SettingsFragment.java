package br.edu.ifsp.scl.sdm.pedrapapeltesourafragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.edu.ifsp.scl.sdm.pedrapapeltesourafragments.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding fragmentSettingsBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentSettingsBinding = FragmentSettingsBinding.inflate(getLayoutInflater(), container, false);

        fragmentSettingsBinding.trioRb.setChecked(((MainActivity)getActivity()).isTrio);
        switch (((MainActivity)getActivity()).nrRodadas) {
            case 1:
                fragmentSettingsBinding.opcao1rodada.setChecked(true);
                break;
            case 3:
                fragmentSettingsBinding.opcao3rodadas.setChecked(true);
                break;
            case 5:
                fragmentSettingsBinding.opcao5rodadas.setChecked(true);
                break;
            default:
                break;
        }


        fragmentSettingsBinding.salvarBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ((MainActivity)getActivity()).isTrio = fragmentSettingsBinding.trioRb.isChecked();
            int nr_rodadas = 1;
            if(fragmentSettingsBinding.opcao3rodadas.isChecked())
                nr_rodadas = 3;
            if(fragmentSettingsBinding.opcao5rodadas.isChecked())
                nr_rodadas = 5;
            ((MainActivity)getActivity()).nrRodadas = nr_rodadas;

            getActivity().getSupportFragmentManager().popBackStack();
        }
    });

        fragmentSettingsBinding.cancelarBt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    });

        fragmentSettingsBinding.stmMostraOpcoes.setOnCheckedChangeListener((compoundButton, mostrarOpcoes) -> {
        fragmentSettingsBinding.llOpcoes.setVisibility(mostrarOpcoes ? View.VISIBLE : View.GONE);
        fragmentSettingsBinding.llRodadas.setVisibility(mostrarOpcoes ? View.VISIBLE : View.GONE);
    });

        return fragmentSettingsBinding.getRoot();
    }
}