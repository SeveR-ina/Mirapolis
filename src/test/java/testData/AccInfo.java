package testData;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccInfo {

    private String login;
    private String pass;
    private String fullName;
}
