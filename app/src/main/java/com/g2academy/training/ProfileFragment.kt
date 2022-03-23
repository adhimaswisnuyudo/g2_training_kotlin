package com.g2academy.training

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.g2academy.training.networks.Myconst.Companion.SP_LOGINDATA
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    lateinit var iv_photoprofile : ImageView
    lateinit var txt_username : EditText
    lateinit var txt_fullname : EditText
    lateinit var btn_editprofile : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_fullname = view.findViewById(R.id.txt_fullname)
        txt_username = view.findViewById(R.id.txt_username)
        btn_editprofile = view.findViewById(R.id.btn_editprofile)
        iv_photoprofile = view.findViewById(R.id.iv_photoprofile)
        getSharedprefData()

        btn_editprofile.setOnClickListener {
            val sp = this.activity?.getSharedPreferences(SP_LOGINDATA,MODE_PRIVATE)
            val editor = sp?.edit()
            editor?.putString("username",txt_username.text.toString())
            editor?.putString("fullname",txt_fullname.text.toString())
            editor?.commit()

            Toast.makeText(context,"New Fullname : "
                    +sp?.getString("fullname","Unavailable"),Toast.LENGTH_SHORT).show()
            getSharedprefData()
        }
    }

    fun getSharedprefData(){
        val sp = this.activity?.getSharedPreferences(SP_LOGINDATA,MODE_PRIVATE)
        txt_fullname.setText(sp?.getString("fullname","Unavailable"))
        txt_username.setText(sp?.getString("username","Unavailable"))
        Picasso.get()
            .load(sp?.getString("photo","-"))
            .resize(30,30)
            .placeholder(R.drawable.ic_brokenimage)
            .into(iv_photoprofile)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity)!!.supportActionBar!!.show()
        (activity as AppCompatActivity)!!.supportActionBar!!.setTitle("Profile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if(id==R.id.menu_editprofile){
            Log.d("TESTING","Pindah ke halaman edit profile")
            return true
        }
        else if (id==R.id.menu_logout){
            preparetoLogout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun preparetoLogout(){
        val builder = AlertDialog.Builder(this.requireContext())
        builder.setTitle("Peringatan")
        builder.setMessage("Yakin Akan Logout Aplikasi?")
        builder.setPositiveButton("Ya"){
                _, _-> (activity as MainActivity)!!.logout()
        }
        builder.setNegativeButton("Tidak"){
                _, _ ->null
        }
        builder.show()
    }

}