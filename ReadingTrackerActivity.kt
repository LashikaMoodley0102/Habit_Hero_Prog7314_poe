package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReadingTrackerActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val etGoalPages = findViewById<EditText>(R.id.etReadingGoalPages)
        val etPagesRead = findViewById<EditText>(R.id.etPagesRead)
        val etBookName = findViewById<EditText>(R.id.etBookName)
        val btnSave = findViewById<Button>(R.id.btnSaveReading)

        btnSave.setOnClickListener {
            val bookName = etBookName.text.toString().trim()
            val pagesRead = etPagesRead.text.toString().toIntOrNull()
            val goalPages = etGoalPages.text.toString().toIntOrNull()

            if (bookName.isEmpty() || pagesRead == null || goalPages == null) {
                Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "bookName" to bookName,
                "pagesRead" to pagesRead,
                "goalPages" to goalPages,
                "timestamp" to System.currentTimeMillis()
            )

            val user = auth.currentUser ?: return@setOnClickListener
            db.collection("users").document(user.uid).collection("reading")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Saved: $bookName ($pagesRead/$goalPages)", Toast.LENGTH_SHORT).show()
                    etBookName.text.clear(); etPagesRead.text.clear(); etGoalPages.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save reading data", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
