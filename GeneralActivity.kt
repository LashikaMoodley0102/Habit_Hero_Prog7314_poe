package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GeneralActivity : AppCompatActivity() {

    private lateinit var moodButton: Button
    private lateinit var journalButton: Button
    private lateinit var todoButton: Button
    private lateinit var customHabitButton: Button
    private lateinit var addButton: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        moodButton = findViewById(R.id.button14)
        journalButton = findViewById(R.id.button12)
        todoButton = findViewById(R.id.button11)
        customHabitButton = findViewById(R.id.button13)
        addButton = findViewById(R.id.button26)

        moodButton.setOnClickListener {
            Toast.makeText(this, "Opening Mood of the Day", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MoodTrackerActivity::class.java))
        }

        journalButton.setOnClickListener {
            Toast.makeText(this, "Opening Journal", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, JournalActivity::class.java))
        }

        todoButton.setOnClickListener {
            Toast.makeText(this, "Opening To-Do List", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ToDoListActivity::class.java))
        }

        customHabitButton.setOnClickListener {
            Toast.makeText(this, "Opening Custom Habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomGeneralHabitActivity::class.java))
        }

        addButton.setOnClickListener {
            Toast.makeText(this, "Add new general habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomHabitActivity::class.java))
        }

        auth.currentUser?.let {
            db.collection("users").document(it.uid).collection("analytics")
                .add(hashMapOf("screen" to "GeneralActivity", "timestamp" to System.currentTimeMillis()))
        }
    }
}
