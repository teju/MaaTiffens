package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.maa.tiffens.R
import com.maa.tiffens.etc.GPSTracker
import com.maa.tiffens.etc.Helper
import com.maa.tiffens.ui.adapter.HistoryAdapter
import com.maa.tiffens.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.history_fragment.*


class HistoryFragment : BaseFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.history_fragment, container, false)
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
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = HistoryAdapter(activity!!)
    }


}