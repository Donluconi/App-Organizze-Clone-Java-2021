package com.example.organizze.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.activity.helper.Base64Custom;
import com.example.organizze.activity.model.*;
import com.example.organizze.R;
import com.example.organizze.activity.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setTitle("Cadastro");

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //validar se os campos foram preenchidos
                if (!textoNome.isEmpty()) {
                    if (!textoEmail.isEmpty()) {
                        if (!textoSenha.isEmpty()) {

                            usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            cadastrarUsuario();

                        } else {
                            Toast.makeText(CadastroActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                    }
                }
                 else{
                        Toast.makeText(CadastroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
        public void cadastrarUsuario(){

      //  autenticacao = FirebaseAuth.getInstance();

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuario.getEmail(), usuario.getSenha()
            ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                                usuario.setIdUsuario(idUsuario);
                                usuario.salvar(); //ele irá com todos os dados do usuário, salvar no firebase.

                                finish(); //a ideia é fechar essa activity
                            }

                               /* Toast.makeText(CadastroActivity.this,
                                        "Sucesso ao cadastrar usuário!", Toast.LENGTH_SHORT).show();*/
                            else {
                                String excecao = "";
                                try { //getException irá retornar uma exceção
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    excecao = "Digite uma senha mais forte!";
                                } catch (FirebaseAuthInvalidCredentialsException e)
                                {
                                    excecao = "Por favor, digite um e-mail válido!";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    excecao = "Essa conta já foi cadastrada";
                                }catch (Exception e){
                                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                    e.printStackTrace(); //printando a exceção no nosso log
                                }

                                Toast.makeText(CadastroActivity.this,
                                     excecao, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


            );


        }

}