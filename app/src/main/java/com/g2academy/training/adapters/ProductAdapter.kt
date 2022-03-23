package com.g2academy.training.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.g2academy.training.R
import com.g2academy.training.models.Product
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProductRowHolder(row : View?){
    val txt_id : TextView
    val txt_name : TextView
    val txt_price : TextView
    val photo : ImageView
    val linear_layout : LinearLayout
    init{
        this.txt_id = row?.findViewById(R.id.txt_id) as TextView
        this.txt_name = row?.findViewById(R.id.txt_name) as TextView
        this.txt_price = row?.findViewById(R.id.txt_price) as TextView
        this.photo = row?.findViewById(R.id.photo) as ImageView
        this.linear_layout = row?.findViewById(R.id.linearlayout_product) as LinearLayout
    }
}

class ProductAdapter(context : Context,arrayListDetails : ArrayList<Product>):BaseAdapter() {

    val layoutInflater : LayoutInflater
    val arrayListDetails : ArrayList<Product>

    init{
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails = arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view : View?
        val productRowHolder : ProductRowHolder
        if(convertView == null){
            view = this.layoutInflater.inflate(R.layout.adapter_product,parent,false)
            productRowHolder = ProductRowHolder(view)
            view.tag = productRowHolder
        }
        else{
            view = convertView
            productRowHolder = view.tag as ProductRowHolder
        }
        try{
            productRowHolder.txt_name.text = arrayListDetails.get(position).name
            productRowHolder.txt_id.text = arrayListDetails.get(position).id
            productRowHolder.txt_price.text = arrayListDetails.get(position).price
            Picasso.get()
                .load(arrayListDetails.get(position).photo)
                .resize(40,40)
                .placeholder(R.drawable.ic_brokenimage)
                .into(productRowHolder.photo)
        }
        catch (e:Exception){
            Log.d("TESTING",e.toString())
        }
        return view
    }

}