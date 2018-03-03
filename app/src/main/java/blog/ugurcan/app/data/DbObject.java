package blog.ugurcan.app.data;

import blog.ugurcan.firedroid.db.FireDbObject;

/**
 * Created by ugurcan on 21.01.2018.
 */
public class DbObject extends FireDbObject {

    private int id;
    private String text;

    public DbObject() {
    }

    public DbObject(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "DbObject{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", timestamp='" + timestamp() + '\'' +
                '}';
    }

}
