package com.nevdiaz.qodclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.nevdiaz.qodclient.model.Quote;
import com.nevdiaz.qodclient.service.QodService;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.LinkedList;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<Quote> random;
  private MutableLiveData<Quote> search;
  private CompositeDisposable pending = new CompositeDisposable();

  public MainViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<Quote> getRandomQuote() {
    if (random == null) {
      random = new MutableLiveData<>();
    }
    pending.add(
        QodService.getInstance().random()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((quote) -> random.setValue(quote))
    );

    return random;
  }
  public LiveData<Quote> searchQuote(String string) {
    if (search == null) {
      search = new MutableLiveData<>();
    }
    if (search != null) {
      pending.add(
          QodService.getInstance().search(search)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe((quotes) -> search.setValue(quotes)));
    } else {
      search.setValue(new LinkedList<>());
    }
    return search;
  }
}
