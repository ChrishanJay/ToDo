package com.uok.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private String email;

    private TodoAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView name = findViewById(R.id.email);

        if (getIntent().hasExtra("email")){
            email = getIntent().getStringExtra("email");
            name.setText(String.format("Hello %s", email));
        } else {
            finish();
        }

        findViewById(R.id.btnAddNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), AddActivity.class);
                addIntent.putExtra("email", email);
                startActivity(addIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getItems();
    }

    private void getItems(){
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        APIService service = APIClient.getAPIClient().create(APIService.class);
        Call<APIResponse> allTodo = service.getAllItems(email);

        allTodo.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                APIResponse apiResponse = response.body();
                showTodoItems(apiResponse.getItems());

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void showTodoItems(List<Todo> items){
        recyclerView = findViewById(R.id.todoView);
        adapter = new TodoAdapter(this, items, email);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
