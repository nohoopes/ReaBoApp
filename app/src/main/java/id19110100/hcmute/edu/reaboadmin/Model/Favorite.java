package id19110100.hcmute.edu.reaboadmin.Model;

public class Favorite {
    String id;
    ModelPdf Books;
    String uid;

    public Favorite() {
    }

    public Favorite(String id, ModelPdf Books, String uid) {
        this.id = id;
        this.Books = Books;
        this.uid = uid;
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
}
