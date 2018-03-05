package com.xcs.server.domain.opc;

import javax.persistence.*;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class XItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 32,nullable = false)
    private String itemName;
    @Column(nullable = false)
    private Boolean active;
    @Column(length = 16,nullable = false)
    private String accessPath;
    @Column(length = 128)
    private String notes;
    @Column(length = 8,nullable = false)
    private String type;
    private Double min;
    private Double max;
    private Double normal;
    @Column(length = 16)
    private String unit;
    @ManyToOne()
    @JoinColumn(nullable = false)
    private XGroup group;

    public XItem() {
    }

    public XItem(String itemName, Boolean active, String accessPath, String type) {
        this.itemName = itemName;
        this.active = active;
        this.accessPath = accessPath;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getNormal() {
        return normal;
    }

    public void setNormal(Double normal) {
        this.normal = normal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public XGroup getGroup() {
        return group;
    }

    public void setGroup(XGroup group) {
        this.group = group;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XItem)
            return this.getId().equals(((XItem)obj).getId());
        return false;
    }
}
