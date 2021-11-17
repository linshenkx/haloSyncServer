package cn.linshenkx.halosyncserver.model.dto;

import cn.linshenkx.halosyncserver.model.enums.LogType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author johnniang
 * @date 3/19/19
 */
@Data
@ToString
@EqualsAndHashCode
public class LogDTO {

    private Long id;

    private String logKey;

    private LogType type;

    private String content;

    private String ipAddress;

    private Date createTime;
}
