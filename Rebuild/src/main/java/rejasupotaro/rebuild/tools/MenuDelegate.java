package rejasupotaro.rebuild.tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import javax.inject.Inject;

import rejasupotaro.rebuild.R;
import rejasupotaro.rebuild.activities.SettingsActivity;
import rejasupotaro.rebuild.events.BusProvider;
import rejasupotaro.rebuild.events.ClearEpisodeCacheEvent;
import rejasupotaro.rebuild.models.Episode;
import rejasupotaro.rebuild.services.EpisodeDownloadService;
import rejasupotaro.rebuild.utils.IntentUtils;

public class MenuDelegate {

    private Activity activity;

    @Inject
    public MenuDelegate(Activity activity) {
        this.activity = activity;
    }

    public void pressHome() {
        activity.finish();
    }

    public void pressSettings() {
        activity.startActivity(SettingsActivity.createIntent(activity));
    }

    public void pressShare(Episode episode) {
        IntentUtils.shareEpisode(activity, episode);
    }

    public void pressDownloadOrClearCache(Episode episode) {
        if (episode.isDownloaded()) {
            episode.clearCache();
            episode.save();
            BusProvider.getInstance().post(new ClearEpisodeCacheEvent());
        } else {
            EpisodeDownloadService.startDownload(activity, episode);
        }
    }
}
