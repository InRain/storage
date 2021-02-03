package com.learn.storage


import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.learn.storage.databinding.ActivityMainBinding
import com.learn.storage.db.RecordDatabase
import com.learn.storage.db.TheRecord


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE)
        setContentView(binding.root)

        binding.saveToPrefs.setOnClickListener {
            val editor = prefs.edit()
            editor.putString(PREF_VALUE, binding.editTextValue.text.toString())
            editor.apply()
            binding.editTextValue.text.clear()
        }

        binding.loadFromPrefs.setOnClickListener {
            if (prefs.contains(PREF_VALUE)) {
                binding.editTextValue.setText(prefs.getString(PREF_VALUE, ""))
            }
        }

        val internalStorageManager = InternalStorageManager(this)

        binding.saveInternal.setOnClickListener {
            internalStorageManager.save(binding.editTextValue.text.toString())
            binding.editTextValue.text.clear()
        }

        binding.loadInternal.setOnClickListener {
            binding.editTextValue.setText(internalStorageManager.load())
        }

        val externalStorageManager = ExternalStorageManager(this)

        binding.saveExternal.setOnClickListener {
            externalStorageManager.writeExternal(binding.editTextValue.text.toString())
            binding.editTextValue.text.clear()
        }

        binding.loadExternal.setOnClickListener {
            binding.editTextValue.setText(externalStorageManager.readExternal())
        }

        val dataBase = Room.databaseBuilder(this, RecordDatabase::class.java, "database")
            .allowMainThreadQueries().build()
        val recordDao = dataBase.recordDao()

        binding.saveToDb.setOnClickListener {
            val record = TheRecord(1, binding.editTextValue.text.toString())
            recordDao.deleteAll()
            recordDao.insert(record)
            binding.editTextValue.text.clear()
        }

        binding.loadFromDb.setOnClickListener {
            binding.editTextValue.setText(recordDao.getById(1).value)
        }
    }

    companion object {
        private const val APP_PREF_NAME = "MY_SETTINGS"
        private const val PREF_VALUE = "VALUE"
        private const val PERMISSION_REQUEST_CODE = 10001
    }
}