package com.example.weather_kotlin.ui.view.content_provider

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmenContentProviderBinding
import java.lang.Thread.sleep

class ContentProviderFragment : Fragment() {
    private var _binding: FragmenContentProviderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmenContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContentProviderFragment()

        const val REQUEST_CODE = 1
    }

    // Проверяем, разрешено ли чтение контактов
    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    //Доступ к контактам на телефоне есть
                    getContacts()
                }
                //Опционально: если нужно пояснение перед запросом разрешений
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    context?.let { showRatio(it) }
                }
                else -> {
                    //Запрашиваем разрешение
                    requestPermission()
                }
            }
        }
    }

    private fun getContacts() {

        val output = StringBuffer()
        context?.let {
            // Получаем ContentResolver у контекста
            val contentResolver: ContentResolver = it.contentResolver
            // Отправляем запрос на получение контактов и получаем ответ в виде Cursor
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor
                    if (cursor.moveToPosition(i)) {
                      // Берём из Cursor столбец с именем

                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        output.append("\n Имя: $name")

                        val id =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                        output.append("\n id: $id")

                        val phoneNumber =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        output.append("\n Номер: $phoneNumber")

                    }
                }
                output.append("\n")
                addView(it, output)

            }
            cursorWithContacts?.close()
        }

    }

    private fun addView(context: Context, textToShow: StringBuffer) {
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.main_container_text_size)
            Thread{
                sleep(3000)
                binding.containerForContacts.pick()
            }.start()

        })

    }

    private val requestCode: Int = 23
    private lateinit var onFailure: (Throwable) -> Unit
    fun LinearLayout.pick() {
        try {
            Intent().apply {
                data = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                action = Intent.ACTION_PICK
                startActivityForResult(this, requestCode)
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }


    // Обратный вызов после получения разрешений от пользователя

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                // Проверяем, дано ли пользователем разрешение по нашему запросу
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    getContacts()
                } else {
                    // Поясните пользователю, что экран останется пустым, потому что доступ к контактам не предоставлен
                    context?.let { showRatio(it) }
                }
                return
            }
        }
    }

    private fun showRatio(context: Context) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Доступ к контактам")
                .setMessage("Экран останется пустым, потому что доступ к контактам не предоставлен")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    requestPermission()
                }
                .setNegativeButton("Закрыть") { dialog, _ ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .create()
                .show()
        }
    }


    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), Companion.REQUEST_CODE)
    }
}





