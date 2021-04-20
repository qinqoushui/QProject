/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 项目二维码用户码参数Entity
 *
 * @author 张文相
 * @version 2021-04-19
 */
@Table(name = "pm_proj_qr_user", alias = "a", label = "项目二维码用户码参数信息", columns = {
        @Column(name = "id", attrName = "id", label = "编号", isPK = true),
        @Column(name = "proj_id", attrName = "projId", label = "项目编号"),
        @Column(name = "qr_type", attrName = "qrType", label = "二维码厂商"),
        @Column(name = "password", attrName = "password", label = "密码"),
        @Column(name = "api_key", attrName = "apiKey", label = "API密钥"),
        @Column(name = "is_card_hex", attrName = "isCardHex", label = "十六进制卡号", isQuery = false),
        @Column(name = "is_cardno_big", attrName = "isCardnoBig", label = "卡号大端序", isQuery = false),
        @Column(name = "is_qr_big", attrName = "isQrBig", label = "二维码卡号大端序", isQuery = false),
        @Column(includeEntity = DataEntity.class),
},
        joinTable = {
                @JoinTable(type = JoinTable.Type.JOIN, entity = PmProjBase.class, alias = "p",
                        on = "p.id = a.proj_id", attrName = "project",
                        columns = {
                                @Column(includeEntity = PmProjBase.class),
                        }),
        }, orderBy = "a.update_date DESC"
)
public class PmProjQrUser extends DataEntity<PmProjQrUser> {

    private static final long serialVersionUID = 1L;
    private String projId;        // 项目编号
    private String qrType;        // 二维码厂商
    private String password;        // 密码
    private String apiKey;        // API密钥
    private Byte isCardHex;        // 十六进制卡号
    private Byte isCardnoBig;        // 卡号大端序
    private Byte isQrBig;        // 二维码卡号大端序
    private PmProjBase project;
    //外键
    public PmProjQrUser() {
        this(null);
    }

    public PmProjQrUser(String id) {
        super(id);
    }

    @NotBlank(message = "项目编号不能为空")
    @Length(min = 0, max = 20, message = "项目编号长度不能超过 20 个字符")
    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    @Length(min = 0, max = 20, message = "二维码厂商长度不能超过 20 个字符")
    public String getQrType() {
        return qrType;
    }

    public void setQrType(String qrType) {
        this.qrType = qrType;
    }

    @NotBlank(message = "密码不能为空")
    @Length(min = 0, max = 20, message = "密码长度不能超过 20 个字符")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 0, max = 32, message = "API密钥长度不能超过 32 个字符")
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Byte getIsCardHex() {
        return isCardHex;
    }

    public void setIsCardHex(Byte isCardHex) {
        this.isCardHex = isCardHex;
    }

    public Byte getIsCardnoBig() {
        return isCardnoBig;
    }

    public void setIsCardnoBig(Byte isCardnoBig) {
        this.isCardnoBig = isCardnoBig;
    }

    public Byte getIsQrBig() {
        return isQrBig;
    }

    public void setIsQrBig(Byte isQrBig) {
        this.isQrBig = isQrBig;
    }


    public PmProjBase getProject() {
        return project;
    }

    public void setProject(PmProjBase project) {
        this.project = project;
    }
}