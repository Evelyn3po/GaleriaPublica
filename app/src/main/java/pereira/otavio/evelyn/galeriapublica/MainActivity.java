package pereira.otavio.evelyn.galeriapublica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.Manifest;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import pereira.otavio.evelyn.galeriapblica.R;

public class MainActivity extends AppCompatActivity {
    static int RESULT_REQUEST_PERMISSION = 2;

    BottomNavigationView bottomNavigationView;

    //recebe como parâmetro um fragment
    void setFragment(Fragment fragment) {
        //é iniciada uma transação do gerenciador de fragmentos
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        //esse fragment será setado no espaço definido pelo elemento de UI fragContainer
        fragmentTransaction.replace(R.id.fragContainer, fragment);
        //esse fragmento agora faz parte da pilha de tela do botão voltar do Android
        fragmentTransaction.addToBackStack(null);
        //é realizado o commit da transação
        fragmentTransaction.commit();
    }

    private void checkForPermissions(List<String> permissions) {
        List<String> permissionsNotGranted = new ArrayList<>();

        for (String permission : permissions) {
            if (!hasPermission(permission)) {
                permissionsNotGranted.add(permission);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsNotGranted.size() > 0) {
                requestPermissions(permissionsNotGranted.toArray(new
                        String[permissionsNotGranted.size()]), RESULT_REQUEST_PERMISSION);
            }
        } else {
            MainViewModel vm = new
                    ViewModelProvider(this).get(MainViewModel.class);
            int navigationOpSelected = vm.getNavigationOpSelected();
            bottomNavigationView.setSelectedItemId(navigationOpSelected);
        }
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return
                    ActivityCompat.checkSelfPermission(MainActivity.this, permission) ==
                            PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    //toda vez que o usuário selecionar uma das opções, o método onNavigationItemSelected será chamado
    public void onRequestPermissionsResult(int requestCode, @NonNull
    String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        final List<String> permissionsRejected = new ArrayList<>();
        if (requestCode == RESULT_REQUEST_PERMISSION) {

            for (String permission : permissions) {
                if (!hasPermission(permission)) {
                    permissionsRejected.add(permission);

                }
            }
        }

        if (permissionsRejected.size() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                    new AlertDialog.Builder(MainActivity.this).
                            setMessage("Para usar essa app é preciso conceder essas permissões").
                            setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new
                                            String[permissionsRejected.size()]), RESULT_REQUEST_PERMISSION);
                                }
                            }).create().show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // obtemos uma referência para MainViewModel
        final MainViewModel vm = new
                ViewModelProvider(this).get(MainViewModel.class);

        //obtemos a referência para o BottonNavigationView.
        bottomNavigationView = findViewById(R.id.btNav);

        //setamos em bottonNavigationView o “escutador” de eventos de seleção do menu
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.gridViewOp:
                        GridViewFragment gridViewFragment = GridViewFragment.newInstance();
                        setFragment(gridViewFragment);
                        break;
                        case R.id.listViewOp:
                            ListViewFragment listViewFragment = ListViewFragment.newInstance();
                            setFragment(listViewFragment);
                            break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        checkForPermissions(permissions);
    }
}