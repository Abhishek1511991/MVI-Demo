package play.abhi.mvidemo.repo

import play.abhi.mvidemo.model.User

interface ApiHelper {
    suspend fun getUsers(): List<User> //10
}