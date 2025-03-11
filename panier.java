import java.util.ArrayList;
import java.util.List;

public class panier {
    private String user; 
    private List<Integer> items = new ArrayList();

    public panier(String user) {
        this.user = user;
    }
    public List<Integer> getItems() {
        return items;
    }
    public void setItems(List<Integer> items) {
        this.items = items;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}