package be.hvwebsites.healthmeasurements.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.hvwebsites.healthmeasurements.R;
import be.hvwebsites.healthmeasurements.entities.Belly;

public class BellyListAdapter extends RecyclerView.Adapter<BellyListAdapter.BellyViewHolder> {
    private final LayoutInflater inflater;
    private List<Belly> bellyList;

    public BellyListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    class BellyViewHolder extends RecyclerView.ViewHolder{
        private final TextView bellyItemView;

        private BellyViewHolder(View itemView){
            super(itemView);
            bellyItemView = itemView.findViewById(R.id.textViewBelly);
        }
    }

    @NonNull
    @Override
    public BellyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_belly_item, parent, false);
        return new BellyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BellyViewHolder holder, int position) {
        if (bellyList != null){
            Belly current = bellyList.get(position);
            String bellyTextLine = current.getDate()
                    + " : "
                    + current.getBellyRadius()
                    + "cm";
            holder.bellyItemView.setText(bellyTextLine);
        } else {
            holder.bellyItemView.setText("No bellys");
        }

    }

    public void setBellyList(List<Belly> bellyList) {
        this.bellyList = bellyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (bellyList != null) return bellyList.size();
        else return 0;
    }

    // Om te weten welke bellymeasurement is gekozen uit de lijst
    public Belly getBellyAtPosition(int position){
        return bellyList.get(position);
    }
}
