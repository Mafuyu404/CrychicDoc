package dev.ftb.mods.ftbquests.quest.theme;

import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;
import dev.ftb.mods.ftbquests.quest.QuestShape;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import dev.ftb.mods.ftbquests.quest.theme.selector.AllSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.AndSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.IDSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.NotSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.TagSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.ThemeSelector;
import dev.ftb.mods.ftbquests.quest.theme.selector.TypeSelector;
import dev.ftb.mods.ftbquests.util.FileUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.Nullable;

public class ThemeLoader implements ResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        loadTheme(resourceManager);
    }

    public static void loadTheme(ResourceManager resourceManager) {
        Map<ThemeSelector, SelectorProperties> map = new HashMap();
        try {
            for (Resource resource : resourceManager.getResourceStack(new ResourceLocation("ftbquests", "ftb_quests_theme.txt"))) {
                try {
                    InputStream in = resource.open();
                    try {
                        parse(map, FileUtils.read(in));
                    } catch (Throwable var9) {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Throwable var8) {
                                var9.addSuppressed(var8);
                            }
                        }
                        throw var9;
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception var10) {
                    var10.printStackTrace();
                }
            }
        } catch (Exception var11) {
            var11.printStackTrace();
        }
        if (map.isEmpty()) {
            FTBQuests.LOGGER.error("FTB Quests theme file is missing! Some mod has broken resource loading, inspect log for errors");
        }
        QuestTheme theme = new QuestTheme();
        theme.defaults = (SelectorProperties) map.remove(AllSelector.INSTANCE);
        if (theme.defaults == null) {
            theme.defaults = new SelectorProperties(AllSelector.INSTANCE);
        }
        theme.selectors.addAll(map.values());
        theme.selectors.sort(null);
        QuestTheme.instance = theme;
        FTBQuests.LOGGER.debug("Theme:");
        FTBQuests.LOGGER.debug("");
        FTBQuests.LOGGER.debug("[*]");
        for (Entry<String, String> entry : theme.defaults.properties.entrySet()) {
            FTBQuests.LOGGER.debug((String) entry.getKey() + ": " + theme.replaceVariables((String) entry.getValue(), 0));
        }
        for (SelectorProperties selectorProperties : theme.selectors) {
            FTBQuests.LOGGER.debug("");
            FTBQuests.LOGGER.debug("[" + selectorProperties.selector + "]");
            for (Entry<String, String> entry : selectorProperties.properties.entrySet()) {
                FTBQuests.LOGGER.debug((String) entry.getKey() + ": " + theme.replaceVariables((String) entry.getValue(), 0));
            }
        }
        LinkedHashSet<String> list = new LinkedHashSet();
        list.add("circle");
        list.add("square");
        list.add("rsquare");
        for (String s : ThemeProperties.EXTRA_QUEST_SHAPES.get().split(",")) {
            list.add(s.trim());
        }
        QuestShape.reload(new ArrayList(list));
    }

    private static void parse(Map<ThemeSelector, SelectorProperties> selectorPropertyMap, List<String> lines) {
        List<SelectorProperties> current = new ArrayList();
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty() && !line.startsWith("//")) {
                int si;
                int ei;
                if (line.length() > 2 && (si = line.indexOf(91)) < (ei = line.indexOf(93))) {
                    current.clear();
                    for (String sel : line.substring(si + 1, ei).split("\\|")) {
                        AndSelector andSelector = new AndSelector();
                        for (String sel1 : sel.trim().split("&")) {
                            ThemeSelector themeSelector = parse(Pattern.compile("\\s").matcher(sel1).replaceAll(""));
                            if (themeSelector != null) {
                                andSelector.selectors.add(themeSelector);
                            }
                        }
                        if (!andSelector.selectors.isEmpty()) {
                            ThemeSelector selector = (ThemeSelector) (andSelector.selectors.size() == 1 ? (ThemeSelector) andSelector.selectors.get(0) : andSelector);
                            current.add((SelectorProperties) selectorPropertyMap.computeIfAbsent(selector, SelectorProperties::new));
                        }
                    }
                } else if (!current.isEmpty()) {
                    String[] s1 = line.split(":", 2);
                    if (s1.length == 2) {
                        String k = s1[0].trim();
                        String v = s1[1].trim();
                        if (!k.isEmpty() && !v.isEmpty()) {
                            for (SelectorProperties selectorProperties : current) {
                                selectorProperties.properties.put(k, v);
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private static ThemeSelector parse(String sel) {
        if (sel.isEmpty()) {
            return null;
        } else if (sel.equals("*")) {
            return AllSelector.INSTANCE;
        } else if (sel.startsWith("!")) {
            ThemeSelector s = parse(sel.substring(1));
            return s == null ? null : new NotSelector(s);
        } else if (QuestObjectType.NAME_MAP.map.containsKey(sel)) {
            return new TypeSelector(QuestObjectType.NAME_MAP.get(sel));
        } else if (sel.startsWith("#")) {
            String s = sel.substring(1);
            return s.isEmpty() ? null : new TagSelector(s);
        } else {
            return (ThemeSelector) QuestObjectBase.parseHexId(sel).map(IDSelector::new).orElse(null);
        }
    }
}