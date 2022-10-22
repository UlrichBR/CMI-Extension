package me.ext.cmi.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;

import me.ulrich.koth.Koth;
import me.ulrich.koth.data.OptimalHologram;
import me.ulrich.koth.interfaces.Hologram;
import me.ulrich.koth.interfaces.HologramAPI;
import me.ulrich.koth.interfaces.ItemLine;
import me.ulrich.koth.interfaces.Line;
import me.ulrich.koth.interfaces.TextLine;
import me.ulrich.koth.interfaces.VisibilityManager;
import net.Zrips.CMILib.Container.CMILocation;

import java.util.Optional;

public final class Implementation implements HologramAPI {

    @Override
    public Hologram createHologram(final Koth plugin, final Location location, String id, Object extra) {
        return new OptimalHologram(
                this,
                new CMIHologram(id, new CMILocation(location)),
                location, id, extra
        );
    }

    @Override
    public void setLine(final Hologram hologram,
                        final int lineIndex,
                        final Line line) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(hologram);

        if(!hologramOptional.isPresent()) {
            return;
        }

        final CMIHologram cmiHologram = hologramOptional.get();

        if (line instanceof ItemLine) {
            return;
        }

        if (line instanceof TextLine) {
            cmiHologram.setLine(lineIndex, ((TextLine) line).getText());
            cmiHologram.update();
        }
    }

    @Override
    public void updateLine(final Hologram hologram,
                           final int lineIndex,
                           final Line line) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(hologram);

        if(!hologramOptional.isPresent()) {

            return;
        }

        final CMIHologram cmiHologram = hologramOptional.get();

        if (line instanceof ItemLine) {
            return;
        }

        if (line instanceof TextLine) {
            final String text = ((TextLine) line).getText();
            cmiHologram.setLine(lineIndex, text);
            CMI.getInstance().getHologramManager().addHologram(cmiHologram);
            cmiHologram.update();
        }
    }

    @Override
    public void appendLine(final Hologram hologram, final Line line) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(hologram);

        if(!hologramOptional.isPresent()) {
            return;
        }

        final CMIHologram cmiHologram = hologramOptional.get();

        if (line instanceof ItemLine) {
            return;
        }

        if (line instanceof TextLine) {
            final String text = ((TextLine) line).getText();
            cmiHologram.addLine(text);
           
            CMI.getInstance().getHologramManager().addHologram(cmiHologram);
            cmiHologram.update();
        }
    }

    @Override
    public void teleport(final Hologram hologram, final Location location) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(hologram);

        if(!hologramOptional.isPresent()) {
            return;
        }

        final CMIHologram cmiHologram = hologramOptional.get();
        cmiHologram.setLoc(location);
        CMI.getInstance().getHologramManager().addHologram(cmiHologram);
        cmiHologram.update();
    }

    @Override
    public void delete(final Hologram hologram) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(hologram);
        hologramOptional.ifPresent(CMIHologram::remove);
    }

    @Override
    public void showTo(final VisibilityManager visibilityManager, final Player player) {
        throw new UnsupportedOperationException("CMI does not support per-player holograms!");
    }

    @Override
    public void hideTo(final VisibilityManager visibilityManager, final Player player) {
        final Optional<CMIHologram> hologramOptional = this.getHologram(visibilityManager.getHologram());

        if(!hologramOptional.isPresent()) {
            return;
        }

        final CMIHologram cmiHologram = hologramOptional.get();
        cmiHologram.hide(player.getUniqueId());
    }

    private Optional<CMIHologram> getHologram(final Hologram hologram) {
        final Object hologramObject = hologram.getHologramAsObject();

        if(hologramObject instanceof CMIHologram) {
            return Optional.of((CMIHologram) hologramObject);
        }

        return Optional.empty();
    }
}