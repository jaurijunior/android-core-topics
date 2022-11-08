package br.com.android.core.topics.intent.implicit

import android.os.Bundle
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R


class ImplicitActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implicit)
        setupToolbar(title = R.string.implicit_intent)

    }
}