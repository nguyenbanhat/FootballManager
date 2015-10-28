package vn.asiantech.internship.footballmanager.model;

import android.util.Log;

import com.orm.SugarApp;

import java.util.List;
import java.util.Random;

import vn.asiantech.internship.footballmanager.R;

/**
 *  Created by sunday on 20/10/2015.
 */
public class BaseSugarApp extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        int a = randomNumber(1,99);
        Log.e("NUM " , a +"");
        int b = randomNumber(0, 45) + 155;
        Log.e("HEIGHT" , b +"");
        int c = randomNumber(0, 150) + 60;
        Log.e("WEIGHT", c + "");
        String d = randomPlayerName();
        Log.e("NAME", d + "");

        Player.EnumPosition[] mang = Player.getAll();
        Log.e("ARRAY", mang[0] + "");
        Log.e("ARRAY", mang.length + "");

        String g = randomPosition();
        Log.e("POSITION", g + "");
        String h = randomCountry();
        Log.e("COUNTRY", h + "");
        List<League> leagues = League.listAll(League.class);
        if(leagues == null || leagues.size() == 0){
            createLeagueData();
        }
    }

    private void createLeagueData(){
        for(int i = 1 ; i <= 10; i++){
            League league = new League();
            league.setName("League " + i);
            league.setInformation("Information League " + i);
            league.setLogo("/storage/emulated/0/Download/logo_cup.jpg");
            league.setChecked(false);
            league.save();
            createTeamData(league.getId());
        }
    }

    private void createTeamData(long id){
        for(int i = 1 ; i <= 16; i++){
            Team team = new Team();
            team.setName("Team " + i);
            team.setInformation("Information Team " + i);
            team.setLeague_id(id);
            team.setLogo(R.drawable.logo_barca);
            team.save();
            createPlayerData(team.getId());
        }
    }

    private void createPlayerData(long id){
        for(int i = 1; i <= 20; i++){
            Player player = new Player();
            int number = randomNumber(1,99);
            int height = randomNumber(0, 45) + 155;
            int weight = randomNumber(0, 150) + 60;
            int year = randomNumber(0,20) + 1975;
            int month = randomNumber(0, 11) + 1;
            int day = randomNumber(0, 28) + 1;
            String name = randomPlayerName();
            String birth = year + "/" + month + "/" + day;
            String position = randomPosition();
            player.setName(name);
            player.setBirth(birth);
            player.setCountry(randomCountry());
            player.setNumber(number);
            player.setPosition(position);
            player.setHeight(height);
            player.setWeight(weight);
            player.setAvatar(R.drawable.ic_avatar_garen);
            player.setTeam_id(id);
            player.save();
        }
    }
    private int randomNumber(int min, int max){
        try {
            Random rn = new Random();
            int range = max - min + 1;
            //int rdNum = min + rn.nextInt(range);
            return (min + rn.nextInt(range));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    private String randomPosition(){
        Player.EnumPosition[] arrPosition = Player.getAll();
        Random r = new Random();
        int i = r.nextInt(arrPosition.length);
        //String position = String.valueOf(arrPosition[i]);
        return (arrPosition[i].toString());
    }

    private String randomPlayerName() {
        String[] arrName = {"Darius", "Veigar", "Garen", "Jax", "Trym", "Lee", "Kennen", "Shen", "Talon", "Yasuo"};
        Random r = new Random();
        int i = r.nextInt(arrName.length);
        String name = arrName[i];
        i = r.nextInt(arrName.length);
        name += " " + arrName[i];
        return name;
    }

    private String randomCountry() {
        String[] arrName = {"England", "Italia", "Franch", "Spanish", "German", "Brazil", "Argentina", "Uruguay", "Chile", "Netherland"};
        Random r = new Random();
        int i = r.nextInt(arrName.length);
        return arrName[i];
    }
}
