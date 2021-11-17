package cn.linshenkx.halosyncserver.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cn.linshenkx.halosyncserver.model.dto.MenuDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ryanwang
 * @date 2019-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuVO extends MenuDTO {

    private List<MenuVO> children = new LinkedList<>();
}
