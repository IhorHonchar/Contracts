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

public class AddBranchDialogFragment extends DialogFragment {

    private EditText country;
    private EditText address;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_branch);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_branch, null);

        String insertUrl = "https://kayzersproject.000webhostapp.com/server/insert_branch.php";


        country = view.findViewById(R.id.ev_country);
        address = view.findViewById(R.id.ev_address);

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
                                parameters.put("Страна", country.getText().toString());
                                parameters.put("Адрес", address.getText().toString());

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
                        AddBranchDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private boolean isCorrectData(){
        return  ((!country.getText().toString().equals("")) &&
                (!address.getText().toString().equals("")));
    }
}
