package com.maa.tiffens.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maa.tiffens.R
import com.maa.tiffens.SearchActivity
import com.maa.tiffens.etc.GPSTracker
import com.maa.tiffens.etc.Helper
import com.maatiffens.libs.helpers.BaseHelper
import kotlinx.android.synthetic.main.add_address.*
import kotlinx.android.synthetic.main.add_address.locationAddress
import kotlinx.android.synthetic.main.header.*


class AddAddressFragment : BaseFragment() ,View.OnClickListener {

    private val REQUEST_CODE_AUTOCOMPLETE = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.add_address, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        title.setText("Add Address")
        change_address.setOnClickListener(this)
        save_address.setOnClickListener(this)
        val gpsTracker = GPSTracker(activity!!)
        if(gpsTracker.canGetLocation()) {
            val address = Helper.getAddress(activity!!,gpsTracker.latitude,gpsTracker.longitude)
            locationAddress.setText(address?.get(0)?.getAddressLine(0))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.save_address -> {
                home().proceedDoOnBackPressed()
            }
            R.id.change_address -> {
                startActivityForResult(Intent(activity, SearchActivity::class.java),REQUEST_CODE_AUTOCOMPLETE);

            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
                val lat = data?.getDoubleExtra("Lat",0.0)
                val lng = data?.getDoubleExtra("Lng",0.0)
                var address = data?.getStringExtra("Address")
                if(BaseHelper.isEmpty(address)) {
                    val list_address = Helper.getAddress(activity!!, lat!!,lng!!)
                    address = list_address?.get(0)?.getAddressLine(0)
                }
                locationAddress.setText(address)
            }
        } catch (e : Exception){

        }


    }


}
