package play.abhi.mvidemo.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import play.abhi.mvidemo.repo.ApiHelper
import play.abhi.mvidemo.repo.MainRepository

@ExperimentalCoroutinesApi
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory  {
    //2
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
        {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}