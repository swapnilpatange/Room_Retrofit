package com.example.ruchir.android_test_ms

import android.content.Intent
import android.opengl.Visibility
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.register -> {
                if (validateRegister()) {
                    var users: List<User>
                    users = UserNameExists().execute(reg_username.text.toString()).get()
                    if (users.size == 0) {
                        InsertAysnc().execute(reg_username.text.toString(), reg_password.text.toString())
                        Toast.makeText(this, "user registered successfully. Login using it", Toast.LENGTH_LONG).show()
                        reg_username.setText("")
                        reg_password.setText("")
                        confirm_password.setText("")
                        linearRegister.visibility = View.GONE
                        linearLogin.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(this, "username already registered with us", Toast.LENGTH_LONG).show()
                    }
                }
            }
            R.id.login -> {
                if (validateLogin()) {
                    var users: List<User>
                    users = UserRegistered().execute(username.text.toString(), password.text.toString()).get()
                    if (users.size > 0) {
                        var intent: Intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        prefManager.saveLogin(true)
                        Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this, "invalid username or password", Toast.LENGTH_LONG).show()
                    }

                }
            }
            R.id.signup -> {
                linearLogin.visibility = View.GONE
                linearRegister.visibility = View.VISIBLE
            }
            R.id.goToLogin -> {
                linearLogin.visibility = View.VISIBLE
                linearRegister.visibility = View.GONE
            }

        }
    }

    private fun validateRegister(): Boolean {

        if (reg_username.length() == 0) {
            Toast.makeText(this, "enter username", Toast.LENGTH_LONG).show();
            return false
        } else if (reg_password.length() == 0) {
            Toast.makeText(this, "enter password", Toast.LENGTH_LONG).show()
            return false
        } else if (!reg_password.text.toString().equals(confirm_password.text.toString())) {
            Toast.makeText(this, "both password must be same", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun validateLogin(): Boolean {
        if (username.length() == 0) {
            Toast.makeText(this, "enter username", Toast.LENGTH_LONG).show();
            return false
        } else if (password.length() == 0) {
            Toast.makeText(this, "enter password", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    lateinit var userDatabase: UserDatabase
    lateinit var prefManager: PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager = PrefManager(applicationContext)
        if (!prefManager.isUserLoggedIn()) {
            userDatabase = UserDatabase.getAppDatabase(this)
            register.setOnClickListener(this)
            login.setOnClickListener(this)
            goToLogin.setOnClickListener(this)
            signup.setOnClickListener(this)
        } else {
            var intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        // InsertAysnc().execute("swapnil", "swapnil")
    }

    inner class InsertAysnc : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg userData: String?): String {
            var user: User = User()
            user.username = userData[0]
            user.password = userData[1]
            userDatabase.userDao().insert(user)
            return "done"
        }

    }

    inner class UserRegistered : AsyncTask<String, Void, List<User>>() {
        override fun doInBackground(vararg userData: String?): List<User> {
            return userDatabase.userDao().registered(userData[0], userData[1])
        }

    }

    inner class UserNameExists : AsyncTask<String, Void, List<User>>() {
        override fun doInBackground(vararg userData: String?): List<User> {
            return userDatabase.userDao().usernameexist(userData[0])
        }


    }
}
