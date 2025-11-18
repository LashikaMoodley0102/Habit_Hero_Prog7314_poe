package student.projects.habithero_prog

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        // Load saved language BEFORE creating UI
        applySavedLanguage()
        applySavedTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // UI elements
        val rbEnglish = findViewById<RadioButton>(R.id.rbEnglish)
        val rbAfrikaans = findViewById<RadioButton>(R.id.rbAfrikaans)
        val rbLight = findViewById<RadioButton>(R.id.rbLight)
        val rbDark = findViewById<RadioButton>(R.id.rbDark)
        val switchNotifications = findViewById<Switch>(R.id.switchNotifications)
        val btnSave = findViewById<Button>(R.id.btnSaveSettings)

        // Restore saved values
        val lang = prefs.getString("language", "english")
        rbEnglish.isChecked = lang == "english"
        rbAfrikaans.isChecked = lang == "afrikaans"

        val theme = prefs.getString("theme", "bright")
        rbLight.isChecked = theme == "bright"
        rbDark.isChecked = theme == "dark"

        switchNotifications.isChecked = prefs.getBoolean("notifications", false)

        // SAVE BUTTON
        btnSave.setOnClickListener {

            val selectedLanguage = if (rbEnglish.isChecked) "english" else "afrikaans"
            val selectedTheme = if (rbDark.isChecked) "dark" else "bright"
            val allowNotifications = switchNotifications.isChecked

            // Save settings
            prefs.edit().apply {
                putString("language", selectedLanguage)
                putString("theme", selectedTheme)
                putBoolean("notifications", allowNotifications)
                apply()
            }

            // Apply settings
            setLocale(selectedLanguage)
            applySavedTheme()

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()

            recreate() // refresh UI immediately
        }
    }

    // ---------------------------
    // LANGUAGE HANDLING
    // ---------------------------

    private fun setLocale(lang: String) {
        val locale = if (lang == "afrikaans") Locale("af") else Locale("en")
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun applySavedLanguage() {
        val lang = getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString("language", "english")!!
        setLocale(lang)
    }

    // ---------------------------
    // THEME HANDLING
    // ---------------------------

    private fun applySavedTheme() {
        val theme = getSharedPreferences("AppSettings", MODE_PRIVATE)
            .getString("theme", "bright")!!

        when (theme) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "bright" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
