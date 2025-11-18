package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HealthActivity : AppCompatActivity() {

    private lateinit var waterButton: Button
    private lateinit var workoutButton: Button
    private lateinit var sleepButton: Button
    private lateinit var customHabitButton: Button
    private lateinit var addButton: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)

        waterButton = findViewById(R.id.button15)
        workoutButton = findViewById(R.id.button18)
        sleepButton = findViewById(R.id.button16)
        customHabitButton = findViewById(R.id.button17)
        addButton = findViewById(R.id.button22)

        waterButton.setOnClickListener {
            Toast.makeText(this, "Opening Water Tracker", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WaterTrackerActivity::class.java))
        }

        workoutButton.setOnClickListener {
            Toast.makeText(this, "Opening Workout Tracker", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WorkoutTrackerActivity::class.java))
        }

        sleepButton.setOnClickListener {
            Toast.makeText(this, "Opening Sleep Tracker", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SleepTrackerActivity::class.java))
        }

        customHabitButton.setOnClickListener {
            Toast.makeText(this, "Opening Custom Habit Form", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomHealthHabitActivity::class.java))
        }

        addButton.setOnClickListener {
            Toast.makeText(this, "Add a new custom habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomHabitActivity::class.java))
        }

        // optional: log that user opened health screen
        val user = auth.currentUser
        user?.let {
            db.collection("users").document(it.uid).collection("analytics")
                .add(hashMapOf("screen" to "HealthActivity", "timestamp" to System.currentTimeMillis()))
        }
    }
}
