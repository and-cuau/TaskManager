package com.example.andresapp;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskManagerDAO_Impl implements TaskManagerDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<taskManagerModel> __insertionAdapterOftaskManagerModel;

  private final EntityDeletionOrUpdateAdapter<taskManagerModel> __deletionAdapterOftaskManagerModel;

  private final EntityDeletionOrUpdateAdapter<taskManagerModel> __updateAdapterOftaskManagerModel;

  public TaskManagerDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOftaskManagerModel = new EntityInsertionAdapter<taskManagerModel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Task` (`task_id`,`task`,`date`,`time`,`image`,`is_selected`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final taskManagerModel entity) {
        statement.bindLong(1, entity.task_id);
        if (entity.getTask() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTask());
        }
        if (entity.date == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.date);
        }
        if (entity.time == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.time);
        }
        statement.bindLong(5, entity.image);
        final int _tmp = entity.isSelected() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOftaskManagerModel = new EntityDeletionOrUpdateAdapter<taskManagerModel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Task` WHERE `task_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final taskManagerModel entity) {
        statement.bindLong(1, entity.task_id);
      }
    };
    this.__updateAdapterOftaskManagerModel = new EntityDeletionOrUpdateAdapter<taskManagerModel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Task` SET `task_id` = ?,`task` = ?,`date` = ?,`time` = ?,`image` = ?,`is_selected` = ? WHERE `task_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final taskManagerModel entity) {
        statement.bindLong(1, entity.task_id);
        if (entity.getTask() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTask());
        }
        if (entity.date == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.date);
        }
        if (entity.time == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.time);
        }
        statement.bindLong(5, entity.image);
        final int _tmp = entity.isSelected() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.task_id);
      }
    };
  }

  @Override
  public void addTask(final taskManagerModel task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOftaskManagerModel.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(final taskManagerModel task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOftaskManagerModel.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePerson(final taskManagerModel task) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOftaskManagerModel.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<taskManagerModel> getAllTask() {
    final String _sql = "select * from task";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfTask = CursorUtil.getColumnIndexOrThrow(_cursor, "task");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "is_selected");
      final List<taskManagerModel> _result = new ArrayList<taskManagerModel>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final taskManagerModel _item;
        final String _tmpTask;
        if (_cursor.isNull(_cursorIndexOfTask)) {
          _tmpTask = null;
        } else {
          _tmpTask = _cursor.getString(_cursorIndexOfTask);
        }
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpTime;
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _tmpTime = null;
        } else {
          _tmpTime = _cursor.getString(_cursorIndexOfTime);
        }
        final int _tmpImage;
        _tmpImage = _cursor.getInt(_cursorIndexOfImage);
        _item = new taskManagerModel(_tmpTask,_tmpDate,_tmpTime,_tmpImage);
        _item.task_id = _cursor.getInt(_cursorIndexOfTaskId);
        final Boolean _tmpIsSelected;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfIsSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfIsSelected);
        }
        _tmpIsSelected = _tmp == null ? null : _tmp != 0;
        _item.setSelected(_tmpIsSelected);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public taskManagerModel getTask(final int task_id) {
    final String _sql = "select * from task where task_id==?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, task_id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTaskId = CursorUtil.getColumnIndexOrThrow(_cursor, "task_id");
      final int _cursorIndexOfTask = CursorUtil.getColumnIndexOrThrow(_cursor, "task");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfIsSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "is_selected");
      final taskManagerModel _result;
      if (_cursor.moveToFirst()) {
        final String _tmpTask;
        if (_cursor.isNull(_cursorIndexOfTask)) {
          _tmpTask = null;
        } else {
          _tmpTask = _cursor.getString(_cursorIndexOfTask);
        }
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpTime;
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _tmpTime = null;
        } else {
          _tmpTime = _cursor.getString(_cursorIndexOfTime);
        }
        final int _tmpImage;
        _tmpImage = _cursor.getInt(_cursorIndexOfImage);
        _result = new taskManagerModel(_tmpTask,_tmpDate,_tmpTime,_tmpImage);
        _result.task_id = _cursor.getInt(_cursorIndexOfTaskId);
        final Boolean _tmpIsSelected;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfIsSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfIsSelected);
        }
        _tmpIsSelected = _tmp == null ? null : _tmp != 0;
        _result.setSelected(_tmpIsSelected);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
