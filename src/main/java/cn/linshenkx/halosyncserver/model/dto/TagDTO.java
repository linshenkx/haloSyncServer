package cn.linshenkx.halosyncserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * Tag output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-19
 */
@Data
public class TagDTO {

    private Integer id;

    private String name;

    private String slug;

    private String thumbnail;

    private Date createTime;

    private String fullPath;
}
