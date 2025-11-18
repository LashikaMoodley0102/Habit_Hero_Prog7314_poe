package student.projects.habithero_prog

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutTrackerActivity : AppCompatActivity() {

    private lateinit var plusButton: ImageButton
    private lateinit var minusButton: ImageButton
    private lateinit var minutesPlusText: TextView
    private lateinit var minutesMinusText: TextView
    private lateinit var editText: EditText
    private lateinit var saveButton: Button
    private lateinit var exitButton: ImageButton

    private var workoutMinutes: Int = 0
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_workout)

        plusButton = findViewById(R.id.imageButton2)
        minusButton = findViewById(R.id.imageButton3)
        minutesPlusText = findViewById(R.id.textView29)
        minutesMinusText = findViewById(R.id.textView28)
        editText = findViewById(R.id.editTextText)
        saveButton = findViewById(R.id.buttonSaveWorkout)
        exitButton = findViewById(R.id.btnExit3)

        updateDisplay()

        plusButton.setOnClickListener {
            workoutMinutes += 15
            updateDisplay()
        }

        minusButton.setOnClickListener {
            workoutMinutes = (workoutMinutes - 15).coerceAtLeast(0)
            updateDisplay()
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = editText.text.toString().toIntOrNull()
                if (input != null && input >= 0) workoutMinutes = input
                updateDisplay()
            }
        }

        saveButton.setOnClickListener {
            saveWorkoutToFirestore(workoutMinutes)
        }

        exitButton.setOnClickListener { finish() }
    }

    private fun updateDisplay() {
        minutesPlusText.text = "$workoutMinutes MINS"
        minutesMinusText.text = "$workoutMinutes MINS"
        editText.setText(workoutMinutes.toString())
    }

    private fun saveWorkoutToFirestore(minutes: Int) {
        val user = auth.currentUser ?: return
        val data = hashMapOf(
            "minutes" to minutes,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("workout")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Workout saved: $minutes minutes", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save workout", Toast.LENGTH_SHORT).show()
            }
    }
}
