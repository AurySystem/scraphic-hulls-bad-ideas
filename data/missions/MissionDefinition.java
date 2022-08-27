package data.missions.scraphiccursedmission;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;

public class MissionDefinition implements MissionDefinitionPlugin {
    @Override
    public void defineMission(MissionDefinitionAPI api) {
        api.initFleet(FleetSide.PLAYER, "SLVG", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "JNK", FleetGoal.ATTACK, true);
        api.setFleetTagline(FleetSide.PLAYER, "Scraphic Shipyards Fielding Fleet");
        api.setFleetTagline(FleetSide.ENEMY, "Baba Is Fleet");

        api.addBriefingItem("Defeat all enemy forces");
        api.addBriefingItem("Or don't we're not a cop");
        api.addBriefingItem("Enjoy the wacky ships");

        api.addToFleet(FleetSide.PLAYER, "scraphichulls_gunnedboat_defualt", FleetMemberType.SHIP, true);
        api.addToFleet(FleetSide.PLAYER, "scraphichulls_tanker_para_standard", FleetMemberType.SHIP, false);
        api.addToFleet(FleetSide.ENEMY, "heron_Strike", FleetMemberType.SHIP, true);

        float width = 24000f;
        float height = 18000f;
        api.initMap(-width/2f, width/2f, -height/2f, height/2f);

        float minX = -width/2;
        float minY = -height/2;

        api.addAsteroidField(-(minY + height), minY + height, -45, 2000f,
                20f, 70f, 100);

        api.addNebula(50, 300, 1430);

        api.addPlanet(0, 0, 200f, "barren", 100f, true);
        api.addRingAsteroids(0,0, 30, 32, 20, 48, 200);
    }
}
