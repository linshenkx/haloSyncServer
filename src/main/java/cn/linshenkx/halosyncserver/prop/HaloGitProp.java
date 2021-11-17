package cn.linshenkx.halosyncserver.prop;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "halo-sync.halo")
public class HaloGitProp {

    @NotNull
    private String url;

    private String username;

    private String password;
}
