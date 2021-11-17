package cn.linshenkx.halosyncserver.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Base meta Dto.
 *
 * @author ryanwang
 * @date 2019-12-10
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseMetaDTO {
    private Long id;

    private Integer postId;

    private String key;

    private String value;

    private Date createTime;
}
