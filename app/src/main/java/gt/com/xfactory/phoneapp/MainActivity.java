package gt.com.xfactory.phoneapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigationView();
    }


    /**
     * metodo para agregar BottomNavigationView e incluir el listener de seleccion de clic
     */
    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        if (bottomNavigationView != null) {

            // Poniendo el menu del bottomNavigationView en el primer item
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));

            // Accion para cuando el usuario seleccione una opcion del menu
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected( @NonNull MenuItem item) {
                            //metodo para seleccionar el Fragment basado en la seleccion del usuario
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }

    /**
     * Metodo para seleccionar el Fragment segun el parametro enviado.
     *
     * @param item Item that is selected.
     */
    protected void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.navigation1:
                // Action to perform when Home Menu item is selected.
                //pushFragment(new HomeFragment());
                break;
            case R.id.navigation2:
                // Action to perform when Bag Menu item is selected.
               pushFragment(new ContactFragment());
                break;
            case R.id.navigation3:
                // Action to perform when Account Menu item is selected.
                break;
        }
    }

    /**
     * Recibe el fragmento seleccionado, si el fragmento no es null se obtiene el FragmentManager.
     * Si el fragment manager no es nulo se prosigue a hacer la invocacion del fragmento a mostrar
     * @param fragment An instance of Fragment to show into the given id.
     */
    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        }
    }
}
