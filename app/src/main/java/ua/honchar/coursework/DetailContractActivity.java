package ua.honchar.coursework;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.honchar.coursework.fragments.dialogs.AddContractDialogFragment;

public class DetailContractActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    private final String URL = "https://kayzersproject.000webhostapp.com/server/get_contracts.php";

    private int position;
    private RequestQueue requestQueue;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            position = getIntent().getIntExtra(POSITION, 1);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView contract = findViewById(R.id.tv_contract);
        TextView s_date = findViewById(R.id.tv_start_date);
        TextView e_date = findViewById(R.id.tv_end_date);
        TextView number = findViewById(R.id.tv_seats_number);
        TextView route = findViewById(R.id.tv_route);
        TextView client = findViewById(R.id.tv_client);
        TextView branch = findViewById(R.id.tv_branch);
        TextView operator = findViewById(R.id.tv_operator);

        requestQueue  = Volley.newRequestQueue(this.getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray contracts = response.getJSONArray("contracts");
                    JSONObject array_card = contracts.getJSONObject(position);

                    contract.setText(array_card.getString("№Договора"));
                    s_date.setText(array_card.getString("Дата_начала"));
                    e_date.setText(array_card.getString("Дата_окончания"));
                    number.setText(array_card.getString("Количество_мест"));
                    route.setText(array_card.getString("Маршрут"));
                    client.setText(array_card.getString("Клиент"));
                    branch.setText(array_card.getString("№Филиала"));
                    operator.setText(array_card.getString("Туроператор"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contract, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_contract){
            DialogFragment create_contract = new AddContractDialogFragment();
            create_contract.show(getSupportFragmentManager(), "Create contract");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}