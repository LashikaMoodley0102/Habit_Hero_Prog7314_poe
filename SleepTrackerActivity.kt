package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SleepTrackerActivity : AppCompatActivity() {

    private lateinit var editTextSleep: EditText
    private lateinit var plusButton: ImageButton
    private lateinit var minusButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var exitButton: ImageButton

    private var sleepHours = 0
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_sleep)

        editTextSleep = findViewById(R.id.editTextText2)
        plusButton = findViewById(R.id.imageButton5)
        minusButton = findViewById(R.id.imageButton4)
        saveButton = findViewById(R.id.button2)
        exitButton = findViewById(R.id.btnExit2)

        editTextSleep.setText(sleepHours.toString())

        plusButton.setOnClickListener {
            sleepHours++
            editTextSleep.setText(sleepHours.toString())
        }

        minusButton.setOnClickListener {
            if (sleepHours > 0) sleepHours--
            editTextSleep.setText(sleepHours.toString())
        }

        editTextSleep.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                sleepHours = editTextSleep.text.toString().toIntOrNull() ?: 0
                editTextSleep.setText(sleepHours.toString())
            }
        }

        saveButton.setOnClickListener {
            saveSleepToFirestore(sleepHours)
        }

        exitButton.setOnClickListener { finish() }
    }

    private fun saveSleepToFirestore(hours: Int) {
        val user = auth.currentUser ?: return
        val data = hashMapOf(
            "hours" to hours,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("sleep")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Sleep saved: $hours hours", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save sleep", Toast.LENGTH_SHORT).show()
            }
    }
}
