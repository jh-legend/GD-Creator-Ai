package com.liveinaura.gdcreator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.liveinaura.gdcreator.R;
import com.liveinaura.gdcreator.models.Doc;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.DocViewHolder> {

    private final List<Doc> docList;
    private final Context context;
    private final OnDocClickListener onDocClickListener;

    public DocsAdapter(Context context, List<Doc> docList, OnDocClickListener onDocClickListener) {
        this.context = context;
        this.docList = docList;
        this.onDocClickListener = onDocClickListener;
    }

    @NonNull
    @Override
    public DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doc, parent, false);
        return new DocViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocViewHolder holder, int position) {
        Doc doc = docList.get(position);
        holder.fileNameTextView.setText(doc.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.fileDateTextView.setText(sdf.format(doc.getDate()));

        holder.itemView.setOnClickListener(v -> {
            onDocClickListener.onDocClick(doc);
        });
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    static class DocViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;
        TextView fileDateTextView;

        public DocViewHolder(@NonNull View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
            fileDateTextView = itemView.findViewById(R.id.fileDateTextView);
        }
    }

    public interface OnDocClickListener {
        void onDocClick(Doc doc);
    }
}
