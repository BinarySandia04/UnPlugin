package com.aranroig.syndriacore.syndriacore.interfaces;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface GameState {
    void Start();
    void Loop();

    void CleanUp();

    void Event(Event e);

    // default void EntityShootArrow(EntityShootBowEvent event) {}
    // default void ProjectileHit(ProjectileHitEvent event) {}
    // default void OnPlayerDamage(EntityDamageEvent p) {}
}
