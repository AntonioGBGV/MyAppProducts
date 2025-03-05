package com.example.myappproducts

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappproducts.databinding.ActivityMainBinding
import com.example.myappproducts.model.Product
import com.example.myappproducts.model.ProductResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRecyclerView()
        getProducts()
    }

    private fun initRecyclerView() {
        adapter = ProductAdapter(productList)
        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = adapter
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ProductResponse> = getRetrofit()
                .create(ApiService::class.java).getAllProducts()

            val productsResponse: ProductResponse? = call.body()

            runOnUiThread {
                if (call.isSuccessful && productsResponse != null) {
                    val productListFromApi = productsResponse.results
                    productList.clear()
                    productList.addAll(productListFromApi)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}
