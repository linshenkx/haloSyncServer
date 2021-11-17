package cn.linshenkx.halosyncserver.model.dto;

import lombok.Data;

/**
 * Link output dto.
 *
 * @author ryanwang
 * @date 2019/3/21
 */
@Data
public class LinkDTO {

    private Integer id;

    private String name;

    private String url;

    private String logo;

    private String description;

    private String team;

    private Integer priority;
}
