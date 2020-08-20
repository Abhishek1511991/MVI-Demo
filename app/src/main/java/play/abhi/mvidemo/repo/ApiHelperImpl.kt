package play.abhi.mvidemo.repo

import play.abhi.mvidemo.model.User

class ApiHelperImpl(private val apiService: ApiService):ApiHelper {
    override suspend fun getUsers(): List<User> {
       return apiService.getUsers()
    }
}