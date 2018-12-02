package com.xsmile2008.righttests.cache

import android.content.SharedPreferences
import com.xsmile2008.righttests.BaseTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner.Silent::class)
class SPCacheTest : BaseTest() {

    companion object {
        const val LOCATION_KYIV = "Kyiv,ua"
        const val LOCATION_LVIV = "Lviv,ua"
    }

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    private val spCacheImpl by lazy { SPCache(sharedPreferences) }

    @Before
    override fun before() {
        super.before()
        doReturn(editor).`when`(sharedPreferences).edit()
        doReturn(editor).`when`(editor).clear()
        doReturn(editor).`when`(editor).putBoolean(anyString(), anyBoolean())
        doReturn(editor).`when`(editor).putInt(anyString(), anyInt())
        doReturn(editor).`when`(editor).putLong(anyString(), anyLong())
        doReturn(editor).`when`(editor).putFloat(anyString(), anyFloat())
        doReturn(editor).`when`(editor).putString(anyString(), anyString())
        doReturn(editor).`when`(editor).putStringSet(anyString(), any())
        doReturn(true).`when`(editor).commit()
    }

    @After
    override fun after() {
        super.after()
        verifyNoMoreInteractions(sharedPreferences, editor)
    }

    //region location property tests

    @Test
    fun check_location_get_hasValue() {
        //Setup
        doReturn(LOCATION_KYIV)
                .`when`(sharedPreferences)
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
                .`when`(sharedPreferences)
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
                .`when`(sharedPreferences)
                .getString(SPCache.PREF_LOCATION, SPCache.DEFAULT_LOCATION)

        doReturn(true).`when`(editor).commit()

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