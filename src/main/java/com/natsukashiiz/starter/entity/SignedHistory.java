package com.natsukashiiz.starter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "tb_signed_history")
@Data
public class SignedHistory extends BaseEntity {
    @JsonIgnore
    @Column(nullable = false)
    private Long uid;
    @Column(nullable = false, length = 32)
    private String ipv4;
    @JsonIgnore
    @Column(nullable = false)
    private String userAgent;
    @Column(nullable = false, length = 7)
    private String device;
}
