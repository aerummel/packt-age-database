package android.example.agedatabase;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class DeleteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_delete, container, false);

        Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        final EditText editName = (EditText) v.findViewById(R.id.editDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();

                if (name.matches("")) {
                    throw new IllegalArgumentException("Delete requires a valid name");
                }

                String[] args = {name};

                int rowsDeleted =
                        getActivity().getContentResolver().delete(PeopleContract.PersonEntry.CONTENT_URI,
                                PeopleContract.PersonEntry.COLUMN_PERSON_NAME + "=?", args);

                if (rowsDeleted != 0) {
                    Toast.makeText(getContext(), R.string.toast_delete_success,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
