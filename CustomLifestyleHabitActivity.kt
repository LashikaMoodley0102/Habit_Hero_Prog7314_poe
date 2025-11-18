package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomLifestyleHabitActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etFrequency: EditText
    private lateinit var etStartDate: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_lifestyle)

        etTitle = findViewById(R.id.editTextHabitTitle)
        etDescription = findViewById(R.id.editTextHabitDescription)
        etFrequency = findViewById(R.id.editTextHabitFrequency)
        etStartDate = findViewById(R.id.editTextHabitStartDate)
        btnSave = findViewById(R.id.buttonSaveHabit)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val frequency = etFrequency.text.toString().trim()
            val startDate = etStartDate.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || frequency.isEmpty() || startDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = auth.currentUser ?: run {
                Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = hashMapOf(
                "title" to title,
                "description" to description,
                "frequency" to frequency,
                "startDate" to startDate,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).collection("lifestyleHabitsCustom")
                .add(item)
                .addOnSuccessListener {
                    Toast.makeText(this, "Habit Saved!", Toast.LENGTH_SHORT).show()
                    etTitle.text.clear(); etDescription.text.clear(); etFrequency.text.clear(); etStartDate.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
