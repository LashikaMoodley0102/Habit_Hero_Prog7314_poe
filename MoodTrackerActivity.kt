package student.projects.habithero_prog

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MoodTrackerActivity : AppCompatActivity() {

    private var selectedMoodId: Int? = null
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_mood)

        findViewById<ImageButton>(R.id.btnExit2).setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.button2)

        val moodButtons = listOf(
            R.id.imageButton16, R.id.imageButton23, R.id.imageButton24, R.id.imageButton25,
            R.id.imageButton15, R.id.imageButton22, R.id.imageButton17, R.id.imageButton14,
            R.id.imageButton18, R.id.imageButton19, R.id.imageButton20, R.id.imageButton21
        )

        moodButtons.forEach { id ->
            findViewById<ImageButton>(id).setOnClickListener {
                selectedMoodId = id
                highlightSelectedMood(moodButtons, id)
            }
        }

        btnSave.setOnClickListener {
            if (selectedMoodId == null) {
                Toast.makeText(this, "Select a mood first!", Toast.LENGTH_SHORT).show()
            } else {
                saveMoodToFirestore(getMoodName(selectedMoodId!!))
            }
        }
    }

    private fun highlightSelectedMood(allButtons: List<Int>, selectedId: Int) {
        allButtons.forEach { id ->
            val btn = findViewById<ImageButton>(id)
            btn.setBackgroundColor(if (id == selectedId) Color.parseColor("#D1B3FF") else Color.TRANSPARENT)
        }
    }

    private fun getMoodName(id: Int) = when(id){
        R.id.imageButton16 -> "Happy"; R.id.imageButton23 -> "Sad"; R.id.imageButton24 -> "Tired"
        R.id.imageButton25 -> "Nervous"; R.id.imageButton15 -> "Scared"; R.id.imageButton22 -> "Angry"
        R.id.imageButton17 -> "Shy"; R.id.imageButton14 -> "Excited"; R.id.imageButton18 -> "Bored"
        R.id.imageButton19 -> "Silly"; R.id.imageButton20 -> "Worried"; R.id.imageButton21 -> "Sick"
        else -> "Unknown"
    }

    private fun saveMoodToFirestore(mood: String) {
        val user = auth.currentUser ?: return
        db.collection("users").document(user.uid).collection("moods")
            .add(hashMapOf("mood" to mood, "timestamp" to System.currentTimeMillis()))
            .addOnSuccessListener { Toast.makeText(this, "Mood saved: $mood", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(this, "Failed to save mood", Toast.LENGTH_SHORT).show() }
    }
}
