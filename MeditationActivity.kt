package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MeditationActivity : AppCompatActivity() {

    private lateinit var etGoal: EditText
    private lateinit var etTime: EditText
    private lateinit var btnSave: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)

        etGoal = findViewById(R.id.etMeditationGoal)
        etTime = findViewById(R.id.etMeditationTime)
        btnSave = findViewById(R.id.btnSaveMeditation)

        btnSave.setOnClickListener {
            val goalMinutes = etGoal.text.toString().trim().toIntOrNull()
            val timeToday = etTime.text.toString().trim().toIntOrNull()

            if (goalMinutes == null || timeToday == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = auth.currentUser
            if (user == null) {
                Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val entry = hashMapOf(
                "goalMinutes" to goalMinutes,
                "timeToday" to timeToday,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).collection("meditationEntries")
                .add(entry)
                .addOnSuccessListener {
                    Toast.makeText(this, "Saved: $timeToday / $goalMinutes minutes", Toast.LENGTH_SHORT).show()
                    etGoal.text.clear()
                    etTime.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save meditation: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
