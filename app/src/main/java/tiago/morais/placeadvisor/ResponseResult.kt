package tiago.morais.placeadvisor


     class Result(
        val fsq_id : String,
        val name : String
    )

     class ResponseResult(
        val results : List<Result>
    )
