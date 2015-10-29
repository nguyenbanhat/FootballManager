package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  Created by sunday on 21/10/2015.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "team")
public class Team extends SugarRecord{
    @Column(name = "name")
    String name;
    @Column(name = "information")
    String information;
    @Column(name = "logo")
    int logo;
    @Column(name = "league_id", unique = false, notNull = true)
    long league_id;
    public Team(){}

    public static void updateTeam(Team team){
        if(team != null){
         team.save();
     }
    }

    public static List<Team> getTeamByName(String name){
      return Team.find(Team.class, "name = ?" , name);
    }

    public static Team getTeamById(long id){
        return Team.findById(Team.class, id);
    }

    public static void deleteTeam(long id){
        //Player in here
        Player.deletePlayerByTeamId(id);
        Team team  = Team.findById(Team.class, id);
        if(team != null){
            team.delete();
        }
    }
    public static void deleteTeamByLeagueId(long id ){
        List<Team> teams = Team.getTeamByLeagueId(id);
       for(Team team : teams){
                team.delete();
       }
    }
    public static List<Team> getTeamByLeagueId(long id){
        return Team.find(Team.class, "league_id = ?", String.valueOf(id));
    }
}
