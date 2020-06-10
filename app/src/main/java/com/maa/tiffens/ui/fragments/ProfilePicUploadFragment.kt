package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maa.tiffens.R
import kotlinx.android.synthetic.main.profile_pic_upload_fragment.*


class ProfilePicUploadFragment : BaseFragment() ,View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.profile_pic_upload_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        btnupload.setOnClickListener(this)
        skip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnupload,R.id.skip  -> {
                home().setFragment(MainFragment())
            }
        }
    }
}
