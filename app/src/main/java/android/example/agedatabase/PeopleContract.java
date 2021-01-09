package android.example.agedatabase;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class PeopleContract {

    private PeopleContract(){}

    public static final String CONTENT_AUTHORITY = "android.example.agedatabase";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PEOPLE = "people";

    public static final class PersonEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PEOPLE);
        public static final String TABLE_NAME = "people";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PERSON_NAME = "name";
        public static final String COLUMN_PERSON_AGE = "age";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PEOPLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PEOPLE;
    }

}
