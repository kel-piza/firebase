package com.example.projetofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
public class TelaListar extends AppCompatActivity {
    //Conexão com o Firebase Firestore
    FirebaseFirestore conexao = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_listar);
        //Iniciar o ListView
        ListView listView = findViewById(R.id.listView);
        //Criar uma lista com a classe Produto para receber os produtos cadastrados
        List<Produto> lista = new ArrayList<>();
        conexao.collection("produtos") //Conectar na mesma coleção
                .get() //O método get recupera todos os produtos
                //Agora usamos o CompleteListener
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Verificar se o processo foi concluído
                        if(task.isSuccessful()){
                            //Percorrer cada item da coleção "produtos"
                            for (QueryDocumentSnapshot cadaUm : task.getResult()) {

                                Produto p = cadaUm.toObject(Produto.class);
                                //Adicionar na lista
                                lista.add(p);
                            }

                            //e os processos serão executados fora de sincronia
                            ArrayAdapter<Produto> adapter = new
                                    ArrayAdapter<>(TelaListar.this,
                                    android.R.layout.simple_list_item_1, lista);
                            listView.setAdapter(adapter);
                        }else{
                            //Se conectar mas não conseguir completar a conexão
                            Toast.makeText(TelaListar.this, "Erro ao buscar",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Se der algum erro para conectar
                        Toast.makeText(TelaListar.this, "Erro ao buscar",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
