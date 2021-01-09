package android.example.agedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ResultsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_results,container,false);

        TextView results = (TextView) v.findViewById(R.id.textResults);
        String list = "";

        String[] projection = {
                PeopleContract.PersonEntry._ID,
                PeopleContract.PersonEntry.COLUMN_PERSON_NAME,
                PeopleContract.PersonEntry.COLUMN_PERSON_AGE };
        Cursor cursor =
                getActivity().getContentResolver().query(PeopleContract.PersonEntry.CONTENT_URI,
                        projection, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            list += cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_NAME))
                    + " - " + cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_AGE))
                    + "\n";
            while (cursor.moveToNext()) {
                list += cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_NAME))
                        + " - " + cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_AGE))
                        + "\n";
            }

            results.setText(list);
        }

        return v;
    }
}
