package com.bisaibang.monojwt.domain.vm;

/**
 * Created by Lynn on 2017/3/22.
 */
public class CityVM {

    private String type;

    private String remark;

    private String description;

    private String avatar;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "CityVM{" +
            "type='" + type + '\'' +
            ", remark='" + remark + '\'' +
            ", description='" + description + '\'' +
            ", avatar='" + avatar + '\'' +
            '}';
    }
}
