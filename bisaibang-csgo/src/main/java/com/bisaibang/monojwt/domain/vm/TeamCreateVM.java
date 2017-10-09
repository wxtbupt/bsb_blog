package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.team.TeamPlayer;

import java.util.List;

/**
 * Created by liujinduo on 2016/10/26.
 */
public class TeamCreateVM {

    private String name;

    private String description;

    private String teamAvatar;

    private String remark;

    private List<TeamPlayer> teamPlayer;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamAvatar() {
        return teamAvatar;
    }

    public void setTeamAvatar(String teamAvatar) {
        this.teamAvatar = teamAvatar;
    }

    public List<TeamPlayer> getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(List<TeamPlayer> teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    @Override
    public String toString() {
        return "TeamCreateVM{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teamAvatar='" + teamAvatar + '\'' +
                ", remark='" + remark + '\'' +
                ", teamPlayer=" + teamPlayer +
                '}';
    }
}
