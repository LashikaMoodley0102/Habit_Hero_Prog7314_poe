package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SetSavingsActivity : AppCompatActivity() {

    private lateinit var etSavingsGoal: EditText
    private lateinit var etSavingsActual: EditText
    private lateinit var btnSaveSavings: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        etSavingsGoal = findViewById(R.id.etSavingsGoal)
        etSavingsActual = findViewById(R.id.etSavingsActual)
        btnSaveSavings = findViewById(R.id.btnSaveSavings)

        btnSaveSavings.setOnClickListener {
            val goal = etSavingsGoal.text.toString().toDoubleOrNull()
            val actual = etSavingsActual.text.toString().toDoubleOrNull()

            if (goal == null || actual == null) {
                Toast.makeText(this, "Please enter valid amounts", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveSavingsToFirestore(goal, actual)
            etSavingsGoal.text.clear()
            etSavingsActual.text.clear()
        }
    }

    private fun saveSavingsToFirestore(goal: Double, actual: Double) {
        val user = auth.currentUser ?: return
        val data = hashMapOf(
            "goal" to goal,
            "actual" to actual,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("savings")
            .add(data)
            .addOnSuccessListener {
                val progress = (actual / goal * 100).coerceAtMost(100.0)
                Toast.makeText(
                    this,
                    "Savings recorded! Progress: ${String.format("%.2f", progress)}%",
                    Toast.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save savings", Toast.LENGTH_SHORT).show()
            }
    }
}
