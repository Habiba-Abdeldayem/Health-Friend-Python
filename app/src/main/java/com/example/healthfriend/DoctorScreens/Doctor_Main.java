package com.example.healthfriend.DoctorScreens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.R;

public class Doctor_Main extends AppCompatActivity implements UserList.OnItemClickListener,UserList.OnItemLongClickListener {
   // private ArrayList<Product> products;
    //MarketoDb database;
    UserList adapter;



    Button b1,feed;
    //private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
//        ArrayList<User> users=new ArrayList<User>();
//        users.add(new User(150,50,R.drawable.ff,"shimaa"));
//        users.add(new User(160,80,R.drawable.car4,"aya"));
//        users.add(new User(170,60,R.drawable.ff,"ahmed"));
//       // database=MarketoDb.getInstance(getApplicationContext());
//       // categories = database.getCategories();
//        // List<Product> products = database.getproductList();
//        RecyclerView recyclerView = findViewById(R.id.rv_userList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new UserList(users, this,this);
//        recyclerView.setAdapter(adapter);
       // b1=findViewById(R.id.search_btn);
        //feed=findViewById(R.id.feed);
//        feed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startActivity(new Intent(AvailableProductActivity.this,ComplainActivity.class));
//            }
//        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i15 = new Intent(getApplicationContext(), search.class);
//                i15.putExtra("user_id",1);
//                startActivity(i15);
//            }
//        });

    }





    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Title")
                .setMessage("This is a message for the user.")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action to be performed when the "Accept" button is clicked
                        // You can put your code here
                        dialog.dismiss(); // Dismiss the dialog
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action to be performed when the "Cancel" button is clicked
                        // You can put your code here
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

        // Create and show the AlertDialog
//        AlertDialog alertDialog = builder.create();
        builder.show();
    }

    @Override
    public void onItemClick(User user) {
//        Intent intent = new Intent(getApplicationContext(), Products.class);
//        intent.putExtra("id",category.getId());
//        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User category) {

    }
}