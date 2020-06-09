package com.maa.tiffens.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.maa.tiffens.R
import com.maa.tiffens.SearchActivity
import com.maa.tiffens.etc.GPSTracker
import com.maa.tiffens.etc.Helper
import com.maa.tiffens.ui.adapter.AddressAdapter
import com.maa.tiffens.ui.adapter.ProductsAdapter
import com.maatiffens.libs.helpers.BaseHelper
import kotlinx.android.synthetic.main.addresslist_fragment.*
import kotlinx.android.synthetic.main.header.*


class AddressListFragment : BaseFragment() ,View.OnClickListener {

    private val REQUEST_CODE_AUTOCOMPLETE = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.addresslist_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        title.setText("Addresses")
        floating_action_button.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AddressAdapter(activity!!)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.floating_action_button -> {
                home().setFragment(AddAddressFragment())
            }
        }
    }


}
