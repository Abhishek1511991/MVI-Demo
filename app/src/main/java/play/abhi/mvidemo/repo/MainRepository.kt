package play.abhi.mvidemo.repo

class MainRepository(private val apiHelper: ApiHelper)  {

    suspend fun getUsers() = apiHelper.getUsers() //2
}