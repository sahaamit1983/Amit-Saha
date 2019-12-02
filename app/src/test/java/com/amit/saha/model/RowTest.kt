package com.amit.saha.model

import org.junit.Assert
import org.junit.Test

class RowTest {

    /*
    *  Compare two identical rows
    * */

    @Test
    @Throws(Exception::class)
    fun isRowsEqual_returnTrue() {

        // Arrange
        val row1 = Rows("Housing", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")
        val row2 = Rows("Housing", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")

        // Act

        //Assert
        Assert.assertEquals(row1, row2)
    }

    /*
     *  Compare two rows with different title
     * */
    @Test
    @Throws(Exception::class)
    fun isRowsEqual_differentTitle_returnFalse() {

        // Arrange
        val row1 = Rows("Housing", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")
        val row2 = Rows("Public Shame", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")

        // Act

        //Assert
        Assert.assertNotEquals(row1, row2)
    }

    /*
     *  Compare two rows with different description
     * */
    @Test
    @Throws(Exception::class)
    fun isRowsEqual_differentDescription_returnFalse() {

        // Arrange
        val row1 = Rows("Housing", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")
        val row2 = Rows("Housing", "Sadly it's true.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")

        // Act

        //Assert
        Assert.assertNotEquals(row1, row2)
    }

    /*
     *  Compare two rows with different url
     * */
    @Test
    @Throws(Exception::class)
    fun isRowsEqual_differentUrl_returnFalse() {

        // Arrange
        val row1 = Rows("Housing", "Warmer than you might think.", "http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")
        val row2 = Rows("Housing", "Warmer than you might think.", "http://static.guim.co.uk/sys-images/Music/Pix/site_furniture/2007/04/19/avril_lavigne.jpg")

        // Act

        //Assert
        Assert.assertNotEquals(row1, row2)
    }
}
