package shateq.tnttime.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {
    public static final String id = "tnttime";
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static boolean colored;

    @Override
    public void onInitializeClient() {
        colored = false;

//        FabricLoader.getInstance().getModContainer(id).ifPresent(modContainer -> {
//            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(id+":nicelogo"),  modContainer, ResourcePackActivationType.NORMAL);
//        });
    }

    public static Text getTime(double ticks) {
        Formatting format = Formatting.WHITE;
        colored = areChatColors();
        double timing = ticks / 20;

        if (!colored) {
            return Text.of(decimalFormat.format(timing)).copy().formatted(Formatting.WHITE);
        } else {
            if (timing > 7d) {
                format = Formatting.DARK_AQUA;
            } else if (timing > 6d) {
                format = Formatting.AQUA;
            } else if (timing > 4d) {
                format = Formatting.DARK_GREEN;
            } else if (timing > 3d) {
                format = Formatting.GREEN;
            } else if (timing > 2d) {
                format = Formatting.GOLD;
            } else if (timing > 1d) {
                format = Formatting.RED;
            } else if (timing > 0d) {
                format = Formatting.DARK_RED;
            }
            return Text.of(decimalFormat.format(timing)).copy().formatted(format);
        }
    }

    private static boolean areChatColors() {
        return MinecraftClient.getInstance().options.chatColors;
    }
}
