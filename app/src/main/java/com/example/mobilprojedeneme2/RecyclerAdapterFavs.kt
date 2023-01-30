package com.example.mobilprojedeneme2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerAdapterFavs(private val gameList: ArrayList<String?>,private val score:ArrayList<String?>,private val genre:ArrayList<String?>,private val images:ArrayList<String?>) : RecyclerView.Adapter<RecyclerAdapterFavs.FavGamesVH>(){












    class FavGamesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById<TextView>(R.id.textView)
        val genre: TextView =itemView.findViewById<TextView>(R.id.textView2)
        val score: TextView =itemView.findViewById<TextView>(R.id.textView3)
        val image:ImageView=itemView.findViewById<ImageView>(R.id.imageRec)








    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavGamesVH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)










        return FavGamesVH(itemView)


    }

    override fun onBindViewHolder(holder: FavGamesVH, position: Int) {

        holder.title.text=gameList?.get(position)
        holder.genre.text=genre?.get(position)
        holder.score.text=score?.get(position)

        Picasso.get().load(images.get(position)!!).into(holder.image)












    }



    override fun getItemCount(): Int {
        if (gameList?.size ===0){

            return 0
        }
        else{
            return gameList!!.size

        }





    }





}