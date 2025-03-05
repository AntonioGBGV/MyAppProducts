package com.example.myappproducts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myappproducts.databinding.ItemproductBinding
import com.example.myappproducts.model.Product
import com.squareup.picasso.Picasso

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemproductBinding.bind(view)

    fun bind(product: Product){
        Picasso.get().load(product.image).into(binding.ivProduct)
        binding.tvName.text = "Producto: " + product.name
        binding.tvDescription.text = "Descripción: " + product.description
        binding.tvPrice.text = "Precio: $" + product.price.toString()
        binding.tvCategory.text = "Categoría: " + product.category
    }
}