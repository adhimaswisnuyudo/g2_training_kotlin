package com.g2academy.training

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment() {

    lateinit var main_slider : ImageSlider
    lateinit var txt_sales : TextView
    lateinit var txt_balances : TextView
    lateinit var card_sales : CardView
    lateinit var card_balances : CardView
    var sales_count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity)!!.supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_slider = view.findViewById(R.id.main_slider)
        txt_sales = view.findViewById(R.id.txt_sales)
        txt_balances = view.findViewById(R.id.txt_balances)
        card_sales   = view.findViewById(R.id.card_sales)
        card_balances = view.findViewById(R.id.card_balances)
        val slide_model : ArrayList<SlideModel> = ArrayList()

        card_sales.setOnClickListener(View.OnClickListener {
            sales_count++
            txt_sales.setText(sales_count.toString())
        })
        slide_model.add(
            SlideModel(
                "https://i.ytimg.com/vi/BT_hFWj1ke8/maxresdefault.jpg",
                "Program Beasiswa",
                ScaleTypes.FIT
            )
        )
        slide_model.add(
            SlideModel(
                "https://i.ytimg.com/vi/DdhalB3WVNE/maxresdefault.jpg",
                "G2 Talk",
                ScaleTypes.FIT
            )
        )
        slide_model.add(
            SlideModel(
                "https://areatrik.com/wp-content/uploads/2020/08/G2-Academy-Prakerja.jpg",
                "Prakerja",
                ScaleTypes.FIT
            )
        )
        main_slider.setImageList(slide_model)
        main_slider.stopSliding()
        main_slider.startSliding(2000)
        main_slider.setItemClickListener(object :ItemClickListener{
            override fun onItemSelected(position: Int) {
                slide_model.get(position).imageUrl?.let { Log.d("TESTING", it) }
            }

        })
    }

}