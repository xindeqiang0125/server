package com.xcs.server.domain.opc;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class XGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 32,nullable = false)
    private String groupName;
    @Column(nullable = false)
    private Boolean active;
    @Column(nullable = false)
    private Integer updateRate;
    @Column(nullable = false)
    private Float percentDeadBand;
    @ManyToOne()
    @JoinColumn(nullable = false)
    private XOpc opc;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "group")
    @JsonIgnore
    private Set<XItem> items=new HashSet<XItem>();

    public XGroup() {

    }

    public XGroup(String groupName, Boolean active, Integer updateRate, Float percentDeadBand) {
        this.groupName = groupName;
        this.active = active;
        this.updateRate = updateRate;
        this.percentDeadBand = percentDeadBand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getUpdateRate() {
        return updateRate;
    }

    public void setUpdateRate(Integer updateRate) {
        this.updateRate = updateRate;
    }

    public Float getPercentDeadBand() {
        return percentDeadBand;
    }

    public void setPercentDeadBand(Float percentDeadBand) {
        this.percentDeadBand = percentDeadBand;
    }

    public XOpc getOpc() {
        return opc;
    }

    public void setOpc(XOpc opc) {
        this.opc = opc;
    }

    public Set<XItem> getItems() {
        return items;
    }

    public void setItems(Set<XItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XGroup)
            return this.getId().equals(((XGroup)obj).getId());
        return false;
    }
}
