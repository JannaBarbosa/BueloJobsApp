package com.buelojobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import eventos.Eventos;
import formacao.Formacao;
import vagas.Vagas;

public class HomeActivity extends AppCompatActivity {

    private MaterialSearchView searchView;//busca
    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Buelojobs");
        setSupportActionBar(toolbar);

        //Configurar abas
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Vagas", Vagas.class)
                        .add("Formação", Formacao.class)
                        .add("Eventos", Eventos.class)
                        .create()
        );

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        //Configuração do search view
        searchView = findViewById(R.id.materialSearchPrincipal);

        //Listener para o search view
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                switch (viewPager.getCurrentItem()) {
                    case 0:

                        Vagas fragment = (Vagas) adapter.getPage(0);
                        fragment.recarregarVagas();
                        break;

                    case 1:

                        Formacao fragmentF = (Formacao) adapter.getPage(1);
                        fragmentF.recarregarFormacao();
                        break;
                    case 2:

                        Eventos eventosFragment = (Eventos) adapter.getPage(2);
                        eventosFragment.recarregarEventos();
                        break;
                }

            }
        });

        //Listener para a caixa de texto
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                switch (viewPager.getCurrentItem()) {

                    case 0:

                        Vagas fragment = (Vagas) adapter.getPage(0);

                        if (newText != null && !newText.isEmpty()) {
                            fragment.pesquisarVagas(newText.toLowerCase());

                        } else {
                            fragment.recarregarVagas();
                        }

                        break;

                    case 1:


                        Formacao fragmentF = (Formacao) adapter.getPage(1);
                        if (newText != null && !newText.isEmpty()) {
                            fragmentF.pesquisarFormacao(newText.toLowerCase());
                        } else {

                            fragmentF.recarregarFormacao();
                        }


                        break;
                    case 2:

                        Eventos eventosFragment = (Eventos) adapter.getPage(2);
                        if (newText != null && !newText.isEmpty()) {
                            eventosFragment.pesquisarEventos(newText.toLowerCase());
                        } else {
                            eventosFragment.recarregarEventos();
                        }
                        break;
                }


                return true;
            }
        });


    }


    //----------------------------- Menu ---------------------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_home,menu); minha versao

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        MenuItem item = menu.findItem(R.id.action_Search);
        searchView.setMenuItem(item);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.duvidas:
                startActivity(new Intent(getBaseContext(),Duvidas.class));
                break;


            case R.id.sobrebuelojobs:

                startActivity(new Intent(getBaseContext(), SobreBuelojobs.class));
                break;



            case R.id.contato:
                startActivity(new Intent(getBaseContext(), ContatoBueloJobs.class));

                break;


            case R.id.sair:

                FirebaseAuth.getInstance().signOut();

                LoginManager.getInstance().logOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                googleSignInClient = GoogleSignIn.getClient(this, gso);
                googleSignInClient.signOut();
                finish();
                startActivity(new Intent(getBaseContext(), MainActivity.class));


                break;

        }

        return super.onOptionsItemSelected(item);
    }


}