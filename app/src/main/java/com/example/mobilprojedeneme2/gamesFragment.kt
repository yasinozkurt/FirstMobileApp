package com.example.mobilprojedeneme2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilprojedeneme2.Service.RetrieveData


private lateinit var recyclerView: RecyclerView
var gameId :ArrayList<Int> = ArrayList()
var gameList:ArrayList<String> = ArrayList()
var score:ArrayList<String>  = ArrayList()
var genre:ArrayList<String>  = ArrayList()
var images:ArrayList<String>  = ArrayList()
var description:String?=""

lateinit var adapter: RecyclerAdapter

class gamesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {






        val view=  inflater.inflate(R.layout.fragment_games, container, false)
        //Some UI changes below:
        activity?.findViewById<TextView>(R.id.textView7)?.text  ="GAMES"







        val layoutManager= LinearLayoutManager(context)

        recyclerView= view.findViewById(R.id.recyclerViewGames)





        recyclerView.layoutManager=layoutManager


        adapter=  RecyclerAdapter(gameList,score,genre,images)


        recyclerView.adapter= adapter
        adapter.setOnItemClickListener(object: RecyclerAdapter.onItemClickListener{

            val rd=RetrieveData()


            override fun onItemClick(position: Int) {
                val intent:Intent= Intent(context,DetailsScreenActivity::class.java)
                intent.putExtra("Name",gameList.get(position))
                intent.putExtra("Genre",genre.get(position))
                intent.putExtra("Score",score.get(position))
                println(gameId.get(position))
                rd.retrieveDescription(gameId.get(position)) // içinde description stringi değitirildi

                intent.putExtra("Description", description)  // BURADA OYUNA ÖZEL DESCRİPTİON İSTEĞİ ATILMALI!!!!
                intent.putExtra("Image",images.get(position))
                startActivity(intent)

            }




        })
        var loading:Boolean=true
        var visibleItemCount:Int
        var pastVisibleItems:Int
        var totalItemCount:Int

        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx:Int, dy:Int){

                visibleItemCount=layoutManager.childCount
                totalItemCount=layoutManager.itemCount
                pastVisibleItems=layoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = false;
                        Log.v("...", "Last Item Wow !");
                        // Do pagination.. i.e. fetch new data
                        //Toast.makeText( context,"onpagelistener çalşt", Toast.LENGTH_SHORT).show()

                        //-------------------------------
                        //-------------------------------
                        var handler= object:OnGetDataHandler {
                            override fun onGetDataSuccess(
                                gameIdL: ArrayList<Int>,
                                gameL: ArrayList<String>,
                                scoreL: ArrayList<String>,
                                genreL: ArrayList<String>,
                                imagesL: ArrayList<String>
                            ) {


                                gameList+=gameL
                                score+=scoreL
                                genre+=genreL
                                images+=imagesL
                                gameId+=gameIdL



                            }

                            override fun onGetDataFailure() {

                                Toast.makeText(getContext(),"bir şey oldu",Toast.LENGTH_SHORT).show()

                            }

                        }
                        var rd=RetrieveData()
                        page=page+1
                        rd.retrieveGame(handler,page)






                        //-------------------------------
                        //-------------------------------





                        loading = true;


                    }
                }




            }



        })










        return view
    }


    
    


}