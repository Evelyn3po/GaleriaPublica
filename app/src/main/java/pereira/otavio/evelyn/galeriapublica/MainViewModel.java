package pereira.otavio.evelyn.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;
import pereira.otavio.evelyn.galeriapblica.R;

LiveData<PagingData<ImageData>> pageLv;

//MainViewModel herda de AndroidViewModel
public class MainViewModel extends AndroidViewModel {
    //MAinViewModel guarda navigationOpSelected
    int navigationOpSelected = R.id.gridViewOp;

    //parâmetro de entrada

    public int getNavigationOpSelected() {
        return navigationOpSelected;

    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        GalleryRepository galleryRepository = new
                GalleryRepository(application);
        GalleryPagingSource galleryPagingSource = new
                GalleryPagingSource(galleryRepository);
        Pager<Integer, ImageData> pager = new Pager(new
                PagingConfig(10), () -> galleryPagingSource);
        CoroutineScope viewModelScope =
                ViewModelKt.getViewModelScope(this);
        pageLv =
                PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),
                        viewModelScope);
    }
    public LiveData<PagingData<ImageData>> getPageLv() {

        return pageLv;
    }
}
