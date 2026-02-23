package ghostybank.gui;

import ghostybank.GhostyBank;
import ghostybank.data.BankData;
import ghostybank.data.BankLevel;
import ghostybank.manager.BankManager;
import ghostybank.manager.LangManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public class AdminGUI {

    private final BankManager bankManager;
    private final LangManager lang;

    public AdminGUI(GhostyBank plugin) {
        this.bankManager = plugin.getBankManager();
        this.lang = plugin.getLangManager();
    }

    public void open(Player admin, BankData target) {
        BankLevel level = bankManager.getLevel(target.getLevel());
        int maxLevel = bankManager.getMaxLevel();

        GuiHolder holder = new GuiHolder(GuiHolder.GuiType.ADMIN, target.getUuid());
        Inventory inv = Bukkit.createInventory(holder, 36,
                lang.get("admin-gui.title", Map.of("%player%", target.getPlayerName())));
        holder.setInventory(inv);

        // Filler
        String fillerMat = lang.getString("admin-gui.filler-material");
        if (!fillerMat.isBlank()) {
            org.bukkit.inventory.ItemStack filler = GuiUtil.createItem(fillerMat,
                    lang.getString("admin-gui.filler-name"), null);
            for (int i = 0; i < 36; i++) inv.setItem(i, filler);
        }

        // Player skull with info (center top)
        inv.setItem(lang.getInt("admin-gui.player-head-slot", 4), GuiUtil.createSkull(
                target.getPlayerName(),
                lang.get("admin-gui.player-head-name", Map.of("%player%", target.getPlayerName())),
                lang.getList("admin-gui.player-head-lore", Map.of(
                        "%player%", target.getPlayerName(),
                        "%balance%", bankManager.formatCurrency(target.getBalance()),
                        "%level%", String.valueOf(target.getLevel()),
                        "%level_name%", level.getDisplayName(),
                        "%total_interest%", bankManager.formatCurrency(target.getTotalInterestEarned()),
                        "%max_deposit%", bankManager.formatMaxDeposit(level)
                ))
        ));

        // Add money
        inv.setItem(lang.getInt("admin-gui.add-money-slot", 11), GuiUtil.createItem(
                lang.getString("admin-gui.add-money-material"),
                lang.get("admin-gui.add-money-name"),
                lang.getList("admin-gui.add-money-lore")
        ));

        // Set money
        inv.setItem(lang.getInt("admin-gui.set-money-slot", 13), GuiUtil.createItem(
                lang.getString("admin-gui.set-money-material"),
                lang.get("admin-gui.set-money-name"),
                lang.getList("admin-gui.set-money-lore")
        ));

        // Remove money
        inv.setItem(lang.getInt("admin-gui.remove-money-slot", 15), GuiUtil.createItem(
                lang.getString("admin-gui.remove-money-material"),
                lang.get("admin-gui.remove-money-name"),
                lang.getList("admin-gui.remove-money-lore")
        ));

        // Set level
        inv.setItem(lang.getInt("admin-gui.set-level-slot", 22), GuiUtil.createItem(
                lang.getString("admin-gui.set-level-material"),
                lang.get("admin-gui.set-level-name"),
                lang.getList("admin-gui.set-level-lore", Map.of(
                        "%max_level%", String.valueOf(maxLevel),
                        "%level%", String.valueOf(target.getLevel()),
                        "%level_name%", level.getDisplayName()
                ))
        ));

        // Close
        inv.setItem(lang.getInt("admin-gui.close-slot", 31), GuiUtil.createItem(
                lang.getString("admin-gui.close-material"),
                lang.get("admin-gui.close-name"),
                lang.getList("admin-gui.close-lore")
        ));

        admin.openInventory(inv);
    }
}
















