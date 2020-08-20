package play.abhi.mvidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import play.abhi.mvidemo.model.MainViewModel
import play.abhi.mvidemo.model.User
import play.abhi.mvidemo.model.ViewModelFactory
import play.abhi.mvidemo.repo.ApiHelperImpl
import play.abhi.mvidemo.repo.RetrofitBuilder
import play.abhi.mvidemo.utility.MainIntent
import play.abhi.mvidemo.utility.MainState

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        observeViewModel()
        setupClicks()
    }
    //1
    private fun setupViewModel()
    {
        mainViewModel = ViewModelProviders.of(this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }
    //5
    private fun observeViewModel()
    {
        lifecycleScope.launch {

            mainViewModel.state.collect {
                //9
                when (it) {
                    is MainState.Idle -> {
                    }
                    is MainState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users ->
                    {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error ->
                    {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error,
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    private fun renderList(users: List<User>)
    {
        Log.e("","")
    }

    private fun setupClicks()
    {
        buttonFetchUser.setOnClickListener(View.OnClickListener {

            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser) //6

            }
        })
    }
}