package cn.linshenkx.halosyncserver.model.dto;

import cn.linshenkx.halosyncserver.model.enums.MFAType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * User output dto.
 *
 * @author johnniang
 * @date 3/16/19
 */
@Data
@ToString
@EqualsAndHashCode
public class UserDTO {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String description;

    private MFAType mfaType;

    private Date createTime;

    private Date updateTime;
}
