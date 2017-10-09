package com.bisaibang.monojwt.domain.vm;

/**
 * Created by xiazhen on 2016/10/31.
 */
public class PlayerStateChangeVM {

    private Long teamid;

    private Long userid;

    private String state;

    public Long getTeamid() {
        return teamid;
    }

    public void setTeamid(Long teamid) {
        this.teamid = teamid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static PlayerStateChangeVM create(Long teamid, Long userid, String state) {
        PlayerStateChangeVM pSCVM = new PlayerStateChangeVM();
        pSCVM.teamid = teamid;
        pSCVM.userid = userid;
        pSCVM.state = state;
        return pSCVM;
    }

    @Override
    public String toString() {
        return "PlayerStateChangeVM{" +
            "teamid=" + teamid +
            ", userid=" + userid +
            ", state='" + state + '\'' +
            '}';
    }
}
