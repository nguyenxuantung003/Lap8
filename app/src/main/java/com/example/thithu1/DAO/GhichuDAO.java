package com.example.thithu1.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thithu1.Dd.db;
import com.example.thithu1.Model.Note;

import java.util.ArrayList;
import java.util.List;

public class GhichuDAO {
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public GhichuDAO(Context context) {
        dbHelper = new db(context);
        database = dbHelper.getWritableDatabase();
    }

    // Thêm ghi chú
    public void addNoteAndRefresh(String content, String date) {
        ContentValues values = new ContentValues();
        values.put(db.COLUMN_CONTENT, content);
        values.put(db.COLUMN_DATE, date);

        // Thêm ghi chú vào cơ sở dữ liệu và lấy ID của ghi chú mới thêm
        long newNoteId = database.insert(db.TABLE_NOTES, null, values);

        // Kiểm tra xem việc thêm ghi chú có thành công không
        if (newNoteId != -1) {
            // Lấy dữ liệu mới sau khi thêm ghi chú
            List<Note> updatedNotes = getAllNotes();
            // Xử lý dữ liệu mới (ví dụ: cập nhật giao diện người dùng)
            // ...
        } else {
            // Xử lý lỗi khi thêm ghi chú
            // ...
        }
    }

    // Xóa ghi chú theo ID
    public void deleteNote(long id) {
        database.delete(db.TABLE_NOTES, db.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }


    // Lấy tất cả ghi chú
    @SuppressLint("Range")
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        // Thay đổi câu lệnh truy vấn để sắp xếp theo ngày giảm dần
        Cursor cursor = database.query(db.TABLE_NOTES,
                new String[]{db.COLUMN_ID, db.COLUMN_CONTENT, db.COLUMN_DATE},
                null, null, null, null,
                db.COLUMN_DATE + " DESC"); // Sắp xếp theo ngày giảm dần

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(db.COLUMN_ID));
                String content = cursor.getString(cursor.getColumnIndex(db.COLUMN_CONTENT));
                String date = cursor.getString(cursor.getColumnIndex(db.COLUMN_DATE));
                notes.add(new Note(id, content, date));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    // Đóng cơ sở dữ liệu
    public void close() {
        database.close();
        dbHelper.close();
    }
}
