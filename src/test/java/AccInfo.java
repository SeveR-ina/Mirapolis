import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccInfo {

    protected String login;
    protected String pass;
    protected String fullName;
}
