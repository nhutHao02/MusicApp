package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayListItem extends AppCompatActivity {
    DatabaseReference mDatabase;

    List<Song> myFavorites=new ArrayList<Song>();
    ListView lvSong;
    ListViewSongAdapter adapter=null;
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
        adapter=new ListViewSongAdapter(PlayListItem.this,R.layout.list_view_item,myFavorites);
        lvSong.setAdapter(adapter);

        //event list View
        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = (Song) lvSong.getItemAtPosition(i);
                startActivity(new Intent(PlayListItem.this,PlayerActivity.class)
                        .putExtra("MyListSong", (Serializable) myFavorites)
                        .putExtra("pos",i));

            }
        });
        ImageView listPlay = (ImageView) findViewById(R.id.icPlayList);
        listPlay.setOnClickListener(new AdapterView.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Tạo Intent để chuyển đến Activity mục tiêu
                Intent intent = new Intent(PlayListItem.this, PlayListActivity.class);
                // Chuyển đến Activity mục tiêu
                startActivity(intent);
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
    }
}