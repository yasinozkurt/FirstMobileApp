package com.example.mobilprojedeneme2

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

val favsNameList=ArrayList<String?>()
val favsGenreList=ArrayList<String?>()
val favsScoreList=ArrayList<String?>()
val favsImageList=ArrayList<String?>()
class DetailsScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_screen)
        //butonlar düzelmedi xml içinde:
        val cdGamesButton=findViewById<Button>(R.id.button)
        val cdBackButton=findViewById<ImageView>(R.id.imageView2)
        val cdAddFavsButton=findViewById<Button>(R.id.button2)

        supportActionBar?.hide()
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
        ////////////////////////////////////////////////



        val heading=findViewById<TextView>(R.id.textView8)
        val description= findViewById<TextView>(R.id.descriptionText)
        val BigImgView=findViewById<ImageView>(R.id.imageView)


        val bundle: Bundle?=intent.extras

        val title=bundle!!.getString("Name")
        val descrpt=bundle!!.getString("Description")
        val score=bundle!!.getString("Score")
        val genre=bundle!!.getString("Genre")
        val image=bundle!!.getString("Image")



        //Setting content:

        heading.text=title
        description.text=descrpt
        println("bura ÇALIŞTIIIII" + descrpt + "dfsvfd")

        Picasso.get().load(image).into(BigImgView)

        cdGamesButton.setOnClickListener{

            finish()
        }
        cdBackButton.setOnClickListener{

            finish()
        }

        //Sharedpreference da tutulmalı ve hatta data class kullanılmalı ama şimdi bodoslama yapıyoruz



        //Favsa ekleme yapılınca intent ile favs sekmesine gidiyor ve fava eklenen oyunu yolluyor
        cdAddFavsButton.setOnClickListener{
            if (favsNameList.contains(bundle!!.getString("Name"))){
                Toast.makeText(this,"Already added!",Toast.LENGTH_SHORT).show()

            }
            else{
                favsNameList.add(bundle!!.getString("Name"))
                favsGenreList.add(bundle!!.getString("Genre"))
                favsScoreList.add(bundle!!.getString("Score"))
                favsImageList.add(bundle!!.getString("Image"))
                Toast.makeText(this,"Added to the Favorites",Toast.LENGTH_SHORT).show()
                cdAddFavsButton.text="Favorited"


            }








        }






    }
}