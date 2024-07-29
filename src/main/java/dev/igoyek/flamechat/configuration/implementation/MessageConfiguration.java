package dev.igoyek.flamechat.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;

public class MessageConfiguration extends OkaeriConfig {

    public String onlyForPlayers = "<red>This command is only for players!";
    public String chatStatusCannotBeTheSame = "<red>Chat status cannot be the same!";
    public String requiredPermission = "<red>You don't have permission to use this command! &4({PERMISSION})";
    public String invalidUsage = "<red>Usage: {USAGE}";
    public String argumentMustBeNumber = "<red>Argument must be a number!";
    public String configReloaded = "<red>Configuration has been reloaded!";

    public String chatStatusChanged = "Chat status has been changed to: {STATUS}";
    public String enabledStatus = "<green>Enabled";
    public String disabledStatus = "<red>Disabled";
    public String premiumStatus = "<gold>Premium";

    public String chatCleared = "Chat has been cleared!";
    public String chatSlowed = "Chat has been slowed down to: <gold>{DELAY} seconds<reset>!";

    public String chatIsDisabled = "<red>Chat is currently disabled!";
    public String chatIsPremium = "<gold>Chat is currently in premium mode!";
    public String chatIsSlowed = "Chat is currently slowed down to: <gold>{DELAY} seconds<reset>!";

    public String requiredToMineBlocks = "<red>You need to mine at least {BLOCKS} blocks to chat!";
}
