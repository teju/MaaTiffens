package com.maa.tiffens.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.maa.tiffens.R
import com.maa.tiffens.ui.adapter.HistoryAdapter
import com.maa.tiffens.ui.adapter.ProductsAdapter
import com.maa.tiffens.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.product_list_fragment.*


class ProductListFragment : BaseFragment() ,View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.product_list_fragment, container, false)
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
        filter.visibility = View.VISIBLE
        order_now.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ProductsAdapter(activity!!)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.order_now -> {
                home().setFragment(CartFragment())
            }
        }
    }


}
