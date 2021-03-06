package rejasupotaro.rebuild.models;

import java.util.Date;

import rejasupotaro.rebuild.utils.DateUtils;
import twitter4j.Status;

public class Tweet {

    private long id;

    private Date createdAt;

    private String userImageUrl;

    private String userName;

    private String text;

    private int favoriteCount;

    private int retweetCount;

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtText() {
        return DateUtils.createdAtToString(createdAt);
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getText() {
        return text;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public Tweet(long id, Date createdAt, String userImageUrl, String userName, String text,
            int favoriteCount, int retweetCount) {
        this.id = id;
        this.createdAt = createdAt;
        this.userImageUrl = userImageUrl;
        this.userName = "@" + userName;
        this.text = text;
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
    }

    public static Tweet fromStatus(Status status) {
        return new Tweet(
                status.getId(),
                status.getCreatedAt(),
                status.getUser().getProfileImageURL(),
                status.getUser().getScreenName(),
                status.getText(),
                status.getFavoriteCount(),
                status.getRetweetCount());
    }
}
