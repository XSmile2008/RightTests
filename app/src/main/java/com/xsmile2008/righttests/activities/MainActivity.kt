package com.xsmile2008.righttests.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override val viewModel: MainViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(viewModel)
        viewModel.weatherData.observe(this, Observer {
            txt_main.text = it?.toString() ?: getString(R.string.no_data)
        })

        setContentView(R.layout.activity_main)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        btn_change_location.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!viewModel.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_change_location -> viewModel.onChangeLocationClicked()
        }
    }
}
