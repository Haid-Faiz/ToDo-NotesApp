package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todonotes.R;

import java.util.List;

import MyInterface.CustomViewClickListener;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<NotesData> list;
    CustomViewClickListener customViewClickListener;

    public NotesAdapter(List<NotesData> list, CustomViewClickListener customViewClickListener){
        this.list = list;
        this.customViewClickListener = customViewClickListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {

        NotesData notesData = list.get(position);

        holder.Title.setText(notesData.getTitle());
        holder.Desc.setText(notesData.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customViewClickListener.onClick(notesData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title, Desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.customTitleID);
            Desc = itemView.findViewById(R.id.customDescID);
        }
    }
}
