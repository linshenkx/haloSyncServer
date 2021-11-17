package cn.linshenkx.halosyncserver.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "halo-sync.hexo.git")
public class HexoGitProp {

    @NotNull
    private boolean isPrivate;

    @NotNull
    private String url;

    private String username;

    private String password;

    @NotNull
    private String branch;

    @NotNull
    private String sourceDir;

    @NotNull
    private boolean init;
}
