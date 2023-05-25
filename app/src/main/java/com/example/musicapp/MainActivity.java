package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    List<Song> myFavorites=new ArrayList<Song>();
    ListView lvSong;
    ListViewSongAdapter adapter=null;
    LinearLayout lyMore, lySearch, lyPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //ánh xạ
        anhXa();
        //Load data
        loadSongs();
        lvSong=(ListView) findViewById(R.id.listViewId);
        adapter=new ListViewSongAdapter(MainActivity.this,R.layout.list_view_item,myFavorites);
        lvSong.setAdapter(adapter);

        //event list View
        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = (Song) lvSong.getItemAtPosition(i);

                startActivity(new Intent(MainActivity.this,PlayerActivity.class)
                        .putExtra("MyListSong", (Serializable) myFavorites)
                        .putExtra("pos",i));

            }
        });
        lyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MoreActivity.class));
            }
        });



    }

    private void loadSongs() {
        mDatabase.child("myFavorites").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Song songs=snapshot.getValue(Song.class);
                myFavorites.add(songs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void anhXa() {
        lyMore=(LinearLayout) findViewById(R.id.lyMore);
        lySearch=(LinearLayout) findViewById(R.id.lySearch);
        lyPlaylist=(LinearLayout) findViewById(R.id.lyPlayList);
    }

}