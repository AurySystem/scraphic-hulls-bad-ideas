package com.aurum.data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.thoughtworks.xstream.XStream;

public class ScraphicHullsModPlug extends BaseModPlugin {

    @Override
    public void onGameLoad(boolean newGame) {
        super.onGameLoad(newGame);

    }

    /**
     * Tell the XML serializer to use custom naming, so that moving or renaming classes doesn't break saves.
     */
    @Override
    public void configureXStream(XStream x) {
        super.configureXStream(x);
        // This will make it so that whenever "ExampleEveryFrameScript" is put into the save game xml file,
        // it will have an xml node called "ExampleEveryFrameScript" (even if you rename the class!).
        // This is a way to prevent refactoring from breaking saves, but is not required to do.

        // x.alias("ExampleEveryFrameScript", ExampleEveryFrameScript.class);
    }
}