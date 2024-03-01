package com.example.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;


public class MainActivity extends AppCompatActivity {
    //Conexão com o Firebase Firestore
    FirebaseFirestore conexao = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Objetos para os componentes da tela
        EditText campoID = findViewById(R.id.campoID);
        EditText campoNome = findViewById(R.id.campoNome);
        EditText campoPreco = findViewById(R.id.campoPreco);
        EditText campoQtde = findViewById(R.id.campoQtde);
        Button cadastrarSem = findViewById(R.id.btSemID);
        Button cadastrarCom = findViewById(R.id.btComID);
        Button apagar = findViewById(R.id.btApagar);
        Button listar = findViewById(R.id.btListar);

        //Evento do botão para cadastrar sem ID
        //O jeito mais rápido de cadastrar um documento no banco Firestore é passar um
      //  objeto
                //de uma classe qualquer, no caso vamos utilizar a classe Produto. Se fizermos
                //isso
        //será criado um ID automático para esse cadastro.
        cadastrarSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produto p = new Produto(Integer.parseInt(campoID.getText().toString()),
                        campoNome.getText().toString(),
                        Double.parseDouble(campoPreco.getText().toString()),
                        Integer.parseInt(campoQtde.getText().toString()));

                //Usamos o objeto "conexao" montado na classe para chamar o banco de
                //dados
                //e depois indicamos o nome de uma coleção. Se essa coleção existir,
                //será utilizada
                //a mesma e caso não exista, será criado na hora
                conexao.collection("produtos") //Pode ser qualquer nome
                        .add(p) //O método add() é utilizado para cadastrar ou atualizar
                        //Adicionamos o listener para verificar se deu certo
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this,
                                        "Produto cadastrado com ID " +
                                                documentReference.getId(), Toast.LENGTH_SHORT).show();
                            }
                        })
//Adicionar o listener em caso de falha
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Erro ao cadastrar",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        cadastrarCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Produto p = new Produto(Integer.parseInt(campoID.getText().toString()),
                        campoNome.getText().toString(),
                        Double.parseDouble(campoPreco.getText().toString()),
                        Integer.parseInt(campoQtde.getText().toString()));


                conexao.collection("produtos")
                        .document(campoID.getText().toString())
                        .set(p)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Produto cadastrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Erro ao cadastrar",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexao.collection("produtos")
                        .document(campoID.getText().toString())
                        .delete()//Chamar o método para apagar o documento com o ID acima
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this, "Produto removido",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Erro ao remover",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        listar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TelaListar.class));
            }
        });


    }
}