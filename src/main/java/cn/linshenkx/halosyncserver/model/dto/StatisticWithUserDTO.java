package cn.linshenkx.halosyncserver.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Statistic with user info DTO.
 *
 * @author ryanwang
 * @date 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StatisticWithUserDTO extends StatisticDTO {

    private UserDTO user;
}
