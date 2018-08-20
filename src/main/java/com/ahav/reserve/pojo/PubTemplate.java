package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "PubTemplate")
public class PubTemplate {
    @ApiModelProperty(value = "模板内容")
    private String content;
    @ApiModelProperty(value = "布局id")
    private Integer layoutId;
    @ApiModelProperty(value = "thumb")
    private String thumb;
    @ApiModelProperty(value = "模板id")
    private Integer id;
    @ApiModelProperty(value = "模板名称")
    private String name;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "pubTemplate{" +
                "content='" + content + '\'' +
                ", layoutId=" + layoutId +
                ", thumb='" + thumb + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
