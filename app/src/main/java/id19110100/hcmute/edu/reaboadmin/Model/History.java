package id19110100.hcmute.edu.reaboadmin.Model;

public class History {
    String id;
    ModelPdf pdf;
    String uid;
    Long timestamp;

    public History() {
    }

    public History(String id, ModelPdf pdf, String uid, Long timestamp, String url) {
        this.id = id;
        this.pdf = pdf;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelPdf getPdf() {
        return pdf;
    }

    public void setPdf(ModelPdf pdf) {
        this.pdf = pdf;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

