package play.abhi.mvidemo.repo

import play.abhi.mvidemo.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}