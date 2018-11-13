package queue.blockQueue;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class Request {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name=" + name +
                "}";
    }
}
