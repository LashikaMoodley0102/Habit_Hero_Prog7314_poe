package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var addFingerprintButton: Button
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        nameEditText = findViewById(R.id.editTextText10)
        surnameEditText = findViewById(R.id.editTextText12)
        ageEditText = findViewById(R.id.editTextText11)
        usernameEditText = findViewById(R.id.editTextText9)
        passwordEditText = findViewById(R.id.editTextTextPassword3)
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword4)
        addFingerprintButton = findViewById(R.id.button4)
        registerButton = findViewById(R.id.button5)

        // Add Fingerprint placeholder
        addFingerprintButton.setOnClickListener {
            Toast.makeText(this, "Fingerprint feature coming soon!", Toast.LENGTH_SHORT).show()
        }

        // Register button
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()
            val ageText = ageEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim().lowercase()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            // Input validation
            if (name.isEmpty() || surname.isEmpty() || ageText.isEmpty() ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val ageInt = ageText.toIntOrNull()
            if (ageInt == null || ageInt < 1 || ageInt > 120) {
                Toast.makeText(this, "Please enter a valid age (1-120)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication registration
            auth.createUserWithEmailAndPassword("$username@gmail.com", password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        val userMap = hashMapOf(
                            "name" to name,
                            "surname" to surname,
                            "age" to ageInt,
                            "username" to username
                        )
                        firestore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error saving user: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
