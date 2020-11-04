package ua.honchar.coursework;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private JSONArray array;
    private Listener listener;
    private String table;

    public RecyclerAdapter(JSONArray array, String table_name) {
        this.array = array;
        table = table_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView;
        switch (table){
            case "clients":
            case "operators":
                cardView = (CardView) LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.cardview_clients_operator, viewGroup, false);
                break;
            case "branches":
                cardView = (CardView) LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.cardview_branches, viewGroup, false);
                break;
            case "contracts":
            default:
                cardView = (CardView) LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.cardview_contracts, viewGroup, false);
        }
        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CardView cardView = viewHolder.cv;
        try {
            JSONObject array_card = array.getJSONObject(i);
            switch (table){
                case "contracts":
                    TextView id_contract = cardView.findViewById(R.id.id_contract);
                    id_contract.setText(array_card.getString("№Договора"));

                    TextView id_client = cardView.findViewById(R.id.id_client);
                    id_client.setText(array_card.getString("Клиент"));

                    TextView start_date = cardView.findViewById(R.id.start_date);
                    start_date.setText(array_card.getString("Дата_начала"));

                    TextView end_date = cardView.findViewById(R.id.end_date);
                    end_date.setText(array_card.getString("Дата_окончания"));
                    break;

                case "clients":
                case "operators":
                    TextView pass = cardView.findViewById(R.id.passport);
                    pass.setText(array_card.getString("Паспорт"));

                    TextView first_name = cardView.findViewById(R.id.f_name);
                    first_name.setText(array_card.getString("Имя"));

                    TextView last_name = cardView.findViewById(R.id.l_name);
                    last_name.setText(array_card.getString("Фамилия"));

                    TextView phone = cardView.findViewById(R.id.phone);
                    phone.setText(array_card.getString("Телефон"));
                    break;

                case "branches":
                    TextView branch = cardView.findViewById(R.id.branch);
                    branch.setText(array_card.getString("№Филиала"));

                    TextView country = cardView.findViewById(R.id.country);
                    country.setText(array_card.getString("Страна"));

                    TextView address = cardView.findViewById(R.id.address);
                    address.setText(array_card.getString("Адрес"));
                    break;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        cardView.setOnClickListener(view -> {
            if (listener != null){
                listener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cv = itemView;
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener{
        void onClick(int position);
    }
}
