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

private const val CHAVE_LOGIN = "Login"

class LoginActivity : AppCompatActivity() {

    lateinit var botaologar: Button
    lateinit var campoemail: EditText
    lateinit var camposenha: EditText
    lateinit var botaoregistrar: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = CHAVE_LOGIN
        auth = Firebase.auth
        iniciaCampos()
        configuraBotao()
        verificaSeEstaLogado()
        irParaRegistrar()
    }

    private fun irParaRegistrar() {
        botaoregistrar.setOnClickListener {
            val intent = Intent(applicationContext, RegistrarActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun verificaSeEstaLogado() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, ListaFilmesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun iniciaCampos() {
        botaologar = findViewById(id.login_botao_registrar)
        campoemail = findViewById(id.login_email)
        camposenha = findViewById(id.login_senha)
        botaoregistrar = findViewById(R.id.login_cadastro)
    }

    private fun configuraBotao(): Button{
        botaologar.setOnClickListener{
            val email = campoemail.text.toString()
            val senha = camposenha.text.toString()

            if (validaEmail(email)) {
                showToast("Por favor, insira um E-Mail válido")
            }
            if (validaSenha(senha)) {
                showToast("Por favor, insira uma senha.")
            }
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showToast("Login realizado com sucesso!")
                        val intent = Intent(applicationContext, ListaFilmesActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        showToast("Falha na Autenticação.")
                    }
                }
        }
        return botaologar
    }

    private fun validaEmail(email: String): Boolean{
        return email.isNotEmpty() && email.contains("@")
    }

    private fun validaSenha(senha: String): Boolean{
        return senha.isEmpty()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}