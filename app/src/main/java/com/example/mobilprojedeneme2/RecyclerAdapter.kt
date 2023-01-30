package com.example.mobilprojedeneme2

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.squareup.picasso.Picasso
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL


class RecyclerAdapter(private var gameList: ArrayList<String>, private var score:ArrayList<String>, private var genre:ArrayList<String>, private var images: ArrayList<String>) : RecyclerView.Adapter<RecyclerAdapter.GamesVH>(){

    /////////////deneme
    private lateinit var mListener:onItemClickListener

     interface  onItemClickListener {
         fun onItemClick(position:Int)
     }



    fun setOnItemClickListener(listener:onItemClickListener){
        mListener=listener
    }
    /////////////deneme







    class GamesVH(itemView: View,listener:onItemClickListener) :RecyclerView.ViewHolder(itemView) {

        val title: TextView= itemView.findViewById<TextView>(R.id.textView)
        val genre:TextView=itemView.findViewById<TextView>(R.id.textView2)
        val score:TextView=itemView.findViewById<TextView>(R.id.textView3)
        val image:ImageView=itemView.findViewById<ImageView>(R.id.imageRec)



        init {
            itemView.setOnClickListener {
                it.findViewById<LinearLayout>(R.id.recrow).setBackgroundColor(Color.WHITE)
                listener.onItemClick(absoluteAdapterPosition)




            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesVH {
        val itemView =LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)







        return GamesVH(itemView,mListener)


    }

    override fun onBindViewHolder(holder: GamesVH, position: Int) {

        holder.title.text=gameList.get(position)
        holder.genre.text=genre.get(position)
        holder.score.text=score.get(position)

        //Efsane bir kütüphane bu picasso tek satır kodla çözdü işi
        Picasso.get().load(images.get(position)).into(holder.image)
















    }





    override fun getItemCount(): Int {

        return gameList.size




    }
    fun setFilteredList(filteredList:ArrayList<String>,scoreFiltered:ArrayList<String>,  genreFiltered:ArrayList<String>, imagesFiltered:ArrayList<String>){
        this.gameList=filteredList
        this.score=scoreFiltered
        this.genre=genreFiltered
        this.images=imagesFiltered

        notifyDataSetChanged()

    }




}