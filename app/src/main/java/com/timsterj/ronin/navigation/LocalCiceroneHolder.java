package com.timsterj.ronin.navigation;

import java.util.HashMap;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

public class LocalCiceroneHolder {

    private HashMap<String, Cicerone<Router>> containers = new HashMap<>();

    public LocalCiceroneHolder() {
    }

    public Cicerone<Router> getCicerone(String containerTag) {
        if (!containers.containsKey(containerTag)) {
            containers.put(containerTag, Cicerone.create());
        }
        return containers.get(containerTag);
    }
}
