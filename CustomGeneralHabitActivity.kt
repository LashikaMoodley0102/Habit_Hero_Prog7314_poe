package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomGeneralHabitActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etTarget: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_general)

        etTitle = findViewById(R.id.editTextGeneralTitle)
        etDescription = findViewById(R.id.editTextGeneralDescription)
        etTarget = findViewById(R.id.editTextGeneralTarget)
        btnSave = findViewById(R.id.buttonSaveGeneral)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val target = etTarget.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                if (title.isEmpty()) etTitle.error = "Enter goal title"
                if (description.isEmpty()) etDescription.error = "Enter description"
                return@setOnClickListener
            }

            val user = auth.currentUser ?: run {
                Toast.makeText(this, "You must be logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = hashMapOf(
                "title" to title,
                "description" to description,
                "target" to target,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("users").document(user.uid).collection("generalGoals")
                .add(item)
                .addOnSuccessListener {
                    Toast.makeText(this, "General Goal Saved!", Toast.LENGTH_SHORT).show()
                    etTitle.text.clear(); etDescription.text.clear(); etTarget.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
