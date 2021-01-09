package android.example.agedatabase;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class InsertFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_insert, container, false);

        Button btnInsert = (Button) v.findViewById(R.id.btnInsert);
        final EditText editName = (EditText) v.findViewById(R.id.editName);
        final EditText editAge = (EditText) v.findViewById(R.id.editAge);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String age = editAge.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(PeopleContract.PersonEntry.COLUMN_PERSON_NAME, name);
                values.put(PeopleContract.PersonEntry.COLUMN_PERSON_AGE, age);

                Uri uri =
                        getActivity().getContentResolver().insert(PeopleContract.PersonEntry.CONTENT_URI,
                        values);
            }
        });

        return v;
    }
}
