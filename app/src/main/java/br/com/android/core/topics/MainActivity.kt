package br.com.android.core.topics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.android.core.topics.databinding.ActivityMainBinding
import br.com.android.core.topics.intent.IntentsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickButton()

    }

    private fun onClickButton() {
        with(binding) {
            intentButtonMain.setOnClickListener {
                startActivity(Intent(baseContext, IntentsActivity::class.java))
            }
        }

    }

}
