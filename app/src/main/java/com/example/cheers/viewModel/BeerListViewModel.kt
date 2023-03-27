package com.example.cheers.viewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheers.data.respository.CheersRespoInterface
import com.example.cheers.data.respository.Source
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.util.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor (private val cheersRespoInterface: CheersRespoInterface) : ViewModel(),LifecycleObserver{

    var selectedBeerid:Long?=null

    val _BeerList:MutableLiveData<RepositoryResult<List<BeerDataModel>>> by lazy { MutableLiveData() }
     val beerList:LiveData<RepositoryResult<List<BeerDataModel>>> get() = _BeerList

    val _beerSelected:MutableLiveData<BeerDataModel> by lazy { MutableLiveData() }
    val beerSelected:LiveData<BeerDataModel> get() = _beerSelected

    val _isLoading :MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading:LiveData<Boolean> get() = _isLoading

    val _isEmptyORError:MutableLiveData<Pair<String,Boolean>> by lazy { MutableLiveData() }
    val isEmptyOrErro:LiveData<Pair<String,Boolean>> get() = _isEmptyORError


    fun getBeerList(){
        viewModelScope.launch {
            cheersRespoInterface.getBeerList(true).collect {
                    result ->
                when (result) {
                    is RepositoryResult.Success -> {
                        when (result.source) {
                            Source.REMOTE -> {
                                if (result.data?.isEmpty() == true){
                                    _isEmptyORError.postValue(Pair("No Data Found",true))
                                }
                                else {
                                    _isEmptyORError.postValue(Pair("",false))
                                    _BeerList.postValue(
                                        RepositoryResult.Success(
                                            result.data,
                                            result.source
                                        )
                                    )

                                    cheersRespoInterface.deleteBeers()
                                    result.data?.let {
                                        cheersRespoInterface.insertBeerDataLocally(
                                            it.map {
                                                it.toEntity()
                                            }
                                        )
                                    }

                                }

                            }
                            Source.LOCAL -> {
                                if (result.data?.isEmpty()==true){
                                    _isEmptyORError.postValue(Pair("No Data Found",true))
                                }else{
                                    _BeerList.postValue(RepositoryResult.Success(result.data,result.source))
                                    _isEmptyORError.postValue(Pair("",false))
                                }

                            }
                        }
                    }
                    is RepositoryResult.Loading -> {
                        _isLoading.postValue(result.isLoading)
                    }
                    is RepositoryResult.Error -> {
                        _isEmptyORError.postValue(Pair("Something went wrong",true))
                    }

                    else -> {
                    }
                }
            }
        }
    }
}