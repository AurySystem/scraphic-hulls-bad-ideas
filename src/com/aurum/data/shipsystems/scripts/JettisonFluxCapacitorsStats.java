package com.aurum.data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.loading.WeaponSlotAPI;

import java.awt.*;
import java.util.EnumSet;

public class JettisonFluxCapacitorsStats extends BaseShipSystemScript {

    protected WeaponAPI weapon;

    public void apply(MutableShipStatsAPI stats, final String id, State state, float effectLevel) {
        ShipAPI ship;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
        } else {
            return;
        }

        ship.setWeaponGlow(effectLevel, new Color(108, 36, 20,255), EnumSet.of(WeaponAPI.WeaponType.BALLISTIC, WeaponAPI.WeaponType.ENERGY, WeaponAPI.WeaponType.MISSILE));
        //instant vent and break capacity cuz, uhh you just fired off your capacitors
        if(state.equals(State.IN)){
            ship.getFluxTracker().setCurrFlux(0f);
            if(weapon == null)
                weapon = Global.getCombatEngine().createFakeWeapon(ship, "scraphichulls_jetison_capacitor_weap");

            for(WeaponSlotAPI slot : ship.getHullSpec().getAllWeaponSlotsCopy()){
                if (slot.isSystemSlot()) {
                    Global.getCombatEngine().spawnProjectile(ship, weapon,
                            "scraphichulls_jetison_capacitor_weap", slot.computePosition(ship),
                            slot.computeMidArcAngle(ship)-2+(2*(float)Math.random()), ship.getVelocity());
                }
            }
        }
        stats.getFluxCapacity().modifyMult(id, 0.5f);
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
