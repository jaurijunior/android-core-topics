package br.com.android.core.topics.intent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R
import br.com.android.core.topics.databinding.ActivityIntentsBinding
import br.com.android.core.topics.intent.common.CommonIntentsActivity
import br.com.android.core.topics.intent.explicit.ExplicitIntentActivity
import br.com.android.core.topics.intent.implicit.ImplicitActivity

class IntentsActivity : BaseActivity() {

    private lateinit var binding: ActivityIntentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(title = R.string.intents_and_intent_filters)

        onActionButton()

    }

    private fun onActionButton() {
        with(binding) {
            /**
             * Example explicit intent.
             * https://developer.android.com/guide/components/intents-filters?hl=en#ExampleExplicit
             */
            explicitIntentButtonIntents.setOnClickListener {
                startActivity(Intent(baseContext, ExplicitIntentActivity::class.java))
            }

            /**
             * Example implicit intent.
             * https://developer.android.com/guide/components/intents-filters?hl=en#ExampleSend
             */
            implicitIntentButtonIntents.setOnClickListener {
                showImplictIntentExample()
            }

            /**
             * Common Intents.
             * https://developer.android.com/guide/components/intents-common?hl=en
             */
            commonIntentsButtonIntents.setOnClickListener {
                startActivity(Intent(baseContext, CommonIntentsActivity::class.java))
            }
        }
    }

    private fun showImplictIntentExample() {
        // Create the text message with a string.
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hello, I'm Implicit Intent")
            type = "text/plain"
        }

        // Try to invoke the intent.
        try {
            startActivity(sendIntent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }
    }

}
