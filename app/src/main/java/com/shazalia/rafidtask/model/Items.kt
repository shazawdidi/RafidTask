package com.shazalia.rafidtask.model

import com.google.gson.annotations.SerializedName


data class Items(

    @SerializedName("id") var id: String? = null,
    @SerializedName("volumeInfo") var volumeInfo: VolumeInfo? = VolumeInfo(),
)
