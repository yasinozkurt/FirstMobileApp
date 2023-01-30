package com.example.mobilprojedeneme2.Service

data class Game(

    var id:Int,
    var name:String,
    var background_image:String,
    var metacritic:String,
    var genres:List<Genre>



)

data class Genre(
    var name:String

)


data class GameDetail(
    var description:String,
    var name_original:String


)


data class SearchResult(

    var results:List<Game>

        )


