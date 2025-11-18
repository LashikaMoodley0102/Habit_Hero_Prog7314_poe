package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomFinancialHabitActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etTarget: EditText
    private lateinit var etCurrent: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_finance)

        etTitle = findViewById(R.id.editTextFinanceTitle)
        etDescription = findViewById(R.id.editTextFinanceDescription)
        etTarget = findViewById(R.id.editTextFinanceTarget)
        etCurrent = findViewById(R.id.editTextFinanceCurrent)
        btnSave = findViewById(R.id.buttonSaveFinance)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val target = etTarget.text.toString().trim().toDoubleOrNull()
            val current = etCurrent.text.toString().trim().toDoubleOrNull()

            if (title.isEmpty() || description.isEmpty() || target == null || current == null) {
                if (title.isEmpty()) etTitle.error = "Enter goal title"
                if (description.isEmpty()) etDescription.error = "Enter description"
                if (target == null) etTarget.error = "Enter target amount"
                if (current == null) etCurrent.error = "Enter current amount"
                return@setOnClickListener
            }

            val user = auth.currentUser ?: run {
                Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = hashMapOf(
                "title" to title,
                "description" to description,
                "targetAmount" to target,
                "currentAmount" to current,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).collection("financeGoals")
                .add(item)
                .addOnSuccessListener {
                    Toast.makeText(this, "Finance Goal Saved!", Toast.LENGTH_SHORT).show()
                    etTitle.text.clear(); etDescription.text.clear(); etTarget.text.clear(); etCurrent.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
