package com.example.thithu1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thithu1.Adapter.NotesAdapter;
import com.example.thithu1.DAO.GhichuDAO;
import com.example.thithu1.Fragment.ThemFragment;
import com.example.thithu1.Model.Note;
import com.example.thithu1.R;
import java.util.List;

public class DanhsachFragment extends Fragment implements ThemFragment.OnNoteAddedListener {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private GhichuDAO notesDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhsach, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesDao = new GhichuDAO(getContext());
        List<Note> notes = notesDao.getAllNotes();
        adapter = new NotesAdapter(notes, this::showNoteDetailDialog, this::deleteNote);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNotesList(); // Cập nhật danh sách khi fragment được hiển thị
    }

    private void updateNotesList() {
        List<Note> updatedNotes = notesDao.getAllNotes();
        adapter.updateNotes(updatedNotes);
    }

    private void showNoteDetailDialog(Note note) {
        new AlertDialog.Builder(getContext())
                .setTitle("Note Details")
                .setMessage("Content: " + note.getContent() + "\nDate: " + note.getDate())
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("Delete", (dialog, which) -> {
                    deleteNote(note);
                })
                .show();
    }

    private void deleteNote(Note note) {
        notesDao.deleteNote(note.getId()); // Xóa ghi chú
        updateNotesList(); // Cập nhật danh sách sau khi xóa
    }

    @Override
    public void onNoteAdded() {
        updateNotesList(); // Cập nhật danh sách khi ghi chú được thêm
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notesDao.close();
    }
}