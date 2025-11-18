package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LifestyleActivity : AppCompatActivity() {

    private lateinit var readingButton: Button
    private lateinit var meditationButton: Button
    private lateinit var customHabitButton: Button
    private lateinit var addButton: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifestyle)

        readingButton = findViewById(R.id.button19)
        meditationButton = findViewById(R.id.button20)
        customHabitButton = findViewById(R.id.button21)
        addButton = findViewById(R.id.button25)

        readingButton.setOnClickListener {
            Toast.makeText(this, "Opening Reading Tracker", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ReadingTrackerActivity::class.java))
        }

        meditationButton.setOnClickListener {
            Toast.makeText(this, "Opening Meditation", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MeditationActivity::class.java))
        }

        customHabitButton.setOnClickListener {
            Toast.makeText(this, "Opening Custom Lifestyle Habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomLifestyleHabitActivity::class.java))
        }

        addButton.setOnClickListener {
            Toast.makeText(this, "Add new lifestyle habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomHabitActivity::class.java))
        }

        auth.currentUser?.let {
            db.collection("users").document(it.uid).collection("analytics")
                .add(hashMapOf("screen" to "LifestyleActivity", "timestamp" to System.currentTimeMillis()))
        }
    }
}
