package cr.ac.ucr.app.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import cr.ac.ucr.app.R;
import cr.ac.ucr.app.utilis.AppPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment implements View.OnClickListener{


    private AppCompatActivity activity;
    private ArrayList<String> todosArr;
    private String todosStr;
    private ListView lvTodos;
    private ArrayAdapter<String> todosAdapter;
    private Gson gson;


    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance() {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gson = new Gson();
        todosArr = new ArrayList<>();
        todosStr = AppPreferences.getInstance(activity).getString(AppPreferences.Keys.ITEMS);
        todosAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, todosArr);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        lvTodos = view.findViewById(R.id.lv_todos);
        FloatingActionButton fabAddTask = view.findViewById(R.id.fab_add_todo);
        fabAddTask.setOnClickListener(this);
        //todos.isEmpty

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if(!todosStr.equals("")){
            String[] todosArray = gson.fromJson(todosStr,String[].class);
            todosArr.addAll(Arrays.asList(todosArray)); //List
        }

        lvTodos.setAdapter(todosAdapter);
        setupListViewListener();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.todos_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.clean_list:
                cleanList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_todo:
                showAlert();
                break;
            default:
                break;
        }
    }

    private void setupListViewListener(){

        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.want_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                todosArr.remove(position);
                                todosAdapter.notifyDataSetChanged();
                                saveListToPreferences();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .create()
                        .show();



                return true;
            }
        });
    }



    private void saveListToPreferences(){
        todosStr = gson.toJson(todosArr);
        AppPreferences.getInstance(activity).put(AppPreferences.Keys.ITEMS,todosStr);
    }

    private void cleanList() {
        todosArr.clear();
        todosAdapter.notifyDataSetChanged();
        saveListToPreferences();
    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_task, null);


        builder.setView(view)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        TextInputEditText etTaskName = view.findViewById(R.id.et_task_name);

                        String taskName = etTaskName.getText().toString();

                        if(!taskName.isEmpty()){
                            todosArr.add(taskName);
                            todosAdapter.notifyDataSetChanged();

                            todosStr = gson.toJson(todosArr);
                            AppPreferences.getInstance(activity).put(AppPreferences.Keys.ITEMS,todosStr);
                            dialog.dismiss();

                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null);
        builder.create();
        builder.show();
    }




}