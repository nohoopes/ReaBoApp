package id19110100.hcmute.edu.reaboadmin.Model;

public class ModelPdf {
    //variables
    String uid, id, title, description, categoryId, license, url, audiobookurl;
    long timestamp;
    int viewCount, downloadCount;

    //empty constructor for firebase
    public ModelPdf() {
    }

    //constructor

    public ModelPdf(String uid, String id, String title, String description, String categoryId, String license, String url, long timestamp, int viewCount, int downloadCount) {
        this.uid = uid;
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.license = license;
        this.url = url;
        this.timestamp = timestamp;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
    }

    public ModelPdf(String uid, String id, String title, String description, String categoryId, String license, String url, long timestamp, int viewCount, int downloadCount, String audiobookurl) {
        this.uid = uid;
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.license = license;
        this.url = url;
        this.timestamp = timestamp;
        this.viewCount = viewCount;
        this.downloadCount = downloadCount;
        this.audiobookurl = audiobookurl;
    }

    //get/set
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getAudiobookurl() {
        return audiobookurl;
    }

    public void setAudiobookurl(String audiobookurl) {
        this.audiobookurl = audiobookurl;
    }
}
