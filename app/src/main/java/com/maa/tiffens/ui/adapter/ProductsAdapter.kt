package com.maa.tiffens.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iapps.gon.etc.callback.RecursiveListener
import com.maa.tiffens.R
import java.text.SimpleDateFormat


class ProductsAdapter(
    val context: Context) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>()  {
    lateinit var listener: RecursiveListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, parent, false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    }


}