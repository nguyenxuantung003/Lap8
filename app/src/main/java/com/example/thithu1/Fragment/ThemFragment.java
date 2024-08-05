package com.example.thithu1.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.thithu1.DAO.GhichuDAO;
import com.example.thithu1.Model.Note;
import com.example.thithu1.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThemFragment extends Fragment {
    private OnNoteAddedListener onNoteAddedListener;

    public interface OnNoteAddedListener {
        void onNoteAdded();
    }

    public void setOnNoteAddedListener(OnNoteAddedListener listener) {
        this.onNoteAddedListener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them, container, false);

        EditText contentEditText = view.findViewById(R.id.contentEditText);
        Button saveButton = view.findViewById(R.id.addButton);

        saveButton.setOnClickListener(v -> {
            String content = contentEditText.getText().toString();
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            if (content.isEmpty()) {
                // Hiển thị thông báo lỗi hoặc yêu cầu người dùng nhập nội dung
                Toast.makeText(getContext(), "Content cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thực hiện thêm ghi chú vào cơ sở dữ liệu và làm mới danh sách
            GhichuDAO notesDao = new GhichuDAO(getContext());
            notesDao.addNoteAndRefresh(content, date);
            notesDao.close(); // Đóng DAO sau khi thêm ghi chú

            contentEditText.setText(""); // Xóa nội dung trường nhập

            // Gọi listener để thông báo rằng một ghi chú đã được thêm
            if (onNoteAddedListener != null) {
                onNoteAddedListener.onNoteAdded();
            }

            // Quay lại màn hình trước đó
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }
}