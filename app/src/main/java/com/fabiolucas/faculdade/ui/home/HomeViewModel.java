package com.fabiolucas.faculdade.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fabiolucas.faculdade.infos.Pessoa;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    Pessoa pessoa = new Pessoa();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        pessoa.setEmail("tes2te");
        pessoa.setNome("test2e");
        pessoa.setTelefone("test2e");

        mText.setValue(pessoa.toString());


    }




    public LiveData<String> getText() {
        return mText;
    }
}