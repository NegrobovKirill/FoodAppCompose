package com.example.foodapp.ui.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodapp.R
import com.example.foodapp.data.Repository
import com.example.foodapp.data.model.category.CategoryItem
import com.example.foodapp.data.model.category.CategoryItemModel
import com.example.foodapp.data.model.product.ProductItem
import com.example.foodapp.data.model.product.ProductItemModel
import com.example.foodapp.data.model.tag.TagsItem
import com.example.foodapp.data.model.tag.TagsItemModel
import com.google.gson.Gson


class MainViewModel: ViewModel() {



    //private var initialList: MutableList<ProductItemModel> = mutableListOf()

    fun update(jsonString: String?,jsonCategories: String?,jsonTags: String?){
        var initialListProducts = mutableListOf<ProductItemModel>().apply {
            val list = Repository().getListProducts(jsonString)
            for (item in list){
                add(item)
            }
            }

        _models = MutableLiveData<List<ProductItemModel>>(initialListProducts)
        models = _models

        var initialListCategory = mutableListOf<CategoryItemModel>().apply {
            val list = Repository().getListCategories(jsonCategories)
            for (item in list){
                add(item)
            }
        }

        chipFilter.value = initialListCategory
        _chipFilter = initialListCategory

        var initialListTags = mutableListOf<TagsItemModel>().apply {
            val list = Repository().getListTags(jsonTags)
            for (item in list){
                add(item)
            }
        }

        tagsFilter.value = initialListTags
        _tagsFilter = initialListTags

//        productTags.value = initialListTags

    }

    private lateinit var _chipFilter: List<CategoryItemModel>
    var chipFilter = MutableLiveData<MutableList<CategoryItemModel>>()

    private lateinit var _tagsFilter: MutableList<TagsItemModel>
    var tagsFilter = MutableLiveData<MutableList<TagsItemModel>>()


//    var productTags = MutableLiveData<MutableList<TagsItem>>()

//    private val initialList = mutableListOf<ProductItemModel>().apply {
//        repeat(20){
//            add(
//                ProductItemModel(
//                    productItem = ProductItem(
//                        it,676168,"Авокадо Кранч Маки 8шт",
//                        "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо.Украшается соусом\"Унаги\" и легким майонезом  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
//
//                        "1.jpg",
//                        47000,null,
//                        250,"г",
//                        307.8, 6.1, 11.4,45.1,
//                          null
//                    ),
//                    add = 0
//                )
//            )
//        }
//    }



    var price = MutableLiveData<Int>(0)

    private val _basketList = MutableLiveData<List<ProductItemModel>>()
    val basketList: LiveData<List<ProductItemModel>> = _basketList

    private var _models = MutableLiveData<List<ProductItemModel>>()
    var models: LiveData<List<ProductItemModel>> = _models


    var modelsSelected = MutableLiveData<MutableList<ProductItemModel>>()

    var modelsSelectedWithTags = MutableLiveData<List<ProductItemModel>>()


    fun firstInit(){
        changeChipSelector(chipFilter.value!!.get(0))

    }


