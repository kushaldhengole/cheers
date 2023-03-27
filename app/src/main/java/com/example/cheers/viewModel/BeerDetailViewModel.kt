package com.example.cheers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheers.data.respository.CheersRespoInterface
import com.example.cheers.data.respository.Source
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.BeerDetailModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import com.example.cheers.util.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailViewModel @Inject constructor( val cheersRespoInterface: CheersRespoInterface):ViewModel(){

val _beerSelected:MutableLiveData<BeerDetailModel> by lazy { MutableLiveData() }
val beerSelected:LiveData<BeerDetailModel> get() = _beerSelected

val _isLoading :MutableLiveData<Boolean> by lazy { MutableLiveData() }
val isLoading:LiveData<Boolean> get() = _isLoading

val _isEmptyORError:MutableLiveData<Pair<String,Boolean>> by lazy { MutableLiveData() }
val isEmptyOrErro:LiveData<Pair<String,Boolean>> get() = _isEmptyORError

    var selectedBeerid:Long?=null

fun getSelectedBeer(){
    viewModelScope.launch {

        selectedBeerid?.let {
                id->
            cheersRespoInterface.getSelectedBeerDetail(id).collect {
                    result ->
                when (result) {
                    is RepositoryResult.Success -> {
                        _isEmptyORError.postValue(Pair("",false))
                        _beerSelected.postValue(result.data)
                    }
                    is RepositoryResult.Error -> {
                        _isEmptyORError.postValue(Pair("",true))
                    }
                    is RepositoryResult.Loading -> {
                        _isLoading.postValue(result.isLoading)
                    }
                    else -> {

                    }
                }
            }
        }
    }
}

}