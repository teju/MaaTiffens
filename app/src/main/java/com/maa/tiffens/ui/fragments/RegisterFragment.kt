package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.maa.tiffens.R
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : BaseFragment() ,View.OnClickListener {


    var isEdit = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.register_fragment, container, false)
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI();
    }

    private fun initUI() {
        submit.setOnClickListener(this)
        address.setOnClickListener(this)
        if(isEdit) {
            address.visibility= View.GONE
            heading.visibility= View.GONE
            logo.visibility= View.GONE
            pgone_number.visibility= View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.submit -> {
                if(!isEdit) {
                    home().setFragment(ProfilePicUploadFragment())
                } else {
                    home().proceedDoOnBackPressed()
                }
            }
            R.id.address -> {
                home().setFragment(AddAddressFragment())
            }
        }
    }


}