    fun updateTagsModel(model: TagsItemModel){
        var counterAllFalse = 0
        for (i in tagsFilter.value!!){
            if (i.isSelected == false){
                counterAllFalse = counterAllFalse + 1
            }
        }

        var counterAllTrue = 0
        for (i in tagsFilter.value!!){
            if (i.isSelected == true){
                counterAllTrue = counterAllTrue + 1
            }
        }
        var newItems: MutableList<ProductItemModel> = mutableListOf()

        if (counterAllFalse == 5 || counterAllTrue == 5){
            newItems = modelsSelected.value!!
        }
        else
        {

            for (item in modelsSelected.value!!){
                if (item.productItem.tag_ids!!.size != 0){
                    for (items in item.productItem.tag_ids){
                        if (items == model.tagsItem.id){
                            newItems.add(item)
                        }
                    }
                }

            }
        }


        modelsSelectedWithTags.value = newItems
    }
    fun updateTagsFilter(model: TagsItemModel){
        val modifiedList = _tagsFilter.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll{
            if(it == model){
                it.copy(isSelected = !it.isSelected)
            }else{
                it
            }
        }
        tagsFilter.value = modifiedList

        var counterAllFalse = 0
        for (i in tagsFilter.value!!){
            if (i.isSelected == false){
                counterAllFalse = counterAllFalse + 1
            }
        }

        var counterAllTrue = 0
        for (i in tagsFilter.value!!){
            if (i.isSelected == true){
                counterAllTrue = counterAllTrue + 1
            }
        }
        var newItems: MutableList<ProductItemModel> = mutableListOf()

        if (counterAllFalse == 5 || counterAllTrue == 5){

            for (item in chipFilter.value!!){
                if (item.isSelected == true){
                    updateTagsFilterWithCategory(item)
                }

            }
            modelsSelectedWithTags.value = modelsSelected.value

        }
        else
        {

            for (item in modelsSelected.value!!){
                if (item.productItem.tag_ids!!.size != 0){
                    for (items in item.productItem.tag_ids){
                        if (items == model.tagsItem.id){
                            newItems.add(item)
                        }
                    }
                }

            }
            modelsSelectedWithTags.value = newItems
        }



    }

    fun updateTagsFilterWithCategory(model: CategoryItemModel){
        val newItems: MutableList<ProductItemModel> = mutableListOf()
        for (item in _models.value!!){
            if (item.productItem.category_id == model.categoryItem.id){
                newItems.add(item)
            }
        }
        modelsSelected.value = newItems
        modelsSelectedWithTags.value = newItems
    }

    fun changeChipSelector(model: CategoryItemModel){

        val modifiedList = _chipFilter.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll{
            if(it == model){
                it.copy(isSelected = !it.isSelected)
            }else{
                it
            }
        }
        chipFilter.value = modifiedList

        val newItems: MutableList<ProductItemModel> = mutableListOf()
        for (item in _models.value!!){
            if (item.productItem.category_id == model.categoryItem.id){
                newItems.add(item)
            }
        }

        modelsSelected.value = newItems


        modelsSelectedWithTags.value = newItems

        for (items in tagsFilter.value!!){
            if (items.isSelected == true){
                updateTagsModel(items)
            }
        }
    }

    fun changeAddPlus(model: ProductItemModel){
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll{
            if(it == model){
                it.copy(add = it.add + 1)
            }else{
                it
            }
        }

        val modifiedSelectedList = modelsSelectedWithTags.value?.toMutableList() ?: mutableListOf()
        modifiedSelectedList.replaceAll{
            if(it == model){
                it.copy(add = it.add + 1)
            }else{
                it
            }
        }



        val modifiedBasketList = _basketList.value?.toMutableList() ?: mutableListOf()
        if (model.add == 0){
            modifiedBasketList.add(model.copy(model.productItem, 1))
        }
        else
        {
            for (elements in modifiedBasketList){
                if (elements.productItem == model.productItem){
                    elements.add = elements.add + 1
                    break
                }
            }
        }
        price.value = price.value?.plus(model.productItem.price_current)
        _models.value = modifiedList
        _basketList.value = modifiedBasketList
        modelsSelected.value = modifiedSelectedList


        modelsSelectedWithTags.value = modifiedSelectedList

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

        val modifiedSelectedList = modelsSelectedWithTags.value?.toMutableList() ?: mutableListOf()
        modifiedSelectedList.replaceAll{
            if(it == model){
                it.copy(add = it.add - 1)
            }else{
                it
            }
        }

        val modifiedBasketList = _basketList.value?.toMutableList() ?: mutableListOf()
        if (model.add > 1){
            for (elements in modifiedBasketList){
                if (elements.productItem == model.productItem){
                    elements.add = elements.add - 1
                    break
                }
            }
        }
        else
        {
            modifiedBasketList.remove(model)
        }
        price.value = price.value?.minus(model.productItem.price_current)
        _basketList.value = modifiedBasketList
        _models.value = modifiedList
        modelsSelected.value = modifiedSelectedList


        modelsSelectedWithTags.value = modifiedSelectedList
    }

}