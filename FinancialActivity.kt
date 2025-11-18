package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FinancialActivity : AppCompatActivity() {

    private lateinit var trackExpensesButton: Button
    private lateinit var setSavingsButton: Button
    private lateinit var customHabitButton: Button
    private lateinit var addButton: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial)

        trackExpensesButton = findViewById(R.id.button8)
        setSavingsButton = findViewById(R.id.button10)
        customHabitButton = findViewById(R.id.button9)
        addButton = findViewById(R.id.button24)

        trackExpensesButton.setOnClickListener {
            Toast.makeText(this, "Opening Track Expenses", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TrackExpensesActivity::class.java))
        }

        setSavingsButton.setOnClickListener {
            Toast.makeText(this, "Opening Set Savings Goals", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SetSavingsActivity::class.java))
        }

        customHabitButton.setOnClickListener {
            Toast.makeText(this, "Opening Custom Habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomFinancialHabitActivity::class.java))
        }

        addButton.setOnClickListener {
            Toast.makeText(this, "Add new financial habit", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CustomHabitActivity::class.java))
        }

        auth.currentUser?.let {
            db.collection("users").document(it.uid).collection("analytics")
                .add(hashMapOf("screen" to "FinancialActivity", "timestamp" to System.currentTimeMillis()))
        }
    }
}
