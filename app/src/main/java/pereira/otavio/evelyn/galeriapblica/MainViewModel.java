package pereira.otavio.evelyn.galeriapblica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
