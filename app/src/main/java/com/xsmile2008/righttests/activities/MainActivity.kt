package com.xsmile2008.righttests.activities

import android.os.Bundle
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.viewmodels.MainViewModel

class MainActivity : BaseActivity() {

    override val viewModel: MainViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
