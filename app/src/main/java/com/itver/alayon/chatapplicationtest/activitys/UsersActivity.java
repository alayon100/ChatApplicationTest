package com.itver.alayon.chatapplicationtest.activitys;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itver.alayon.chatapplicationtest.R;
import com.itver.alayon.chatapplicationtest.models.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {

    //FIREBASE
    private DatabaseReference reference;


    //UI ELEMENTS
    private Toolbar toolbar;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initComponents();
    }

    private void initComponents() {

        reference = FirebaseDatabase.getInstance().getReference().child("usuarios");

        toolbar = (Toolbar) findViewById(R.id.usersToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All users");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewListUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, UsersViewHolder> fireBaseAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>
                (User.class, R.layout.recycler_view_users_list_item,
                        UsersViewHolder.class, reference) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, User model, int position) {

                viewHolder.bindData(model);
            }
        };

        recyclerView.setAdapter(fireBaseAdapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageViewProfile;
        private TextView userNameProfile;
        private TextView userStatusProfile;
        private Context context;

        public UsersViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            circleImageViewProfile = (CircleImageView) itemView.findViewById(R.id.userImageProfile);
            userNameProfile = (TextView) itemView.findViewById(R.id.userNameProfile);
            userStatusProfile = (TextView) itemView.findViewById(R.id.userStatusProfile);
        }

        public void bindData(User user) {

            if (!user.getProfile_image().equals("default")) {
                Picasso.with(context).load(user.getProfile_image()).into(circleImageViewProfile);
            }
            userNameProfile.setText(user.getUser_name());
            userStatusProfile.setText(user.getStatus());
        }


    }
}

