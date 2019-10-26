package com.uok.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private String email;
    private EditText todoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        todoTxt = findViewById(R.id.newTxt);

        if (getIntent().hasExtra("email")){
            email = getIntent().getStringExtra("email");
        } else {
            finish();
        }

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoTxt.getText().toString();

                Data data = new Data();
                data.setStatus(false);
                data.setName(todo);

                APIService service = APIClient.getAPIClient().create(APIService.class);
                Call<APIResponse> addItem = service.addItem(email, data);

                addItem.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        if (response.code() == 200) {
                            Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        });
    }
}
