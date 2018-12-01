package com.xsmile2008.righttests.activities

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.viewmodels.LocationViewModel
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val REQUEST_CODE = 117
    }

    override val viewModel: LocationViewModel by viewModelDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        viewModel.location.observe(this, Observer {
            f_location.setText(it)
        })
    }

    override fun onContentChanged() {
        super.onContentChanged()
        btn_save.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_save -> viewModel.onSaveClicked(f_location.text.toString())
        }
    }
}
