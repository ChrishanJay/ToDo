package com.uok.todo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<Todo> todoList;
    private Context context;

    public TodoAdapter( Context context, List<Todo> todoList) {
        this.todoList = todoList;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        final Todo todo = todoList.get(position);

        holder.txtView.setText(todo.getData().getName());
        holder.done.setChecked(todo.getData().isStatus());

        holder.done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Data data = new Data();
                data.setStatus(isChecked);

                APIService service = APIClient.getAPIClient().create(APIService.class);
                Call<APIResponse> updateItem = service.updateItem("a@a.com", todo.getId(), data);
                updateItem.enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse apiResponse = response.body();
                        Todo todo1 = apiResponse.getItems().get(0);
                        Log.e("TODO", "onResponse: "+ todo1.getData().isStatus());
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        TextView txtView;
        private CheckBox done;


        ViewHolder(View mView) {
            super(mView);
            this.mView = mView;

            txtView = mView.findViewById(R.id.item);
            done = mView.findViewById(R.id.checkBox);
        }


    }


}
