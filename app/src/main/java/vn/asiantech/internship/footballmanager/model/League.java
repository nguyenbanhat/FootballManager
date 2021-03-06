package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  Created by sunday on 21/10/2015.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "league")
public class League extends SugarRecord {
    @Column(name = "name")
    String name;
    @Column(name = "information")
    String information;
    @Column(name = "logo")
    String logo;
    @Ignore
    private boolean isChecked;
    public League(){}

    public static void updateLeague(League league){
        if(league != null){
            league.save();
        }
    }

    public static List<League> getLeagueByName(String name){
        return League.find(League.class, "name = ?", name);
    }

    public static League getLeagueById(long id){
        return League.findById(League.class, id);
    }

    public static void deleteLeague(long id){
        League league = League.findById(League.class, id);
        if(league != null){
            league.delete();
        }
        Team.deleteTeamByLeagueId(id);
    }
}
