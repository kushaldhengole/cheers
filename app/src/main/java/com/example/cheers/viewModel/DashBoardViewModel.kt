package com.example.cheers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheers.data.nerwork.CheersApiService
import com.example.cheers.respository.CheersRespoInterface
import com.example.cheers.respository.Source
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel
import com.example.cheers.util.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor (
    val cheersRespoInterface: CheersRespoInterface
)  :ViewModel() {

    val _fooPaireddBeerList: MutableLiveData<List<FoodPairedBeerDataModel>> by lazy { MutableLiveData() }
    val foodPairedBeerList: LiveData<List<FoodPairedBeerDataModel>> get() = _fooPaireddBeerList

    val _randomBeer: MutableLiveData<BeerDataModel> by lazy { MutableLiveData() }
    val randomBeer: LiveData<BeerDataModel> get() = _randomBeer

    val _isLoading : MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading: LiveData<Boolean> get() = _isLoading

    val _isLoading_one : MutableLiveData<Boolean> by lazy { MutableLiveData() }
    val isLoading_one: LiveData<Boolean> get() = _isLoading_one

    val _isEmptyORError: MutableLiveData<Pair<String, Boolean>> by lazy { MutableLiveData() }
    val isEmptyOrErro: LiveData<Pair<String, Boolean>> get() = _isEmptyORError

    var _page:Int=2
    val page:Int get() = _page

    var _per_page:Int=4
    val per_page:Int get() = _per_page

    var _food:String?="Chicken"
    val food:String? get() = _food


    fun getFoodpairedBeerList(){
        viewModelScope.launch {
            cheersRespoInterface.getFoodPairedBeer(page = page,
            pageSize = per_page, food = food?:"Chicken").collect {
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
                                    _fooPaireddBeerList.postValue(
                                            result.data,
                                    )


                                }

                            }
                            Source.LOCAL -> {

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
    fun getRandomBeeer(){
        viewModelScope.launch {

                cheersRespoInterface.getRandomBeer().collect {
                        result ->
                    when (result) {
                        is RepositoryResult.Success -> {
                            _randomBeer.postValue(result.data)
                        }
                        is RepositoryResult.Error -> {

                        }
                        is RepositoryResult.Loading -> {
                            _isLoading_one.postValue(result.isLoading)
                        }
                        else -> {

                        }
                    }
                }
            }

        }
    }

