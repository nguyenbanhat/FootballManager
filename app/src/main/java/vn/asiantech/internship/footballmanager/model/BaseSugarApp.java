package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarApp;

import java.util.List;

import vn.asiantech.internship.footballmanager.R;

/**
 *  Created by sunday on 20/10/2015.
 */
public class BaseSugarApp extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        List<League> leagues = League.listAll(League.class);
        if(leagues == null || leagues.size() == 0){
            createLeagueData();
        }
    }

    private void createLeagueData(){
        for(int i = 1 ; i < 10; i++){
            League league = new League();
            league.setName("League " + i);
            league.setInformation("Information League " + i);
            league.setLogo(R.drawable.logo_cup);
            league.setChecked(false);
            league.save();
            createTeamData(league.getId());
        }
    }

    private void createTeamData(long id){
        for(int i = 1 ; i < 16; i++){
            Team team = new Team();
            team.setName("Team " + i);
            team.setInformation("Information Team " + i);
            team.setLogo(R.drawable.logo_barca);
            team.setLeague_id(id);
            team.save();
        }
    }
}
