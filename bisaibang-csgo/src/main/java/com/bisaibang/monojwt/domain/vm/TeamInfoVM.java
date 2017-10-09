package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.team.Team;
import com.bisaibang.monojwt.domain.team.TeamPlayer;

import java.sql.Time;
import java.util.List;

/**
 * Created by liujinduo on 2016/10/26.
 */
public class TeamInfoVM {

    private Team team;

    private List<TeamPlayer> teamPlayer;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<TeamPlayer> getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(List<TeamPlayer> teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    @Override
    public String toString() {
        return "TeamInfoVM{" +
            "team=" + team +
            ", teamPlayer=" + teamPlayer +
            '}';
    }
}
