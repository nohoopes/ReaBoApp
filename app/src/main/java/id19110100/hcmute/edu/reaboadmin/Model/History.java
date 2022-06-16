package id19110100.hcmute.edu.reaboadmin.Model;

public class History {
    String id;
    ModelPdf Books;
    String uid;
    Long timestamp;

    public History() {
    }

    public History(String id, ModelPdf Books, String uid, Long timestamp) {
        this.id = id;
        this.Books = Books;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelPdf getBooks() {
        return Books;
    }

    public void setBooks(ModelPdf books) {
        Books = books;
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

