package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maa.tiffens.R
import com.maatiffens.libs.helpers.BaseHelper
import com.mukesh.OnOtpCompletionListener
import kotlinx.android.synthetic.main.otp_validation_fragment.*


class OtpValidationFragment : BaseFragment() ,View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.otp_validation_fragment, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        btnVerify.setOnClickListener(this)
        otp_view.setOtpCompletionListener {
            if(!BaseHelper.isEmpty(it)) {

            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnVerify -> {
                home().setFragment(RegisterFragment())
            }
        }
    }


}
