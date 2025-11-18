package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CustomHabitActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var frequencyEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_habit) // your XML file name

        // Bind views
        titleEditText = findViewById(R.id.editTextHabitTitle)
        descriptionEditText = findViewById(R.id.editTextHabitDescription)
        frequencyEditText = findViewById(R.id.editTextHabitFrequency)
        startDateEditText = findViewById(R.id.editTextHabitStartDate)
        saveButton = findViewById(R.id.buttonSaveHabit)

        // Save Button functionality
        saveButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val frequency = frequencyEditText.text.toString().trim()
            val startDate = startDateEditText.text.toString().trim()

            // Validate fields
            if (title.isEmpty() || description.isEmpty() || frequency.isEmpty() || startDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Here you can save the habit to a database or SharedPreferences
                // For now, just show a confirmation Toast
                Toast.makeText(
                    this,
                    "Habit '$title' saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                // Optional: Clear all fields after saving
                titleEditText.text.clear()
                descriptionEditText.text.clear()
                frequencyEditText.text.clear()
                startDateEditText.text.clear()
            }
        }
    }
}
