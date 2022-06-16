package id19110100.hcmute.edu.reaboadmin.Model;

public class Library {
    String id;
    ModelPdf pdf;
    String uid;

    public Library() {
    }

    public Library(String id, ModelPdf pdf, String uid) {
        this.id = id;
        this.pdf = pdf;
        this.uid = uid;
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
}
