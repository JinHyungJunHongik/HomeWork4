package com.example.homwwork4

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Stuff(val name: String,val address: String,val img: Int,val price: Int, val celler: String) : Parcelable {
    private var _name = ""
    private var _address = ""
    private var _img = 0
    private var _price = 0
    private var _celler = ""
    private var like = 0
    private var introduce = ""
    private var comments = mutableListOf<String>()
    private var interest =  false

    init{
        _name = name
        _address = address
        _img = img
        _price = price
        _celler = celler
    }

    fun pullName() : String{
        return this._name
    }
    fun pullAddress() : String {
        return this._address
    }
    fun pullImg() : Int {
        return this._img
    }
    fun pullPrice() : Int {
        return this._price
    }
    fun addLike(){
        this.like++
    }
    fun pullLike() : Int {
        return this.like
    }
    fun pullComments() : MutableList<String> {
        return this.comments
    }
    fun pullCeller() : String {
        return this._celler
    }
    fun pushIntroduce(str: String){
        this.introduce = str
    }
    fun pullIntroduce() : String{
        return this.introduce
    }
    fun decreaseLike(){
        this.like--
    }
    fun priceCount() : String {
        var textPrice = ""
        var resultPrice = ""
        var buffer = this.pullPrice().toString()
        var count = 0
        for(i in buffer.length - 1 downTo 0){
            ++count
            textPrice += buffer[i]
            if(count == 3 && i > 0){
                textPrice += ','
                count = 0
            }
        }
        resultPrice = textPrice.reversed() + "원"
        return resultPrice
    }
    fun isInterest() {
        this.interest = true
    }
    fun isNotInterest() {
        this.interest = false
    }
    fun pullInterest() : Boolean {
        return this.interest
    }
}