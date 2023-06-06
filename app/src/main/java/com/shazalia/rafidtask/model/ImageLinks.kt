 package com.shazalia.rafidtask.model

import com.google.gson.annotations.SerializedName


data class ImageLinks(

    @SerializedName("smallThumbnail") var smallThumbnail: String? = "https://i.imgur.com/YjoNXCX.png",
    @SerializedName("thumbnail") var thumbnail: String? = "https://i.imgur.com/YjoNXCX.png"

)