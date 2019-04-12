package com.xcs.server.history.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xcs.server.web.domain.opc.XItem;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(name = "xItem_time",columnList = "x_item_id,time")})
public class IntegerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer value;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;
    @ManyToOne()
    @JoinColumn(nullable = false)
    @JsonIgnore
    private XItem xItem;

    public IntegerHistory() {
    }

    public IntegerHistory(Integer value, LocalDateTime time, XItem xItem) {
        this.value = value;
        this.time = time;
        this.xItem = xItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public XItem getxItem() {
        return xItem;
    }

    public void setxItem(XItem xItem) {
        this.xItem = xItem;
    }
}
