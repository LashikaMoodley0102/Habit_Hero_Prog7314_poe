package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomHealthHabitActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etTarget: EditText
    private lateinit var etCurrent: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_health)

        etTitle = findViewById(R.id.editTextHealthTitle)
        etDescription = findViewById(R.id.editTextHealthDescription)
        etTarget = findViewById(R.id.editTextHealthTarget)
        etCurrent = findViewById(R.id.editTextHealthCurrent)
        btnSave = findViewById(R.id.buttonSaveHealth)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val target = etTarget.text.toString().trim().toDoubleOrNull()
            val current = etCurrent.text.toString().trim().toDoubleOrNull()

            if (title.isEmpty() || description.isEmpty() || target == null || current == null) {
                if (title.isEmpty()) etTitle.error = "Enter goal title"
                if (description.isEmpty()) etDescription.error = "Enter description"
                if (target == null) etTarget.error = "Enter target value"
                if (current == null) etCurrent.error = "Enter current value"
                return@setOnClickListener
            }

            val user = auth.currentUser ?: run {
                Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = hashMapOf(
                "title" to title,
                "description" to description,
                "targetValue" to target,
                "currentValue" to current,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).collection("healthGoals")
                .add(item)
                .addOnSuccessListener {
                    Toast.makeText(this, "Health Goal Saved!", Toast.LENGTH_SHORT).show()
                    etTitle.text.clear(); etDescription.text.clear(); etTarget.text.clear(); etCurrent.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
