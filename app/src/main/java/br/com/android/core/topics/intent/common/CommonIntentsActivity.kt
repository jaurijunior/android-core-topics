package br.com.android.core.topics.intent.common

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R
import br.com.android.core.topics.databinding.ActivityCommonIntentsBinding
import com.google.android.gms.actions.NoteIntents
import java.util.*

class CommonIntentsActivity : BaseActivity() {

    private lateinit var binding: ActivityCommonIntentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonIntentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(title = R.string.common_intents)


        with(binding) {

            alarmClockButton.setOnClickListener {
                createAlarm(message = "Study Android", hour = 15, minutes = 50)
            }

            timerButton.setOnClickListener {
                startTimer(message = "Runnig", seconds = 30)
            }

            calendarEventButton.setOnClickListener {
                val today = Calendar.getInstance().time.time
                val tomorrow = today.plus(1000 * 60 * 60 * 24)
                addCalendarEvent(
                    title = "Android Dev Summit",
                    location = "Online",
                    begin = today,
                    end = tomorrow
                )
            }

            contactsViewButton.setOnClickListener {
                startActivity(
                    Intent(
                        baseContext,
                        ContactsActivity::class.java
                    )
                )
            }

            emailButton.setOnClickListener {
                composeEmail(addresses = arrayOf("test@email.com"), subject = "Test email")
            }

            fileStorageButton.setOnClickListener {
                selectImage()
            }

            mapsButton.setOnClickListener {
                val mapUri = Uri.parse("geo:37.4118539,-122.0923756,14")
                showMap(mapUri)
            }

            newNoteButton.setOnClickListener {
                createNote(subject = "Android", text = "Android Developer Note")
            }

            phoneButton.setOnClickListener {
                dialPhoneNumber(phoneNumber = "123456789")
            }

            searchButton.setOnClickListener {
                searchWeb(query = "Android Developer")
            }

            settingsButton.setOnClickListener {
                openWifiSettings()
            }

            webBrowserButton.setOnClickListener {
                openWebPage(url = "https://developer.android.com/")
            }

            textMessagingButton.setOnClickListener {
                composeMmsMessage("Android, sms", Uri.parse("content://sms/inbox"))
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GET && resultCode == Activity.RESULT_OK) {
            // val thumbnail: Bitmap = data?.getParcelableExtra("data")
            val fullPhotoUri: Uri? = data?.data
            fullPhotoUri?.let { uri ->
                Log.i(TAG, "fullPhotoUri: $uri")
            }
            // Do work with photo saved at fullPhotoUri
            //...
        }
    }



    // https://developer.android.com/guide/components/intents-common?hl=en#CreateAlarm
    private fun createAlarm(message: String, hour: Int, minutes: Int) {
        AlarmClock.ACTION_SET_ALARM
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common?hl=en#CreateTimer
    private fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        openIntent(intent)
    }


    // https://developer.android.com/guide/components/intents-common?hl=en#Calendar
    private fun addCalendarEvent(title: String, location: String, begin: Long, end: Long) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = Events.CONTENT_URI
            putExtra(Events.TITLE, title)
            putExtra(Events.EVENT_LOCATION, location)
            // The start and end time of the event (milliseconds since epoch).
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end)
        }
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common#Email
    private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common#Storage
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET)
        }
    }

    // https://developer.android.com/guide/components/intents-common#Maps
    private fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
        openIntent(intent)
    }

    //https://developer.android.com/guide/components/intents-common#NewNote
    private fun createNote(subject: String, text: String) {
        val intent = Intent(NoteIntents.ACTION_CREATE_NOTE).apply {
            putExtra(NoteIntents.EXTRA_NAME, subject)
            putExtra(NoteIntents.EXTRA_TEXT, text)
        }
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common#Phone
    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        openIntent(intent)
    }


    // https://developer.android.com/guide/components/intents-common#Search
    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common#Settings
    private fun openWifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        openIntent(intent)
    }

    // https://developer.android.com/guide/components/intents-common#Browser
    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        openIntent(intent)
    }


    private fun openIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(baseContext, R.string.action_not_suported, Toast.LENGTH_LONG).show()
        }
    }

    private fun composeMmsMessage(message: String, attachment: Uri) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            type = "text/plain"
            data = Uri.parse("smsto:")
            putExtra("sms_body", message)
            putExtra(Intent.EXTRA_STREAM, attachment)
        }
        openIntent(intent)
    }


    companion object {
        private const val REQUEST_IMAGE_GET  = 105
        private const val TAG = "CommonIntentsActivity"
    }

}


