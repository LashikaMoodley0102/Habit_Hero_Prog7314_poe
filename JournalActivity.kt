package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class JournalActivity : AppCompatActivity() {

    private lateinit var etJournalText: EditText
    private lateinit var btnSaveJournal: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)

        etJournalText = findViewById(R.id.etJournalText)
        btnSaveJournal = findViewById(R.id.btnSaveJournal)

        // Load latest entry (optional)
        loadLatestJournal()

        btnSaveJournal.setOnClickListener {
            saveJournal()
        }
    }

    private fun saveJournal() {
        val journalText = etJournalText.text.toString().trim()
        if (journalText.isEmpty()) {
            Toast.makeText(this, "Please write something before saving!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "You must be logged in to save a journal entry", Toast.LENGTH_SHORT).show()
            return
        }

        val entry = hashMapOf(
            "text" to journalText,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .collection("journalEntries")
            .add(entry)
            .addOnSuccessListener {
                Toast.makeText(this, "Journal saved successfully!", Toast.LENGTH_SHORT).show()
                etJournalText.text.clear()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save journal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadLatestJournal() {
        val user = auth.currentUser ?: return
        db.collection("users")
            .document(user.uid)
            .collection("journalEntries")
            .orderBy("timestamp")
            .limit(1)
            .get()
            .addOnSuccessListener { snap ->
                val doc = snap.documents.firstOrNull()
                val text = doc?.getString("text") ?: ""
                etJournalText.setText(text)
            }
    }
}
