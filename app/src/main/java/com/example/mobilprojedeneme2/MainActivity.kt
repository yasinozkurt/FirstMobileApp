package com.example.mobilprojedeneme2

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilprojedeneme2.Service.RetrieveData
import com.google.android.material.navigation.NavigationBarMenu
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


public lateinit var  sView:SearchView

public var page:Int=1

// GAMES FRAGMENT ONCLİCK METHODUNU NASIL ÇAĞIRABİLİRİM SOR


class MainActivity : AppCompatActivity() {


    var page:Int=0



    fun getContext():Context{
        return applicationContext
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        var rd:RetrieveData= RetrieveData()


        var gamesButton:ImageView=findViewById(R.id.gamesButton)
        var favsButton:ImageView=findViewById(R.id.favsButton)

        var gamesTextView:TextView=findViewById(R.id.textView4)


        gamesButton.setImageResource(R.drawable.vector)

        favsButton.setImageResource(R.drawable.icon)
        gamesTextView.setTextColor(Color.parseColor("#1064BC"))


        gamesFragment()








        //kopyala yapıştr kod alttaki
        @RequiresApi(Build.VERSION_CODES.R)
        fun hideSystemUI() {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(
                window,
                window.decorView.findViewById(android.R.id.content)
            ).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())

                // When the screen is swiped up at the bottom
                // of the application, the navigationBar shall
                // appear for some time
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }


        }
        hideSystemUI()


        //SearchView denemeleri:
        sView=findViewById(R.id.sView)


        sView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                sView.clearFocus()

                apifilterlist(rd.retrieveSearchedGames(p0!!))




                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //filterList(p0)
                return false
            }

        })




    }


    private fun apifilterlist(multipleList:ArrayList<ArrayList<String>>){
        val filterList:ArrayList<String>
        val scoreFiltered:ArrayList<String>
        val genreFiltered:ArrayList<String>
        val imagesFiltered:ArrayList<String>



        filterList=multipleList.get(0)
        scoreFiltered=multipleList.get(1)
        imagesFiltered=multipleList.get(2)
        genreFiltered=multipleList.get(3)






        adapter.setFilteredList(filterList,scoreFiltered,genreFiltered,imagesFiltered)




    }

    private fun filterList(p0: String?) {
        val filterList=ArrayList<String>()
        val scoreFiltered=ArrayList<String>()
        val genreFiltered=ArrayList<String>()
        val imagesFiltered=ArrayList<String>()

        if(p0!!.length>2){
            var count:Int=0
            for(item:String in gameList){

                if(item.lowercase().contains(p0.lowercase())){
                    if(!(filterList.contains(item)) ){
                        filterList.add(item)
                        scoreFiltered.add(score.get(count))
                        genreFiltered.add(genre.get(count))
                        imagesFiltered.add(images.get(count))



                    }

                }
                count++
            }
            if(filterList.isEmpty()){
                Toast.makeText(this,"No result found",Toast.LENGTH_SHORT).show()

            }
            else{
                adapter.setFilteredList(filterList,scoreFiltered,genreFiltered,imagesFiltered)

            }


        }
        else{
            adapter.setFilteredList(gameList, score,genre,images)
        }


    }

    override fun onStart() {
        //Games fragmentini koyarak başlıyoruz
        super.onStart()



        """val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        val gamesFragment=gamesFragment()

        fragmentTransaction.replace(R.id.frameLayout,gamesFragment).commit()"""

        // AZ ÖNCE VERİ ÇEKTİYSEK TEKRARA GEREK YOK KONTROLÜ
        if(gameList.size>0){


            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()

            val gamesFragment=gamesFragment()
            fragmentTransaction.replace(R.id.frameLayout,gamesFragment).commit()

        }
        else{
            var handler= object:OnGetDataHandler {
                override fun onGetDataSuccess(
                    gameIdL: ArrayList<Int>,
                    gameL: ArrayList<String>,
                    scoreL: ArrayList<String>,
                    genreL: ArrayList<String>,
                    imagesL: ArrayList<String>
                ) {


                    gameList=gameL
                    score=scoreL
                    genre=genreL
                    images=imagesL
                    gameId=gameIdL


                    val fragmentManager=supportFragmentManager
                    val fragmentTransaction=fragmentManager.beginTransaction()

                    val gamesFragment=gamesFragment()
                    fragmentTransaction.replace(R.id.frameLayout,gamesFragment).commit()


                }

                override fun onGetDataFailure() {

                    Toast.makeText(getContext(),"bir şey oldu",Toast.LENGTH_SHORT).show()

                }

            }
            var rd=RetrieveData()
            rd.retrieveGame(handler,1)

        }










    }
    override fun onResume(){
        super.onResume()
        var gamesTextV:TextView=findViewById(R.id.textView4)
        gamesTextV.setText("Games")


    }






    fun gamesFragment(view :View){

        //Button color change:
        //bunlar dışarıda tanımlanınca çalışmadı
        var gamesButton:ImageView=findViewById(R.id.gamesButton)
        var favsButton:ImageView=findViewById(R.id.favsButton)
        var gamesTextView:TextView=findViewById(R.id.textView4)
        var favsTextView:TextView=findViewById(R.id.textView5)
        gamesTextView.setTextColor(Color.parseColor("#1064BC"))
        favsTextView.setTextColor(Color.parseColor("#A3A3A3"))

        gamesButton.setImageResource(R.drawable.vector)

        favsButton.setImageResource(R.drawable.icon)



        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()

        val gamesFragment=gamesFragment()
        fragmentTransaction.replace(R.id.frameLayout,gamesFragment).commit()





    }


    fun favsFragment(view: View){

        //Button color change:
        var gamesButton:ImageView=findViewById(R.id.gamesButton)
        var favsButton:ImageView=findViewById(R.id.favsButton)
        var gamesTextView:TextView=findViewById(R.id.textView4)
        var favsTextView:TextView=findViewById(R.id.textView5)
        favsTextView.setTextColor(Color.parseColor("#1064BC"))
        gamesTextView.setTextColor(Color.parseColor("#A3A3A3"))
        gamesButton.setImageResource(R.drawable.vector2)

        favsButton.setImageResource(R.drawable.icon2)





        var fragmentManager=supportFragmentManager
        var fragmentTransaction=fragmentManager.beginTransaction()

        var favsFragment=favsFragment()











        fragmentTransaction.replace(R.id.frameLayout,favsFragment).commit()















        //İLK GELİŞTE ZATEN COUNT BİLMEDİĞİNDEN
        //if(favsFragment.getItemCount()===0){
          //  Toast.makeText(this,"Fav yok",Toast.LENGTH_SHORT).show()

        //}
        //else{
          //  Toast.makeText(this,"Fav var",Toast.LENGTH_SHORT).show()



        //}




    }





}