package ua.honchar.coursework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.honchar.coursework.R;
import ua.honchar.coursework.RecyclerAdapter;

public class ClientsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RequestQueue requestQueue;
    private SwipeRefreshLayout refreshLayout;
    private final String URL = "https://kayzersproject.000webhostapp.com/server/get_clients.php";
    private RecyclerView recyclerView;
    private String table = "clients";


    public ClientsFragment() {
        // Required empty public constructor
    }

    public static ClientsFragment newInstance() {
        return new ClientsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        getData();
    }

    @Override
    public void onRefresh() {
        refreshLayout.post(() -> {
            if (refreshLayout.isRefreshing()) {
                getData();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void getData(){
        requestQueue  = Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray contracts = response.getJSONArray(table);
                    RecyclerAdapter adapter = new RecyclerAdapter(contracts, table);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Some problem with json", Toast.LENGTH_SHORT).show();
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
}