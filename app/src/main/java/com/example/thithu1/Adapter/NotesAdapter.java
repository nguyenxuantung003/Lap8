package com.example.thithu1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thithu1.Model.Note;
import com.example.thithu1.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notes;
    private final NoteClickListener noteClickListener;
    private final NoteDeleteListener noteDeleteListener;

    public NotesAdapter(List<Note> notes, NoteClickListener noteClickListener, NoteDeleteListener noteDeleteListener) {
        this.notes = notes;
        this.noteClickListener = noteClickListener;
        this.noteDeleteListener = noteDeleteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public interface NoteClickListener {
        void onNoteClick(Note note);
    }

    public interface NoteDeleteListener {
        void onNoteDelete(Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteContent;
        private final TextView noteDate;

        NoteViewHolder(View itemView) {
            super(itemView);
            noteContent = itemView.findViewById(R.id.noteContent);
            noteDate = itemView.findViewById(R.id.noteDate);
        }

        void bind(Note note) {
            noteContent.setText(note.getContent());
            noteDate.setText(note.getDate());
            itemView.setOnClickListener(v -> noteClickListener.onNoteClick(note));
        }
    }
}