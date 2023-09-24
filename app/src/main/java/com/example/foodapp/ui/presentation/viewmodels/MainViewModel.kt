package com.example.foodapp.ui.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.model.product.ProductItem
import com.example.foodapp.data.model.product.ProductItemModel

class MainViewModel: ViewModel() {

    private val initialList = mutableListOf<ProductItemModel>().apply {
        repeat(20){
            add(
                ProductItemModel(
                    productItem = ProductItem(
                        it,676168,"Авокадо Кранч Маки 8шт",
                        "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо.Украшается соусом\"Унаги\" и легким майонезом  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",

                        "1.jpg",
                        47000,null,
                        250,"г",
                        307.8, 6.1, 11.4,45.1,
                          null
                    ),
                    add = 0
                )
            )
        }
    }

    private val _models = MutableLiveData<List<ProductItemModel>>(initialList)
    val models: LiveData<List<ProductItemModel>> = _models

    fun changeAddPlus(model: ProductItemModel){
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll{
            if(it == model){
                it.copy(add = it.add + 1)
            }else{
                it
            }
        }
        _models.value = modifiedList
    }

    fun changeAddMinus(model: ProductItemModel){
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll{
            if(it == model){
                it.copy(add = it.add - 1)
            }else{
                it
            }
        }
        _models.value = modifiedList
    }


}