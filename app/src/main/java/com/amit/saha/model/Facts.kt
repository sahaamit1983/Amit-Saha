package com.amit.saha.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Facts(@field:SerializedName("title")
                 @field:Expose
                var title : String?,

                @field:SerializedName("rows")
                @field:Expose
                var rows: List<Rows> = ArrayList()
): Serializable
