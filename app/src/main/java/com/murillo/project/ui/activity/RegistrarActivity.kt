package com.murillo.project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.murillo.project.R
import com.murillo.project.R.id

class RegistrarActivity : AppCompatActivity() {

    lateinit var botaoregistrar: Button
    lateinit var campoemail: EditText
    lateinit var camposenha: EditText
    lateinit var auth: FirebaseAuth
    lateinit var retornar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
        auth = Firebase.auth
        iniciaCampos()
        configuraBotao()
        voltaParaLogin()
    }

    private fun voltaParaLogin() {
        retornar.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun iniciaCampos() {
        botaoregistrar = findViewById(id.registrar_botao_registrar)
        campoemail = findViewById(id.registrar_email)
        camposenha = findViewById(id.registrar_senha)
        retornar = findViewById(id.registrar_retornar_login)
    }

    private fun configuraBotao(): Button {
        botaoregistrar.setOnClickListener {
            val email = campoemail.text.toString()
            val senha = camposenha.text.toString()

            if (validaEmail(email)) {
               showToast("Por favor, insira um E-Mail válido")
            }
            if (validaSenha(senha)) {
               showToast("Por favor, insira uma senha.")
            }
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showToast("Conta Criada")
                        val intent = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                            showToast("Falha na autenticação.")
                    }
                }
        }
        return botaoregistrar
    }

    private fun validaEmail(email: String): Boolean{
        return email.isNotEmpty() && email.contains("@")
    }

    private fun validaSenha(senha: String): Boolean {
        return senha.isEmpty()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}