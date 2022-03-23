package com.g2academy.training

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.g2academy.training.adapters.ProductAdapter
import com.g2academy.training.models.Product
import com.g2academy.training.models.ProductsItem
import com.g2academy.training.models.ResponseProduct
import com.g2academy.training.networks.AllproductsInterface
import com.g2academy.training.networks.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ProductFragment : Fragment() {

    lateinit var listview_product : ListView
    var productList : ArrayList<Product> = ArrayList()
    lateinit var fab_add : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listview_product = view.findViewById(R.id.listview_product)
        fab_add = view.findViewById(R.id.fab_add)
        getallproducts()
        listview_product.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(context,productList[id.toInt()].name.toString(),Toast.LENGTH_SHORT).show()
            }
        }

        listview_product.onItemLongClickListener = object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {
                val ad = AlertDialog.Builder(context)
                ad.setTitle("Are u Sure?")
                ad.setMessage("Product will be deleted?")
                ad.setIcon(R.drawable.ic_trash)
                ad.setPositiveButton("Yes"){
                    dialog, which ->
//                    CALL FUNCTION DELETE
                }
                ad.setNegativeButton("Cancel"){
                        dialog, which ->
//                    CALL FUNCTION DELETE
                }
                ad.show()
                return true;
            }

        }

        fab_add.setOnClickListener{
            startActivity(Intent(context,AddProductActivity::class.java))
        }
    }

    fun getallproducts(){
        try{
            val pd = ProgressDialog(context)
            pd.setTitle("Please Wait")
            pd.setMessage("Mengambil Data...")
            pd.setIcon(R.drawable.ic_timer)
            pd.show()
            val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(AllproductsInterface::class.java)
            retrofitInstance.allproducts().enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable){
                    Toast.makeText(context,t.message,Toast.LENGTH_LONG).show()
                    pd.hide()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    pd.hide()
                    if(response.code() == 200 && response.isSuccessful){
                        val responseBody = JSONObject(response.body()?.string())
                        var gson = Gson()
                        productList = gson.fromJson(responseBody["products"].toString(),
                            Array<Product>::class.java).toList() as ArrayList<Product>
                        activity?.runOnUiThread({
                            val myAdapter : ProductAdapter
                            myAdapter = ProductAdapter(requireContext(),productList)
                            listview_product.adapter = myAdapter
                        })
                       // Toast.makeText(context,productList.toString(),Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(context,response.message().toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        catch (e:Exception){

        }
    }
}