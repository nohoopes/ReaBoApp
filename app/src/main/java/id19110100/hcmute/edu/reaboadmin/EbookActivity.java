package id19110100.hcmute.edu.reaboadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class EbookActivity extends AppCompatActivity {

    PlayerView pvEbookController;
    ExoPlayer player;
    ImageButton next, previous, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

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

        back = findViewById(R.id.btn_back_ebook);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        PlayMedia("https://firebasestorage.googleapis.com/v0/b/spotityuser.appspot.com/o/y2mate.com%20-%20The%20Kid%20LAROI%20Justin%20Bieber%20%20Stay%20Lyrics%20(1).mp3?alt=media&token=c0290441-dc3e-4e75-972a-4cb1c04821b4");

    }

    public void PlayMedia(String url)
    {
        player = new ExoPlayer.Builder(this).build();

        pvEbookController = findViewById(R.id.pvControllerBar);

        pvEbookController.setPlayer(player);

        Uri uriOfContentUrl = Uri.parse(url);
        MediaItem Item = MediaItem.fromUri(uriOfContentUrl);

        player.addMediaItem(Item);
        player.prepare();

        player.play();
        pvEbookController.setControllerShowTimeoutMs(0);
        pvEbookController.showController();
        pvEbookController.setControllerHideOnTouch(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop(true);
    }
}