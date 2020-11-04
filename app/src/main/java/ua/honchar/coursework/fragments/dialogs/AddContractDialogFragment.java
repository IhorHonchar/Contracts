package ua.honchar.coursework.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

public class AddContractDialogFragment extends DialogFragment {
    private EditText start_date,
            end_date,
            seats_number,
            route,
            client,
            branch,
            operator;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_contract);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_contract, null);

        String insertUrl = "https://kayzersproject.000webhostapp.com/server/insert_contract.php";


        start_date = view.findViewById(R.id.ev_start_date);
        end_date = view.findViewById(R.id.ev_end_date);
        seats_number = view.findViewById(R.id.ev_seats_number);
        route = view.findViewById(R.id.ev_route);
        client = view.findViewById(R.id.ev_client);
        branch = view.findViewById(R.id.ev_branch);
        operator = view.findViewById(R.id.ev_operator);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        builder.setView(view)
                .setPositiveButton(R.string.add, (dialogInterface, i) -> {
                    if (isCorrectData()) {
                        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
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
                                parameters.put("Дата_начала", start_date.getText().toString());
                                parameters.put("Дата_окончания", end_date.getText().toString());
                                parameters.put("Количество_мест", seats_number.getText().toString());
                                parameters.put("Маршрут", route.getText().toString());
                                parameters.put("Клиент", client.getText().toString());
                                parameters.put("№Филиала", branch.getText().toString());
                                parameters.put("Туроператор", operator.getText().toString());

                                return parameters;
                            }
                        };
                        requestQueue.add(request);
                        Toast.makeText(getContext(), R.string.created_contract, Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getContext(), R.string.correct_input, Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddContractDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private boolean isCorrectData(){
        return  ((!start_date.getText().toString().equals("")) &&
                (!end_date.getText().toString().equals("")) &&
                (!seats_number.getText().toString().equals("")) &&
                (!route.getText().toString().equals("")) &&
                (!client.getText().toString().equals("")) &&
                (!branch.getText().toString().equals("")) &&
                (!operator.getText().toString().equals("")));
    }
}
