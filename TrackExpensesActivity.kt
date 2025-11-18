package student.projects.habithero_prog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TrackExpensesActivity : AppCompatActivity() {

    private lateinit var etBudget: EditText
    private lateinit var etExpenseName: EditText
    private lateinit var etExpenseAmount: EditText
    private lateinit var btnAddExpense: Button
    private lateinit var btnSaveExpense: Button

    private val expensesList = mutableListOf<Expense>()
    private var budget: Double = 0.0

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        etBudget = findViewById(R.id.etBudget)
        etExpenseName = findViewById(R.id.etExpenseName)
        etExpenseAmount = findViewById(R.id.etExpenseAmount)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        btnSaveExpense = findViewById(R.id.btnSaveExpense)

        btnAddExpense.setOnClickListener {
            val name = etExpenseName.text.toString().trim()
            val amount = etExpenseAmount.text.toString().toDoubleOrNull()
            if (name.isEmpty() || amount == null) {
                Toast.makeText(this, "Fill valid expense", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            expensesList.add(Expense(name, amount))
            Toast.makeText(this, "Expense '$name' added!", Toast.LENGTH_SHORT).show()
            etExpenseName.text.clear(); etExpenseAmount.text.clear()
        }

        btnSaveExpense.setOnClickListener {
            val budgetValue = etBudget.text.toString().toDoubleOrNull()
            if (budgetValue == null || expensesList.isEmpty()) {
                Toast.makeText(this, "Enter budget and expenses", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            budget = budgetValue
            saveExpensesToFirestore()
            etBudget.text.clear(); expensesList.clear()
        }
    }

    private fun saveExpensesToFirestore() {
        val user = auth.currentUser ?: return
        val data = hashMapOf(
            "budget" to budget,
            "expenses" to expensesList.map { hashMapOf("name" to it.name, "amount" to it.amount) },
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("users").document(user.uid).collection("expenses")
            .add(data)
            .addOnSuccessListener {
                val total = expensesList.sumOf { it.amount }
                Toast.makeText(this, "Saved! Remaining: R${budget - total}", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save expenses", Toast.LENGTH_SHORT).show()
            }
    }
}
