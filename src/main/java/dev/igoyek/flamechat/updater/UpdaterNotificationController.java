package dev.igoyek.flamechat.updater;

import dev.igoyek.flamechat.notification.Notification;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.notification.implementation.ChatNotification;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdaterNotificationController implements Listener {

    private static final Notification NEW_VERSION_AVAILABLE_MESSAGE =
            new ChatNotification("<b><gradient:#00FFE0:#EB00FF>FlameChat</gradient></b> <color:#cfb1ff>New version of VoidKeeper is available, please update!");

    private final Logger logger = LoggerFactory.getLogger(UpdaterNotificationController.class);

    private final UpdaterService updaterService;
    private final NotificationService notificationSender;

    public UpdaterNotificationController(UpdaterService updaterService, NotificationService notificationSender) {
        this.updaterService = updaterService;
        this.notificationSender = notificationSender;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("flamechat.revieveupdates")) {
            return;
        }

        this.updaterService.isUpToDate().whenComplete((isUpToDate, throwable) -> {
            if (throwable != null) {
                this.logger.error("An error occurred while checking for updates", throwable);
                return;
            }

            if (!isUpToDate) {
                this.notificationSender.send(player, NEW_VERSION_AVAILABLE_MESSAGE);
            }
        });
    }

}
