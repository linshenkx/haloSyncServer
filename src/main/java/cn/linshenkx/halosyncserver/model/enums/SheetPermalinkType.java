package cn.linshenkx.halosyncserver.model.enums;

/**
 * Sheet Permalink type enum.
 *
 * @author ryanwang
 * @date 2020-12-01
 */
public enum SheetPermalinkType implements ValueEnum<Integer> {

    /**
     * /{@link cn.linshenkx.halosyncserver.model.properties.PermalinkProperties#SHEET_PREFIX}/${slug}
     */
    SECONDARY(0),

    /**
     * /${slug}
     */
    ROOT(1);

    private final Integer value;

    SheetPermalinkType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
