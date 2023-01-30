package com.example.mobilprojedeneme2.Service

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.example.mobilprojedeneme2.OnGetDataHandler
import com.example.mobilprojedeneme2.description
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrieveData {
     var gameIdL:ArrayList<Int> = ArrayList()
     var gameL:ArrayList<String> =ArrayList()
     var scoreL:ArrayList<String> =ArrayList()
     var genreL:ArrayList<String> =ArrayList()
     var descriptionL:ArrayList<String> =ArrayList()
     var imagesL:ArrayList<String> = ArrayList()





    //base url vererek PlaceHolderApi interfaceini gerçekledik ve o arayüzden türemiş bir sınıfa ait retrofit objesi yarattık
    var ApiCall:PlaceHolderApi=Retrofit.Builder()
    .baseUrl("https://api.rawg.io/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(PlaceHolderApi::class.java)







    fun retrieveGame(onGetHandler:OnGetDataHandler,page:Int){

        // call objesi oluşturduk
        val callForGames  = ApiCall.getGames(page = page.toString())


        //call objesine parametre olarak Callback interfaceini anonim obje olarak gerçekleyip verdik, enqueue ile asenkron bir istekte bulunduk.

        callForGames.enqueue(object:Callback<Games>{
            override fun onResponse(call: Call<Games>, response: Response<Games>) {
                println("istek başarılı")

                for(game in response.body()!!.results){

                    println("Game id: "+game.id+" --- "+game.name +"  --- " + game.background_image +"  ---  " + game.metacritic + "   ---  " + game.genres[0].name)
                    println(" ---------------------------------------------------- ")

                    gameL.add(game.name)
                    gameIdL.add(game.id)
                    scoreL.add(game.metacritic)
                    genreL.add(game.genres[0].name)
                    imagesL.add(game.background_image)

                }


                onGetHandler.onGetDataSuccess(gameIdL,gameL,scoreL,genreL,imagesL)





            }

            override fun onFailure(call: Call<Games>, t: Throwable) {
                println("istek başarısız oldu")
                println(t.message)

            }

        })

    }




    fun retrieveDescription(id:Int){

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here

            println("çalıştıııııııı")
            val callForDetail:Call<GameDetail> =ApiCall.getDetails(id)

            //description=callForDetail.execute().body()?.descript

            description=callForDetail.execute().body()?.description

            description= description!!.drop(3)
            println("d"+description)


        }




    }


    fun retrieveSearchedGames(str:String):ArrayList<ArrayList<String>>{
        val filterList=ArrayList<String>()
        val scoreFiltered=ArrayList<String>()
        val genreFiltered=ArrayList<String>()
        val imagesFiltered=ArrayList<String>()


        val callSearched:Call<SearchResult> =ApiCall.getSearched(str)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val results:List<Game> =callSearched.execute().body()!!.results
            for(i in 0..9){
                filterList.add(results[i].name)
                scoreFiltered.add(results[i].metacritic)
                imagesFiltered.add(results[i].background_image)
                genreFiltered.add("Action")

            }


        }

        return arrayListOf(filterList,scoreFiltered,imagesFiltered,genreFiltered)

    }

}