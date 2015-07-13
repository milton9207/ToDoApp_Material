package com.example.todoapp_material.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;

public class Main_Fragment extends Fragment {

    private static final String FILE_NAME = "TodoManagerActivityData.txt";

    private Button button;
    private Toolbar toolbar;
    private ToDoListAdapater adapater;
    private RecyclerView recyclerView;


    public Main_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isInTwoPaneMode())
                {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container,new AddToDoFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();

                }


            }
        });

        //Adding custom Toolbar(app_bar), activity host needed
        toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ActionBarActivity activityRef = (ActionBarActivity) getActivity();
        activityRef.setSupportActionBar(toolbar);
        activityRef.getSupportActionBar().setTitle("hola");

        //RecyclerList
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerMainList);
        adapater = new ToDoListAdapater(getActivity());
        recyclerView.setAdapter(adapater);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }

    public void updateData(Bundle data)
    {
        ToDoItem item = new ToDoItem(data);
        adapater.add(item);


    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (adapater.getItemCount() == 0)
            loadItems();
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save ToDoItems

        saveItems();

    }

    // Load stored ToDoItems
    private void loadItems() {
        BufferedReader reader = null;
        try {
            FileInputStream fis = getActivity().openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));

            String title = null;
            String priority = null;
            String status = null;
            Date date = null;

            while (null != (title = reader.readLine())) {
                priority = reader.readLine();
                status = reader.readLine();
                date = ToDoItem.FORMAT.parse(reader.readLine());
                adapater.add(new ToDoItem(title, ToDoItem.Priority.valueOf(priority),
                        ToDoItem.Status.valueOf(status), date));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Save ToDoItems to file
    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILE_NAME,getActivity().MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));

            for (int idx = 0; idx < adapater.getItemCount(); idx++) {

                writer.println(adapater.getItem(idx));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    // If there is a FeedFragment, then the layout is two-pane
    private boolean isInTwoPaneMode() {

        return getFragmentManager().findFragmentById(R.id.main_fragment_large) != null;

    }


}
