package rejasupotaro.rebuild.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import rejasupotaro.rebuild.R;
import rejasupotaro.rebuild.media.PodcastPlayer;
import rejasupotaro.rebuild.models.Episode;
import rejasupotaro.rebuild.notifications.PodcastPlayerNotification;

public class MediaBarView extends FrameLayout {

    private View rootView;

    private TextView episodeTitleTextView;

    private CheckBox mediaPlayAndPauseButton;

    private View mediaStopButton;

    public MediaBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public void setup() {
        rootView = View.inflate(getContext(), R.layout.media_bar_view, null);
        findViews(rootView);
        setupViews();
        addView(rootView);
    }

    private void findViews(View view) {
        episodeTitleTextView = (TextView) view.findViewById(R.id.episode_title_text);
        mediaPlayAndPauseButton = (CheckBox) view.findViewById(R.id.media_play_and_pause_button);
        mediaStopButton = view.findViewById(R.id.media_stop_button);
    }

    private void setupViews() {
        mediaStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PodcastPlayer.getInstance().stop();
                hide();
            }
        });
    }

    private void setupMediaPlayAndPauseButton(final Episode episode) {
        if (PodcastPlayer.getInstance().isPlaying()) {
            mediaPlayAndPauseButton.setChecked(true);
        } else {
            mediaPlayAndPauseButton.setChecked(false);
        }

        mediaPlayAndPauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayAndPauseButton.isChecked()) {
                    start(episode);
                } else {
                    pause(episode);
                }
            }
        });
    }

    private void start(final Episode episode) {
        final PodcastPlayer podcastPlayer = PodcastPlayer.getInstance();
        if (shouldRestart(episode)) {
            podcastPlayer.start();
            PodcastPlayerNotification.notify(getContext(), episode);
        } else {
            mediaPlayAndPauseButton.setEnabled(false);
            podcastPlayer.start(getContext(), episode, new PodcastPlayer.StateChangedListener() {
                @Override
                public void onStart() {
                    if (getContext() == null) {
                        pause(episode);
                    } else {
                        PodcastPlayerNotification.notify(getContext(), episode);
                    }
                }
            });
        }
    }

    private boolean shouldRestart(Episode episode) {
        PodcastPlayer podcastPlayer = PodcastPlayer.getInstance();
        return (podcastPlayer.isPlayingEpisode(episode)
                && podcastPlayer.getCurrentPosition() > 0);
    }

    private void pause(final Episode episode) {
        final PodcastPlayer podcastPlayer = PodcastPlayer.getInstance();
        podcastPlayer.pause();
        PodcastPlayerNotification
                .notify(getContext(), episode, PodcastPlayer.getInstance().getCurrentPosition());
    }

    public void setEpisode(final Episode episode, final OnMediaBarClickListener listener) {
        PodcastPlayer podcastPlayer = PodcastPlayer.getInstance();
        if (episode == null
                || !((podcastPlayer.isPlaying() || podcastPlayer.isPaused()))) {
            gone();
            return;
        }

        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(episode);
            }
        });
        show(episode);
    }

    private void show(Episode episode) {
        setupMediaPlayAndPauseTextView(episode);
        setupMediaPlayAndPauseButton(episode);

        if (rootView.getVisibility() == View.GONE) {
            rootView.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_enter);
            animation.setFillAfter(true);
            animation.setFillEnabled(true);
            rootView.startAnimation(animation);
        }
    }

    private void setupMediaPlayAndPauseTextView(Episode episode) {
        episodeTitleTextView.setText(episode.getTitle());
        episodeTitleTextView.setSelected(true);
        episodeTitleTextView.requestFocus();
    }

    private void hide() {
        rootView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_exit);
        animation.setFillAfter(true);
        animation.setFillEnabled(true);
        rootView.startAnimation(animation);
    }

    private void gone() {
        rootView.setVisibility(View.GONE);
    }

    public static interface OnMediaBarClickListener {
        public void onClick(Episode episode);
    }
}
