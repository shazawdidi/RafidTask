 package com.shazalia.rafidtask.model

import com.google.gson.annotations.SerializedName
import com.shazalia.rafidtask.model.Items


data class Books(

    @SerializedName("items") var items: ArrayList<Items> = arrayListOf()

)
