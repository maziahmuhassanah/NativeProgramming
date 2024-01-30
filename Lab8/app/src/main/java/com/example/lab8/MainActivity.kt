import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab8.R

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        if (checkCredentials()) {
            openSuccessPage()
        } else {
            setupLoginPage()
        }
    }

    private fun setupLoginPage() {
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                saveCredentials(username, password)
                openSuccessPage()
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }

    private fun checkCredentials(): Boolean {
        val username = sharedPreferences.getString(KEY_USERNAME, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)

        return !username.isNullOrBlank() && !password.isNullOrBlank()
    }

    private fun openSuccessPage() {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
    }
}
