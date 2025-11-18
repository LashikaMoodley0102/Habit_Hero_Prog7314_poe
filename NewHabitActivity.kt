package student.projects.habithero_prog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewHabitActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var healthButton: ImageButton
    private lateinit var financeButton: ImageButton
    private lateinit var lifestyleButton: ImageButton
    private lateinit var generalButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_habit)

        addButton = findViewById(R.id.button23)
        healthButton = findViewById(R.id.imageButton8)
        financeButton = findViewById(R.id.imageButton9)
        lifestyleButton = findViewById(R.id.imageButton10)
        generalButton = findViewById(R.id.imageButton11)

        addButton.setOnClickListener {
            Toast.makeText(this, "Open Add Habit Form", Toast.LENGTH_SHORT).show()
            // TODO: Redirect to AddHabitFormActivity when implemented
        }

        healthButton.setOnClickListener {
            startActivity(Intent(this, HealthActivity::class.java))
        }

        financeButton.setOnClickListener {
            startActivity(Intent(this, FinancialActivity::class.java))
        }

        lifestyleButton.setOnClickListener {
            startActivity(Intent(this, LifestyleActivity::class.java))
        }

        generalButton.setOnClickListener {
            startActivity(Intent(this, GeneralActivity::class.java))
        }
    }
}
