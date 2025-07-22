package com.liveinaura.gdcreator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liveinaura.gdcreator.adapters.DocsAdapter;
import com.liveinaura.gdcreator.models.Doc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocsFragment extends Fragment implements DocsAdapter.OnDocClickListener {

    private RecyclerView docsRecyclerView;
    private TextView noDocsTextView;
    private DocsAdapter docsAdapter;
    private List<Doc> docList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs, container, false);

        docsRecyclerView = view.findViewById(R.id.docsRecyclerView);
        noDocsTextView = view.findViewById(R.id.noDocsTextView);

        docList = new ArrayList<>();
        docsAdapter = new DocsAdapter(getContext(), docList, this);
        docsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        docsRecyclerView.setAdapter(docsAdapter);

        if (PermissionUtils.hasStoragePermission(getActivity())) {
            loadDocuments();
        } else {
            PermissionUtils.requestStoragePermission(getActivity());
        }

        return view;
    }

    private void loadDocuments() {
        docList.clear();
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "GD-Creator");
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".pdf")) {
                        docList.add(new Doc(file.getName(), file.getAbsolutePath(), file.lastModified()));
                    }
                }
            }
        }

        if (docList.isEmpty()) {
            noDocsTextView.setVisibility(View.VISIBLE);
            docsRecyclerView.setVisibility(View.GONE);
        } else {
            noDocsTextView.setVisibility(View.GONE);
            docsRecyclerView.setVisibility(View.VISIBLE);
            docsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDocClick(Doc doc) {
        File file = new File(doc.getPath());
        Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application found to open PDF file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadDocuments();
            } else {
                Toast.makeText(getContext(), "Storage permission is required to view documents", Toast.LENGTH_SHORT).show();
            }
        }
    }
}