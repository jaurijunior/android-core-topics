package br.com.android.core.topics.intent.explicit

import android.os.Bundle
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R

class ExplicitIntentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit_intent)
        setupToolbar(title = R.string.explicit_intent)
    }
}