package hyunji.shin.test_server1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import hyunji.shin.test_server1.API.ISearchAPI;
import hyunji.shin.test_server1.API.RetrofitClient;
import hyunji.shin.test_server1.Adapter.PersonAdapter;
import hyunji.shin.test_server1.Model.Person;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ISearchAPI myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView recycler_search;
    LinearLayoutManager layoutManager;
    PersonAdapter adapter;

    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init API
        myAPI = getAPI();

        //View
        recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        recycler_search.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_search.setLayoutManager(layoutManager);
        recycler_search.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        materialSearchBar.setCardViewElevation(10);

        //Add suggest list
        addSuggestList();
        getAllPerson();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for (String search_term : suggestList)
                    if (search_term.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search_term);
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    getAllPerson();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private ISearchAPI getAPI() {
        return RetrofitClient.getInstatnce().create(ISearchAPI.class);
    }

    private void startSearch(String query) {
        compositeDisposable.add(myAPI.searchPerson(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Person>>() {
                    @Override
                    public void accept(List<Person> people) throws Exception {
                        adapter = new PersonAdapter(getBaseContext(),people);
                        recycler_search.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                    }
                }));
    }

    private void getAllPerson() {
        compositeDisposable.add(myAPI.getPersonList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Person>>() {
                    @Override
                    public void accept(List<Person> people) throws Exception {
                        adapter = new PersonAdapter(getBaseContext(),people);
                        recycler_search.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_LONG).show();
                    }
                }));
    }

    private void addSuggestList() {
        //Here you can load suggest list from cache or somewhere
        //In this example i will add manual
        suggestList.add("Eddy");
        suggestList.add("b");
        suggestList.add("d");
        suggestList.add("e");

        materialSearchBar.setLastSuggestions(suggestList);

    }
}
