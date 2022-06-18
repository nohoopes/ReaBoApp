package id19110100.hcmute.edu.reaboadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id19110100.hcmute.edu.reaboadmin.Class.MyApplication;
import id19110100.hcmute.edu.reaboadmin.Fragment.MyProfileFragment;

public class EbookActivity extends AppCompatActivity {
    //variables
    PlayerView pvEbookController;
    ExoPlayer player;
    ImageButton next, previous, back;
    PDFView imgPDF;
    TextView titleName;

    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        //get bookId from intent
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        //mapping
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        imgPDF = findViewById(R.id.img_pdf_audio_book);
        titleName = findViewById(R.id.txt_name_ebook);
        back = findViewById(R.id.btn_back_ebook);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(player.getCurrentPosition() + 5000);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.getCurrentPosition() > 5000)
                    player.seekTo(player.getCurrentPosition() - 5000);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadBookView(); //load firebase to do stuff

    }

    //load from firebase to take data to pdfview, textview and audio player
    private void loadBookView() {

        //database ref to load from firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get book url
                        String pdfUrl = "" + snapshot.child("url").getValue();
                        //load pdf from firebase
                        MyApplication.loadFirstPage(pdfUrl, imgPDF);

                        String title = "" + snapshot.child("title").getValue();

                        titleName.setText(title);

                        String audiourl = "" + snapshot.child("audiobookurl").getValue();

                        PlayMedia(audiourl);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    //play media from url
    public void PlayMedia(String url)
    {
        //ExoPlayer
        player = new ExoPlayer.Builder(this).build();

        //mapping
        pvEbookController = findViewById(R.id.pvControllerBar);

        //set player
        pvEbookController.setPlayer(player);

        //get data from url
        Uri uriOfContentUrl = Uri.parse(url);
        MediaItem Item = MediaItem.fromUri(uriOfContentUrl);

        //add to player
        player.addMediaItem(Item);
        player.prepare();

        //play
        player.play();
        pvEbookController.setControllerShowTimeoutMs(0);
        pvEbookController.showController();
        pvEbookController.setControllerHideOnTouch(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop(true); //stop when exit activity
    }
}