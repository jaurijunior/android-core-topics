package br.com.android.core.topics.intent.common

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import br.com.android.core.topics.BaseActivity
import br.com.android.core.topics.R
import br.com.android.core.topics.databinding.ActivityContactsBinding

/* https://developer.android.com/guide/components/intents-common#Contacts */
class ContactsActivity : BaseActivity() {

    private lateinit var binding: ActivityContactsBinding

    var contactUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(title = R.string.contacts)

        binding.selectContactButton.setOnClickListener {
            selectContact()
        }

        binding.viewContactButton.setOnClickListener {
            contactUri?.let {
                viewContact(it)
            }
        }
        binding.editContactButton.setOnClickListener {
            contactUri?.let {
                editContact(it, email = "email@test.com")
            }
        }
        binding.insertContactButton.setOnClickListener {
            contactUri?.let {
                insertContact(name = "User Test", email = "email@test.com")
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            // Get the URI and query the content provider for the phone number
            contactUri = data?.data
            val projection: Array<String> = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)

            contentResolver.query(contactUri!!, projection, null, null, null).use { cursor ->
                // If the cursor returned is valid, get the phone number
                if (cursor!!.moveToFirst()) {
                    val contactIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val name = cursor.getString(contactIndex)

                    binding.contactsPeople.text = name

                }
            }
        }
    }

    private fun selectContact() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT)
        }  else {
            Toast.makeText(baseContext, R.string.action_not_suported, Toast.LENGTH_LONG).show()
        }
    }

    private fun viewContact(contactUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, contactUri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }  else {
            Toast.makeText(baseContext, R.string.action_not_suported, Toast.LENGTH_LONG).show()
        }
    }

    private fun editContact(contactUri: Uri, email: String) {
        val intent = Intent(Intent.ACTION_EDIT).apply {
            data = contactUri
            putExtra(ContactsContract.Intents.Insert.EMAIL, email)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(baseContext, R.string.action_not_suported, Toast.LENGTH_LONG).show()
        }
    }

    private fun insertContact(name: String, email: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            type = ContactsContract.Contacts.CONTENT_TYPE
            putExtra(ContactsContract.Intents.Insert.NAME, name)
            putExtra(ContactsContract.Intents.Insert.EMAIL, email)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(baseContext, R.string.action_not_suported, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val REQUEST_SELECT_CONTACT = 1
    }

}
