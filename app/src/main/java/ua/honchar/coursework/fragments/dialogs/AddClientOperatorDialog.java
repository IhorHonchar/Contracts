package ua.honchar.coursework.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ua.honchar.coursework.R;

public class AddClientOperatorDialog extends DialogFragment {

    public static final int CLIENTS = 0;
    public static final int OPERATORS = 1;
    public static final String TABLE = "table";

    private EditText passport,
                    f_name,
                    l_name,
                    phone;

    public static AddClientOperatorDialog newInstance(int num) {
        AddClientOperatorDialog fragment = new AddClientOperatorDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("table", num);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int table = getArguments().getInt("table");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String insertUrl="";
        String showResult="";
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_client_or_operator, null);

        passport = view.findViewById(R.id.ev_passport);
        f_name = view.findViewById(R.id.ev_f_name);
        l_name = view.findViewById(R.id.ev_l_name);
        phone = view.findViewById(R.id.ev_phone);

        switch (table){
            case CLIENTS:
                builder.setTitle(R.string.add_client);
                insertUrl = "https://kayzersproject.000webhostapp.com/server/insert_client.php";
                showResult = getActivity().getResources().getString(R.string.added_client);
                break;
            case OPERATORS:
                builder.setTitle(R.string.add_operator);
                insertUrl = "https://kayzersproject.000webhostapp.com/server/insert_operator.php";
                showResult = getActivity().getResources().getString(R.string.added_operator);
                break;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String finalInsertUrl = insertUrl;
        String finalShowResult = showResult;
        builder.setView(view)
                .setPositiveButton(R.string.add, (dialogInterface, i) -> {
                    if (isCorrectData()) {
                        StringRequest request = new StringRequest(Request.Method.POST, finalInsertUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<>();
                                parameters.put("Паспорт", passport.getText().toString());
                                parameters.put("Имя", f_name.getText().toString());
                                parameters.put("Фамилия", l_name.getText().toString());
                                parameters.put("Телефон", phone.getText().toString());

                                return parameters;
                            }
                        };
                        requestQueue.add(request);
                        Toast.makeText(getContext(), finalShowResult, Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getContext(), R.string.correct_input, Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddClientOperatorDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private boolean isCorrectData() {
        return ((!passport.getText().toString().equals("")) &&
                (!f_name.getText().toString().equals("")) &&
                (!l_name.getText().toString().equals("")) &&
                (!phone.getText().toString().equals("")));
    }
}
