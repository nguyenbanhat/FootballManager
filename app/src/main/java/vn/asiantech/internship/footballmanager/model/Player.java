package vn.asiantech.internship.footballmanager.model;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  Created by sunday on 22/10/2015.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Table(name = "player")
public class Player extends SugarRecord {
    @Column(name = "name")
    String name;
    @Column(name = "country")
    String country;
    @Column(name = "birth")
    String birth;
    @Column(name = "position")
    String position;
    @Column(name = "number")
    int number;
    @Column(name = "weight")
    int weight;
    @Column(name = "height")
    int height;
    @Column(name = "avatar")
    int avatar;
    @Column(name = "team_id", unique = false, notNull = true)
    long team_id;

    public Player() {
    }

    public static void updatePlayer(Player player) {
        if (player != null) {
            player.save();
        }
    }

    public static List<Player> getPlayerByName(String name) {
        return Player.find(Player.class, "name = ?", name);
    }

    public static List<Player> getPlayerByNameAndNumber(String name, int number) {
        return Player.find(Player.class, "name = ? and number = ?", name, String.valueOf(number));
    }

    public static Player getPlayerById(long id) {
        return Player.findById(Player.class, id);
    }

    public static void deletePlayer(long id) {
        Player player = Player.findById(Player.class, id);
        if (player != null) {
            player.delete();
        }
    }

    public static void deletePlayerByTeamId(long id) {
        List<Player> players = Player.getPlayerByTeamId(id);
        for (Player player : players) {
            player.delete();
        }
    }

    public static List<Player> getPlayerByTeamId(long id) {
        return Player.find(Player.class, "team_id = ?", String.valueOf(id));
    }

    public enum EnumPosition {
        GK,
        SW,
        LB, CB, RB,
        LWB, RWB,
        DM,
        LM, CM, RM,
        AM,
        LW, SS, RW,
        CF
    }

    public static EnumPosition getEnumPosition(String position) {
        switch (position) {
            case "GK":
                return EnumPosition.GK;
            case "SW":
                return EnumPosition.SW;
            case "LB":
                return EnumPosition.LB;
            case "CB":
                return EnumPosition.CB;
            case "RB":
                return EnumPosition.RB;
            case "LWB":
                return EnumPosition.LWB;
            case "RWB":
                return EnumPosition.RWB;
            case "DM":
                return EnumPosition.DM;
            case "LM":
                return EnumPosition.LM;
            case "CM":
                return EnumPosition.CM;
            case "RM":
                return EnumPosition.RM;
            case "AM":
                return EnumPosition.AM;
            case "LW":
                return EnumPosition.LW;
            case "SS":
                return EnumPosition.SS;
            case "RW":
                return EnumPosition.RW;
            case "CF":
                return EnumPosition.CF;
            default:
                return null;
        }
    }

    public static EnumPosition[] getAll() {
        return EnumPosition.values();
    }

    public static int getAge(long id){
        Player player = Player.findById(Player.class, id);
        String birth = player.getBirth() + "";
        String[] s = birth.split("[/]");
        String year =  s[0];
        //int age = (2015 - Integer.parseInt(year));
        return (2015 - Integer.parseInt(year));
    }
}
