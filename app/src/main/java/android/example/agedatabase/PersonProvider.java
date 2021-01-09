package android.example.agedatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.example.agedatabase.R;

public class PersonProvider extends ContentProvider {

    private static final int PEOPLE = 100;
    private static final int PERSON = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PeopleContract.CONTENT_AUTHORITY, PeopleContract.PATH_PEOPLE, PEOPLE);
        sUriMatcher.addURI(PeopleContract.CONTENT_AUTHORITY,  PeopleContract.PATH_PEOPLE + "/#",
                PERSON);
    }

    private PeopleDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new PeopleDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PEOPLE:
                cursor = db.query(PeopleContract.PersonEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case PERSON:
                selection = PeopleContract.PersonEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(PeopleContract.PersonEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PEOPLE:
                return PeopleContract.PersonEntry.CONTENT_LIST_TYPE;
            case PERSON:
                return PeopleContract.PersonEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PEOPLE:
                return insertPerson(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PEOPLE:
                rowsDeleted = db.delete(PeopleContract.PersonEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case PERSON:
                selection = PeopleContract.PersonEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(PeopleContract.PersonEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PEOPLE:
                return updatePeople(uri, values, selection, selectionArgs);
            case PERSON:
                selection = PeopleContract.PersonEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updatePeople(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private Uri insertPerson(Uri uri, ContentValues values) {
        String name = values.getAsString(PeopleContract.PersonEntry.COLUMN_PERSON_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Person requires a name");
        }

        Integer age = values.getAsInteger(PeopleContract.PersonEntry.COLUMN_PERSON_AGE);
        if (age != null && age <= 0) {
            age = age * -1;
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(PeopleContract.PersonEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Toast.makeText(getContext(), R.string.toast_insert_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast. makeText(getContext(), R.string.toast_insert_success, Toast.LENGTH_SHORT).show();
        }

        return ContentUris.withAppendedId(uri, id);
    }

    private int updatePeople(Uri uri, ContentValues values, String selection,
                             String[] selectionArgs) {
        // Update function not needed
        return 0;
    }
}
