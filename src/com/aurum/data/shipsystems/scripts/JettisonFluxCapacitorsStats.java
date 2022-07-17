package com.aurum.data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.loading.WeaponSlotAPI;

import java.awt.*;
import java.util.EnumSet;

public class JettisonFluxCapacitorsStats extends BaseShipSystemScript {

    public static final Object KEY_SHIP = new Object();


    public void apply(MutableShipStatsAPI stats, final String id, State state, float effectLevel) {
        ShipAPI ship;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
        } else {
            return;
        }

        ship.fadeToColor(KEY_SHIP, new Color(75,75,75,255), 0.1f, 0.1f, effectLevel);
        //ship.fadeToColor(KEY_SHIP, new Color(100,100,100,255), 0.1f, 0.1f, effectLevel);
        ship.setWeaponGlow(effectLevel, new Color(100,165,255,255), EnumSet.of(WeaponAPI.WeaponType.BALLISTIC, WeaponAPI.WeaponType.ENERGY, WeaponAPI.WeaponType.MISSILE));

        //instant vent
        ship.getFluxTracker().setCurrFlux(0f);
        //break capacity cuz, uhh you just fired off your capacitors
        stats.getFluxCapacity().modifyMult(id, 0.5f);

        for(WeaponSlotAPI slot : ship.getHullSpec().getAllWeaponSlotsCopy()){
            if (slot.isSystemSlot()) {
                slot.computeMidArcAngle(ship);
                Global.getCombatEngine().spawnProjectile(ship,null,"scraphichulls_jetison_capacitor_weap",slot.computePosition(ship),slot.computeMidArcAngle(ship),ship.getVelocity());
            }
        }

        stats.getFluxDissipation().modifyMult(id, 2f);

    }


    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getFluxCapacity().unmodify(id);
        stats.getFluxDissipation().unmodify(id);

    }

    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            return new StatusData("50% Max Flux Capacity", true);
        }else if (index == 1) {
            return new StatusData("200% Flux Dissipation", false);
        }
        return null;
    }
}
