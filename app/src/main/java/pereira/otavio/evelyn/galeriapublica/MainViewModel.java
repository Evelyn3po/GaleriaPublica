package pereira.otavio.evelyn.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import pereira.otavio.evelyn.galeriapblica.R;

//MainViewModel herda de AndroidViewModel
public class MainViewModel extends AndroidViewModel {
    //MAinViewModel guarda navigationOpSelected
    int navigationOpSelected = R.id.gridViewOp;
    //parâmetro de entrada
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }
}
