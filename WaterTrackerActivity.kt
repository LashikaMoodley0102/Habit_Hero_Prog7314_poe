package student.projects.habithero_prog

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WaterTrackerActivity : AppCompatActivity() {

    private var dailyGoal = 8
    private var currentGlasses = 0

    private lateinit var progressText: TextView
    private lateinit var encouragementText: TextView
    private lateinit var waterDrops: List<ImageButton>
    private lateinit var notificationSwitch: Switch

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.habit_water)

        progressText = findViewById(R.id.textView2)
        encouragementText = findViewById(R.id.textView6)
        notificationSwitch = findViewById(R.id.switch1)

        waterDrops = listOf(
            findViewById(R.id.btnWaterDrop),
            findViewById(R.id.btnWaterDrop2),
            findViewById(R.id.btnWaterDrop3),
            findViewById(R.id.drop),
            findViewById(R.id.drop2),
            findViewById(R.id.drop3),
            findViewById(R.id.drop4),
            findViewById(R.id.drop5),
            findViewById(R.id.drop6)
        )

        waterDrops.forEachIndexed { index, button ->
            button.setOnClickListener {
                toggleWaterDrop(index)
            }
        }

        updateUI()
    }

    private fun toggleWaterDrop(index: Int) {
        val button = waterDrops[index]
        val tag = button.tag as? String
        if (tag == "filled") {
            button.setImageResource(R.drawable.light_water_drop)
            button.tag = "empty"
            currentGlasses--
        } else {
            button.setImageResource(R.drawable.dark_water_drop)
            button.tag = "filled"
            currentGlasses++
        }

        currentGlasses = currentGlasses.coerceIn(0, dailyGoal)
        updateUI()
        saveWaterProgress()
    }

    private fun updateUI() {
        progressText.text = "$currentGlasses out of $dailyGoal Glasses"
        val remaining = dailyGoal - currentGlasses
        encouragementText.text = if (remaining > 0)
            "Ella, you only had $currentGlasses glasses today. Keep going, $remaining more to go."
        else
            "Great job! You reached your daily goal of $dailyGoal glasses!"
    }

    private fun saveWaterProgress() {
        val user = auth.currentUser ?: return
        val data = hashMapOf(
            "currentGlasses" to currentGlasses,
            "dailyGoal" to dailyGoal,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("water")
            .add(data)
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save water progress", Toast.LENGTH_SHORT).show()
            }
    }
}
