package tiago.morais.placeadvisor


    class Location (
        val address : String,
        val country : String,
        val formatted_address : String,
        val locality : String,
        val postcode : String,
        val region : String
            )

    class Icon(
        val prefix : String,
        val suffix : String
            )

    class Categories(
        val id : Int,
        val name : String,
        val icon: Icon
    )


    class ResponseResultDetails(
        val fsq_id : String,
        val name : String,
        val location: Location,
        val categories: List<Categories>
    )