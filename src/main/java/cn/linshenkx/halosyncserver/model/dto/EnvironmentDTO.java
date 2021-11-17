package cn.linshenkx.halosyncserver.model.dto;

import cn.linshenkx.halosyncserver.model.enums.Mode;
import lombok.Data;

/**
 * Theme controller.
 *
 * @author ryanwang
 * @date 2019/5/4
 */
@Data
public class EnvironmentDTO {

    private String database;

    private Long startTime;

    private String version;

    private Mode mode;
}
