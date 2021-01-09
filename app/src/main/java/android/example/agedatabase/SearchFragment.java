package android.example.agedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_search,container,false);

        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        final EditText searchName = (EditText) v.findViewById(R.id.editSearch);
        TextView result = (TextView) v.findViewById(R.id.textResult);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = searchName.getText().toString().trim();

                if (name.matches("")) {
                    throw new IllegalArgumentException("Search requires a valid name");
                }

                String[] args = {name};
                String[] projection = {
                        PeopleContract.PersonEntry._ID,
                        PeopleContract.PersonEntry.COLUMN_PERSON_NAME,
                        PeopleContract.PersonEntry.COLUMN_PERSON_AGE };
                Cursor cursor =
                        getActivity().getContentResolver().query(PeopleContract.PersonEntry.CONTENT_URI,
                                projection, PeopleContract.PersonEntry.COLUMN_PERSON_NAME + "=?",
                                args, null);

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    result.setText("Result = " + cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_NAME))
                     + " - " + cursor.getString(cursor.getColumnIndex(PeopleContract.PersonEntry.COLUMN_PERSON_AGE)));
                }
            }
        });

        return v;
    }
}
