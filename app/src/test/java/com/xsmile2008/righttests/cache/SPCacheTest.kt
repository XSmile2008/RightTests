package com.xsmile2008.righttests.cache

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.*
import com.xsmile2008.righttests.BaseTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class SPCacheTest : BaseTest() {

    companion object {
        const val LOCATION_KYIV = "Kyiv,ua"
        const val LOCATION_LVIV = "Lviv,ua"
    }

    private val editor: SharedPreferences.Editor = autoverified(
            mock {
                on { putBoolean(any(), any()) } doReturn mock
                on { putInt(any(), any()) } doReturn mock
                on { putLong(any(), any()) } doReturn mock
                on { putFloat(any(), any()) } doReturn mock
                on { putString(any(), any()) } doReturn mock
                on { putStringSet(any(), any()) } doReturn mock
                on { remove(any()) } doReturn mock
                on { commit() } doReturn true
            }
    )
    private val sharedPreferences: SharedPreferences = autoverified(
            mock {
                on { edit() } doReturn editor
            }
    )

    private val spCacheImpl by lazy { SPCache(sharedPreferences) }

    //region location property tests

    @Test
    fun check_location_get_hasValue() {
        //Setup
        doReturn(LOCATION_KYIV)
                .whenever(sharedPreferences)
                .getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)

        //Run
        assertEquals(LOCATION_KYIV, spCacheImpl.location)

        //Verify
        verify(sharedPreferences).getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)
    }

    @Test
    fun check_location_get_defaultValue() {
        //Setup
        doReturn(SPCache.DEFAULT_LOCATION)
                .whenever(sharedPreferences)
                .getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)

        //Run
        assertEquals(SPCache.DEFAULT_LOCATION, spCacheImpl.location)

        //Verify
        verify(sharedPreferences).getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)
    }

    @Test
    fun check_location_set() {
        //Setup
        val inOrder = inOrder(sharedPreferences, editor)

        doReturn(SPCache.DEFAULT_LOCATION)
                .whenever(sharedPreferences)
                .getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)

        doReturn(true).whenever(editor).commit()

        //Run
        assertEquals(SPCache.DEFAULT_LOCATION, spCacheImpl.location)

        //Verify
        inOrder.verify(sharedPreferences).getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)

        //Run 2
        spCacheImpl.location = LOCATION_LVIV
        assertEquals(LOCATION_LVIV, spCacheImpl.location)

        //Verify 2
        inOrder.verify(sharedPreferences).edit()
        inOrder.verify(editor).putString(SPCache.PREF_LOCATION, LOCATION_LVIV)
        inOrder.verify(editor).commit()
    }
    //endregion
}