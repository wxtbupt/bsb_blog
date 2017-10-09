package com.bisaibang.monojwt.domain.vm;

import com.bisaibang.monojwt.domain.game.Registration;
import com.bisaibang.monojwt.domain.team.TeamPlayer;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * Created by Lynn on 2017/3/24.
 */
public class RegistrationVM {

    private Registration registration;

    private List<TeamPlayer> teamPlayers;


    @Override
    public String toString() {
        return "RegistrationVM{" +
            "registration=" + registration +
            ", teamPlayers=" + teamPlayers +
            '}';
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public List<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public static RegistrationVM create(Registration registration, List<TeamPlayer> teamPlayers){
        RegistrationVM registrationVM = new RegistrationVM();
        registrationVM.setRegistration(registration);
        registrationVM.setTeamPlayers(teamPlayers);
        return registrationVM;
    }

}
