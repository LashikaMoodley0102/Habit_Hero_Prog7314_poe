package student.projects.habithero_prog

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReportActivity : AppCompatActivity() {

    private lateinit var etTextReport: EditText
    private lateinit var btnSaveReport: Button
    private lateinit var summaryLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        etTextReport = findViewById(R.id.etTextReport)
        btnSaveReport = findViewById(R.id.btnSaveReport)
        summaryLayout = findViewById(R.id.linearSummary)

        // Load SharedPreferences
        val prefs = getSharedPreferences("HabitHeroReport", Context.MODE_PRIVATE)

        // Load saved report notes
        etTextReport.setText(prefs.getString("reportText", ""))

        // Display summary of habits dynamically
        displayHabitSummary(prefs)

        // Save button logic
        btnSaveReport.setOnClickListener {
            prefs.edit().putString("reportText", etTextReport.text.toString()).apply()
            Toast.makeText(this, "Report saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayHabitSummary(prefs: android.content.SharedPreferences) {
        // Get data from all trackers
        val journalCount = prefs.getInt("journalCount", 0)
        val todoDone = prefs.getInt("todoDone", 0)
        val todoPlanned = prefs.getInt("todoPlanned", 0)
        val expenses = prefs.getInt("expenses", 0)
        val budget = prefs.getInt("budget", 0)
        val savingsGoal = prefs.getInt("savingsGoal", 0)
        val savingsCurrent = prefs.getInt("savingsCurrent", 0)
        val pagesRead = prefs.getInt("pagesRead", 0)
        val meditationSessions = prefs.getInt("meditationSessions", 0)
        val meditationMinutes = prefs.getInt("meditationMinutes", 0)
        val workoutSessions = prefs.getInt("workoutSessions", 0)
        val sleepHours = prefs.getInt("sleepHours", 0)

        // Build summary string
        val summaryText = """
            • Journal — $journalCount entries this week
            • To-Do — $todoDone tasks done / $todoPlanned planned
            • Expenses — R $expenses spent (Budget: R $budget)
            • Savings — Goal: R $savingsGoal | Current: R $savingsCurrent
            • Reading — $pagesRead pages
            • Meditation — $meditationSessions sessions (avg $meditationMinutes min)
            • Workout — $workoutSessions sessions
            • Sleep — $sleepHours hours
        """.trimIndent()

        // Clear old summary and add new TextView
        summaryLayout.removeAllViews()
        val summaryTextView = TextView(this)
        summaryTextView.text = summaryText
        summaryTextView.textSize = 14f
        summaryTextView.setTextColor(resources.getColor(R.color.purple_700))
        summaryLayout.addView(summaryTextView)
    }
}
