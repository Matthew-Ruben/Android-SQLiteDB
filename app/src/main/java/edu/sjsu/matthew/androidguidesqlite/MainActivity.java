package edu.sjsu.matthew.androidguidesqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	
	DatabaseHelper myDb;
	
	EditText editFirstName, editLastName, editGrade, editId;
	Button btnAddData;
	Button btnViewAll;
	Button btnUpdate;
	Button btnDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myDb = new DatabaseHelper(this);
		
		editFirstName = (EditText) findViewById(R.id.editText_fname);
		editLastName = (EditText) findViewById(R.id.editText_lname);
		editGrade = (EditText) findViewById(R.id.editText_grade);
		editId = (EditText) findViewById(R.id.editText_id);
		btnAddData = (Button) findViewById(R.id.button_add);
		btnViewAll = (Button) findViewById(R.id.button_viewAll);
		btnUpdate = (Button) findViewById(R.id.button_update);
		btnDelete = (Button) findViewById(R.id.button_delete);
		
		addData();
		viewAll();
		updateData();
		deleteData();
	}
	
	public void deleteData() {
		btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Integer deletedRows = myDb.deleteData(editId.getText().toString());
				if (deletedRows > 0) {
					Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	public void updateData() {
		btnUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isUpdated = myDb.updateData(editId.getText().toString(),
						editFirstName.getText().toString(),
						editLastName.getText().toString(),
						editGrade.getText().toString() );
				
				if(isUpdated) {
					Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	public void addData() {
		btnAddData.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						boolean isInserted = myDb.insertData(editFirstName.getText().toString(),
								editLastName.getText().toString(),
								editGrade.getText().toString());
						if (isInserted) {
							Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
						}
					}
				}
		);
	}
	
	public void viewAll() {
		btnViewAll.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Cursor result = myDb.getAllData();
						if (result.getCount() == 0) { //there is no data for us to get
							//show (error) message
							showMessage("Error", "No data found");
							return;
						}
						
						StringBuffer buffer = new StringBuffer();
						while (result.moveToNext()) {
							buffer.append("Id: " + result.getString(0) + "\n");
							buffer.append("FirstName: " + result.getString(1) + "\n");
							buffer.append("LastName : " + result.getString(2) + "\n");
							buffer.append("Grade: " + result.getString(3) + "\n\n");
						}
						
						// Show all data
						showMessage("Data", buffer.toString());
					}
				}
		);
	}
	
	public void showMessage(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.show();
	}
	
}
