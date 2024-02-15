package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import Adapter.ToDoLists;
import DataBase.ToDoDataBaseHelper;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG="ActionBottomDialog";
    private EditText newTaskText;
    private Button SaveButton;
    private ToDoDataBaseHelper dataBase;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_newtask,container,false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newTaskText = requireView().findViewById(R.id.addNewTaskEditText);
        SaveButton= requireView().findViewById(R.id.taskSavingButton);

        final Bundle bundle = getArguments();
        if (bundle != null) {
            String task = bundle.getString("task");
            newTaskText.setText(task);
            assert task != null;
            if (task.length() > 0) {
                SaveButton.setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary));
            }

            dataBase = new ToDoDataBaseHelper(getActivity());
            dataBase.openDatabase();

            newTaskText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().equals("")) {
                        SaveButton.setEnabled(false);
                        SaveButton.setTextColor(Color.GRAY);
                    } else
                        SaveButton.setEnabled(true);
                    SaveButton.setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            SaveButton.setEnabled(true);
            SaveButton.setClickable(true);
            SaveButton.setOnClickListener(v -> {
                String text=newTaskText.getText().toString();
                ToDoLists task1 = new ToDoLists();
                task1.setTask(text);
                task1.setStatus(0);
                dataBase.insertTask(task1);
                dismiss();
            });
        }
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
