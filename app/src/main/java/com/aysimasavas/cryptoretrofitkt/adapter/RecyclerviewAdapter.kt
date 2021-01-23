package com.aysimasavas.cryptoretrofitkt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aysimasavas.cryptoretrofitkt.R
import com.aysimasavas.cryptoretrofitkt.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerviewAdapter(private val cryptoList:ArrayList<CryptoModel>,private val listener:Listener) : RecyclerView.Adapter<RecyclerviewAdapter.RowHolder>() {


    private val colors: Array<String> = arrayOf("#9575cd","#7986cb","#64b5f6","#4fc3f7","#4dd0e1")



    interface Listener{

        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder (view: View): RecyclerView.ViewHolder(view) {


        fun bind(cryptoModel: CryptoModel,colors:Array<String>,position: Int,listener:Listener)
        {
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }




            itemView.example_card_view.setCardBackgroundColor(Color.parseColor(colors[position % 5]))
            //temView.setBackgroundColor(Color.parseColor(colors[position % 5]))
            itemView.text_currency.text=cryptoModel.currency
            itemView.text_price.text=cryptoModel.price

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerviewAdapter.RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)

        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerviewAdapter.RowHolder, position: Int) {

        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}

