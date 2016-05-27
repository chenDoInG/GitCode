package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Org;
import com.chendoing.gitcode.data.api.model.Team;

public class TeamAddEvent extends Payloaded {
    private Team team;
    private Org organization;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Org getOrganization() {
        return organization;
    }

    public void setOrganization(Org organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "TeamAddPayload{" +
                "team=" + team +
                ", organization=" + organization +
                '}';
    }

    @Override
    public int getDrawable() {
        return R.drawable.mega_icon_team;
    }
}
