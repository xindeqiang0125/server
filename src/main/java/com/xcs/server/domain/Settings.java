package com.xcs.server.domain;

import javax.persistence.*;

@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer pushInterval;
    @Column(nullable = false)
    private Integer saveInterval;

    public Settings() {
    }

    public Settings(Integer pushInterval, Integer saveInterval) {
        this.pushInterval = pushInterval;
        this.saveInterval = saveInterval;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPushInterval() {
        return pushInterval;
    }

    public void setPushInterval(Integer pushInterval) {
        this.pushInterval = pushInterval;
    }

    public Integer getSaveInterval() {
        return saveInterval;
    }

    public void setSaveInterval(Integer saveInterval) {
        this.saveInterval = saveInterval;
    }
}
