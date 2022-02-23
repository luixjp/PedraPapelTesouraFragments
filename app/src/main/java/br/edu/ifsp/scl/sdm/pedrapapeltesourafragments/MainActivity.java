package br.edu.ifsp.scl.sdm.pedrapapeltesourafragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifsp.scl.sdm.pedrapapeltesourafragments.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding activityMainBinding;

    public boolean isTrio = false;
    public int nrRodadas = 1;
    public int loopRodadas = 0;
    public int qtdPontosJogador;
    public int qtdPontosComputadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("principal")
                .add(R.id.principalFcv, new MainFragment(), "MainFragment")
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miConfiguracoes: {

                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack("configurações")
                        .replace(R.id.principalFcv, new SettingsFragment(), "SettingsFragment")
                        .commit();

                return true;
            }
            default:
                break;
        }
        return false;
    }
}