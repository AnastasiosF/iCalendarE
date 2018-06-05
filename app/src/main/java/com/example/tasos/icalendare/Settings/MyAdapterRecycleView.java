package com.example.tasos.icalendare.Settings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasos.icalendare.R;
import com.example.tasos.icalendare.database.TypeOfEvent;

import java.util.List;

public class MyAdapterRecycleView extends RecyclerView.Adapter<MyAdapterRecycleView.ViewHolder> {

    private List<TypeOfEvent> values;

    public MyAdapterRecycleView(List<TypeOfEvent> values) {
        this.values = values;
    }

    public void add(int position, TypeOfEvent item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public TextView txtCost;
        public TextView txtDuration;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = (TextView) v.findViewById(R.id.type_title);
            txtCost = (TextView) v.findViewById(R.id.cost);
            txtDuration = (TextView) v.findViewById(R.id.duration);

        }
    }

        @NonNull
        @Override
        public MyAdapterRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(
                    parent.getContext());
            View v = inflater.inflate(R.layout.type_list_row, parent, false);
            // set the view's size, margins, paddings and layout parameters
            v.setPadding(15,15,15,15);
            v.setMinimumHeight(25);
            ViewHolder vh = new ViewHolder(v);
            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapterRecycleView.ViewHolder holder, int position) {

            final TypeOfEvent typeOfEvent = values.get(position);


            holder.txtTitle.setText(typeOfEvent.getTitle());
            holder.txtCost.setText(String.valueOf(typeOfEvent.getPrice()));
            holder.txtDuration.setText(typeOfEvent.getDuration());

            holder.txtCost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //holder.txtDuration.setText("Duration");

                }
            });

        }

        @Override
        public int getItemCount() {
            return values.size();

        }
    }
