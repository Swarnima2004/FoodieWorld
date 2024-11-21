package com.example.foodieworld.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class orderDetails(): Parcelable {
    var userUid: String ?= null
    var userName : String ?= null
    var foodName : MutableList<String>?= null
    var foodImages : MutableList<String>?= null
    var foodPrices : MutableList<String>?= null
    var foodQuantities : MutableList<String> ?= null
    var address : String?= null
    var totalPrice : String ?= null
    var phoneNumber : String ?= null
    var orderAccepted: Boolean = false
    var paymentReceived : Boolean = false
    var itemPushKey : String ?= null
    var currentItem : Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentItem = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantities: ArrayList<Int>,
        address: String,
        phone: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<orderDetails> {
        override fun createFromParcel(parcel: Parcel): orderDetails {
            return orderDetails(parcel)
        }

        override fun newArray(size: Int): Array<orderDetails?> {
            return arrayOfNulls(size)
        }
    }

}