package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomescreenActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var newHabitButton: Button
    private lateinit var progressWater: ProgressBar
    private lateinit var progressWorkout: ProgressBar
    private lateinit var progressSleep: ProgressBar
    private lateinit var progressMeditate: ProgressBar
    private lateinit var progressSavings: ProgressBar
    private lateinit var progressRead: ProgressBar

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        welcomeTextView = findViewById(R.id.textView31)
        newHabitButton = findViewById(R.id.buttonNewHabit)
        progressWater = findViewById(R.id.progressWater)
        progressWorkout = findViewById(R.id.progressWorkout)
        progressSleep = findViewById(R.id.progressSleep)
        progressMeditate = findViewById(R.id.progressMeditate)
        progressSavings = findViewById(R.id.progressSavings)
        progressRead = findViewById(R.id.progressRead)

        // Load username from Firestore (if available)
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { doc ->
                    val username = doc?.getString("username") ?: user.email?.substringBefore("@") ?: "User"
                    welcomeTextView.text = "Welcome back, $username"
                }
                .addOnFailureListener {
                    welcomeTextView.text = "Welcome back"
                }
        } else {
            welcomeTextView.text = "Welcome back"
        }

        newHabitButton.setOnClickListener {
            startActivity(Intent(this, NewHabitActivity::class.java))
        }

        // Make progress bars clickable as before
        val progressBars = listOf(
            progressWater, progressWorkout, progressSleep,
            progressMeditate, progressSavings, progressRead
        )

        progressBars.forEach { progressBar ->
            progressBar.setOnClickListener {
                Toast.makeText(this, "Habit clicked! Update progress here.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
