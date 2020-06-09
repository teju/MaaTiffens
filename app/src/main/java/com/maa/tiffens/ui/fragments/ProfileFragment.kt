package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.maa.tiffens.R
import com.maa.tiffens.etc.GPSTracker
import com.maa.tiffens.etc.Helper
import com.maa.tiffens.ui.adapter.AddressAdapter
import com.maa.tiffens.ui.adapter.HistoryAdapter
import com.maa.tiffens.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : BaseFragment() ,View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)
        return v
    }

    override fun onBackTriggered() {
        home().exitApp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        edit_ptofile_pic.setOnClickListener(this)
        edit_details.setOnClickListener(this)
        add_new.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AddressAdapter(activity!!)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.edit_ptofile_pic -> {
                home().setFragment(ProfilePicUploadFragment())
            }
            R.id.add_new -> {
                home().setFragment(AddAddressFragment())
            }
            R.id.edit_details -> {
                home().setFragment(RegisterFragment().apply {
                    isEdit = true
                })
            }
        }
    }


}
