package com.amit.saha.util

import com.amit.saha.model.Facts
import com.amit.saha.model.Rows


object MockTestUtil {

    private val TITLE = "About Canada"
    val Row1Title = "Hockey Night in Canada"
    val Row1Description = "These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week."
    val Row1imageHref = "http://fyimusic.ca/wp-content/uploads/2008/06/hockey-night-in-canada.thumbnail.jpg"

    fun mockFactResponse(): Facts {
        return Facts(TITLE, mockRows())
    }

    fun mockRows(): ArrayList<Rows> {
        val rows = ArrayList<Rows>()

        val row1 = Rows(Row1Title, Row1Description, Row1imageHref)
        rows.add(row1)

        return rows
    }
}
