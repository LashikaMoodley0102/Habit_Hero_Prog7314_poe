package student.projects.habithero_prog

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ToDoListActivity : AppCompatActivity() {

    private lateinit var etNewTask: EditText
    private lateinit var btnAddTask: Button
    private lateinit var todoListContainer: LinearLayout
    private lateinit var btnSaveTodo: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        etNewTask = findViewById(R.id.etNewTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        todoListContainer = findViewById(R.id.todoListContainer)
        btnSaveTodo = findViewById(R.id.btnSaveTodo)

        loadTasksFromFirestore()

        btnAddTask.setOnClickListener {
            val text = etNewTask.text.toString().trim()
            if (text.isNotEmpty()) {
                addTask(text, false)
                etNewTask.text.clear()
            }
        }

        btnSaveTodo.setOnClickListener {
            saveTasksToFirestore()
        }
    }

    private fun addTask(text: String, checked: Boolean) {
        val checkBox = CheckBox(this)
        checkBox.text = text
        checkBox.isChecked = checked
        applyStrikeThrough(checkBox)
        checkBox.setOnCheckedChangeListener { button, _ -> applyStrikeThrough(button as CheckBox) }
        todoListContainer.addView(checkBox)
    }

    private fun applyStrikeThrough(checkBox: CheckBox) {
        checkBox.text = if (checkBox.isChecked) {
            SpannableString(checkBox.text).apply {
                setSpan(StrikethroughSpan(), 0, length, 0)
            }
        } else {
            checkBox.text.toString()
        }
    }

    private fun saveTasksToFirestore() {
        val user = auth.currentUser ?: return
        val tasks = mutableListOf<HashMap<String, Any>>()
        todoListContainer.children.forEach {
            if (it is CheckBox) {
                tasks.add(hashMapOf("text" to it.text.toString(), "checked" to it.isChecked))
            }
        }
        db.collection("users").document(user.uid).collection("todo")
            .add(hashMapOf("tasks" to tasks, "timestamp" to System.currentTimeMillis()))
            .addOnSuccessListener { Toast.makeText(this, "Tasks saved!", Toast.LENGTH_SHORT).show() }
            .addOnFailureListener { Toast.makeText(this, "Failed to save tasks", Toast.LENGTH_SHORT).show() }
    }

    private fun loadTasksFromFirestore() {
        val user = auth.currentUser ?: return
        db.collection("users").document(user.uid).collection("todo")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.forEach { doc ->
                    val tasks = doc.get("tasks") as? List<HashMap<String, Any>> ?: return@forEach
                    tasks.forEach {
                        val text = it["text"] as? String ?: ""
                        val checked = it["checked"] as? Boolean ?: false
                        addTask(text, checked)
                    }
                }
            }
    }
}
