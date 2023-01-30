package com.example.mobilprojedeneme2

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var recyclerView: RecyclerView
var dataCount:Int=0

class favsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_favs, container, false)



        val layoutManager= LinearLayoutManager(context)
        recyclerView= view.findViewById(R.id.recyclerViewFavs)
        recyclerView.layoutManager=layoutManager
        val adapter=  RecyclerAdapterFavs(favsNameList, favsScoreList, favsGenreList, favsImageList)
        recyclerView.adapter= adapter


        //Swipe kısmı:
        //*********************************
        var builder:AlertDialog.Builder=AlertDialog.Builder(requireActivity())


        //itemtouchhelper adlı classtan oluşturduğumuz swiptetodelete abstract classını burada bir val'a object olarak veriyoruz
        //onswipe methodunu override edip  ItemTouchHelper methoduna bu objeyi verip recyclerviewa bağlıyoruz
        val swipteToDeleteCallback=object:SwipeToDeleteCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                builder.setTitle("Alert!")
                    .setMessage("Do you want to remove it from favorites")
                    .setCancelable(true)
                    .setPositiveButton("yes"){ dialogInterface: DialogInterface, i: Int ->
                        val position=viewHolder.absoluteAdapterPosition
                        favsImageList.removeAt(position)
                        favsNameList.removeAt(position)
                        favsGenreList.removeAt(position)
                        favsScoreList.removeAt(position)
                        recyclerView.adapter?.notifyItemRemoved(position)

                        //Aynı fragmanı baştan yüklemeyi deniyorum, böylece silinen veri  varsa güncel fragman yüklenecek ve ui değişmiş olacak:
                        //*****************************************
                        val fF=favsFragment()

                        val fragmentManager=parentFragmentManager
                        val fragmentTransaction=fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.frameLayout,fF)
                        fragmentTransaction.commit()





                    }
                    .setNegativeButton("no"){ dialogInterface: DialogInterface, i: Int ->
                        adapter.notifyDataSetChanged()

                        dialogInterface.dismiss()

                    }
                    .show()



                //update etmiyor işe yaramıyor çünkü fragman açılıp kapanıınca çalışıyor, etkisiz
                if(dataCount==0){
                    //şimdi burada boş recyclerview üzerine no data found textviewi eklemeliyiz
                    view.findViewById<TextView>(R.id.textViewNoData).visibility=View.VISIBLE


                    requireActivity().findViewById<TextView>(R.id.textView7)?.text  ="FAVORITES"}
                else{
                    activity?.findViewById<TextView>(R.id.textView7)?.text  ="FAVORITES"+" ("+ dataCount.toString()+")"
                    view.findViewById<TextView>(R.id.textViewNoData).visibility=View.INVISIBLE



                }





            }

        }
        val itemTouchHelper=ItemTouchHelper(swipteToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //*********************************





         fun updateFavCount(){
            //favoritesda eleman yoksa bu var ile göreceğiz
            dataCount = adapter.itemCount
            //Some UI changes below:
            if(dataCount==0){
                //şimdi burada boş recyclerview üzerine no data found textviewi eklemeliyiz
                view.findViewById<TextView>(R.id.textViewNoData).visibility=View.VISIBLE


                activity?.findViewById<TextView>(R.id.textView7)?.text  ="FAVORITES"}
            else{
                activity?.findViewById<TextView>(R.id.textView7)?.text  ="FAVORITES"+" ("+ dataCount.toString()+")"
                view.findViewById<TextView>(R.id.textViewNoData).visibility=View.INVISIBLE


            }

        }
        updateFavCount()

















        return view
    }







}