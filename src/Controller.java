import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Controller implements Serializable {

    private List<String> list;

    public Controller(){
        this.list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
