package br.com.android.core.topics.service

import android.content.Intent
import android.os.Bundle
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R
import br.com.android.core.topics.databinding.ActivityServiceSampleBinding

class ServiceSampleActivity : BaseActivity() {

    private lateinit var binding: ActivityServiceSampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(title = R.string.services)

        with(binding) {
            btnStartService.setOnClickListener {
                Intent(baseContext, ServiceSample::class.java).also {
                    startService(it)
                    textService.text = "Service Running"
                }
            }

            btnStopService.setOnClickListener {
                Intent(baseContext, ServiceSample::class.java).also {
                    stopService(it)
                    textService.text = "Service Stopping"
                }
            }

            btnSendData.setOnClickListener {
                Intent(baseContext, ServiceSample::class.java).also {
                    val dataString = etSendData.text.toString()
                    it.putExtra(SERVICE_DATA_KEY, dataString)
                    startService(it)
                }
            }
        }
    }

    companion object {
        const val SERVICE_DATA_KEY = "service_data_key"
    }

}
