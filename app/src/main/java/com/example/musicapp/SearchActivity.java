package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    ListView lvTracks1,lvTracks2, lvTracks3,lvNewTracks1,lvNewTracks2;
    List<Song> listTopTracks=new ArrayList<Song>();
    List<Song> listTopTracksItem1=new ArrayList<Song>();
    List<Song> listTopTracksItem2=new ArrayList<Song>();
    List<Song> listTopTracksItem3=new ArrayList<Song>();
    ListViewSongAdapter adapter=null;
    ListViewSongAdapter adapter2=null;
    ListViewSongAdapter adapter3=null;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        anhXa();
        loadSongs();

        adapter=new ListViewSongAdapter(SearchActivity.this,R.layout.list_view_search,listTopTracksItem1);
        lvTracks1.setAdapter(adapter);
        adapter2=new ListViewSongAdapter(SearchActivity.this,R.layout.list_view_search,listTopTracksItem2);
        lvTracks2.setAdapter(adapter2);
        lvNewTracks1.setAdapter(adapter2);
        adapter3=new ListViewSongAdapter(SearchActivity.this,R.layout.list_view_search,listTopTracksItem3);
        lvTracks3.setAdapter(adapter3);
        lvNewTracks2.setAdapter(adapter3);

    }
    private void loadSongs() {
        mDatabase.child("myFavorites").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Song songs=snapshot.getValue(Song.class);
                listTopTracks.add(songs);
                index++;
                if (index<5){
                    listTopTracksItem1.add(songs);
                }
                if (index>=5 && index<9){
                    listTopTracksItem2.add(songs);
                }
                if (index>=9&&index<13){
                    listTopTracksItem3.add(songs);
                }
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
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
        lvTracks1=(ListView) findViewById(R.id.lvTopTracks1);
        lvTracks2=(ListView) findViewById(R.id.lvTopTracks2);
        lvTracks3=(ListView) findViewById(R.id.lvTopTracks3);
        lvNewTracks1=(ListView) findViewById(R.id.lvTopNewTracks1);
        lvNewTracks2=(ListView) findViewById(R.id.lvTopNewTracks2);
    }
}