package play.abhi.mvidemo.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import play.abhi.mvidemo.repo.MainRepository
import play.abhi.mvidemo.utility.MainIntent
import play.abhi.mvidemo.utility.MainState

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository):ViewModel() {
    //3
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState> get() = _state
    init {
        handleIntent()
    }//4
    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchUser -> fetchUser() //7
                }
            }
        }
    }
    private fun fetchUser()
    {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Users(repository.getUsers()) //8
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }

        }
    }
}