package com.amit.saha.model

import com.amit.saha.ui.fragments.adapter.RecyclerAdapter.Companion.FactItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import java.io.Serializable

data class Rows(@field:SerializedName("title")
                @field:Expose
                var title: String?, @field:SerializedName("description")
                @field:Expose
                var description: String?, @field:SerializedName("imageHref")
                @field:Expose
                var imageHref: String?): Serializable {
    @IgnoredOnParcel
    var itemType: Int = FactItem
}
