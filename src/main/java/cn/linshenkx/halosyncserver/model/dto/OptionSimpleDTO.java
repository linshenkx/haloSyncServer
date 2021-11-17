package cn.linshenkx.halosyncserver.model.dto;

import cn.linshenkx.halosyncserver.model.enums.OptionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Option list output dto.
 *
 * @author ryanwang
 * @date 2019-12-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OptionSimpleDTO extends OptionDTO {

    private Integer id;

    private OptionType type;

    private Date createTime;

    private Date updateTime;
}
