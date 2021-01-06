package models;

/**
 * POJO skeleton to make fulfilled requests pretty and play nice
 *
 *
 * @author August Duet
 *
 */
public class Response {
    private String type;
    private Object body;
    private String message;

    public Response() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
